package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.service.HoaDonService;
import com.example.datn_sd80_sum2025.service.ZaloPayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired private ZaloPayService zaloPayService;
    @Autowired private HoaDonService hoaDonService;
    private final String callbackUrl = "https://e158b6018cd8.ngrok-free.app/payment/callback";


    @GetMapping("/create/{idHoaDon}")
    public void createPayment(@PathVariable Integer idHoaDon,
                              @RequestParam(defaultValue = "ZALOPAY") String paymentMethod,
                              HttpServletResponse response) throws IOException {
        try {
            HoaDon hoaDon = hoaDonService.getById(idHoaDon);
            String orderUrl = zaloPayService.createPayment(hoaDon, paymentMethod, callbackUrl);
            response.sendRedirect(orderUrl);
        } catch (Exception e) {
            log.error("Lỗi khi tạo thanh toán: {}", e.getMessage(), e);
            response.sendRedirect("/payment/result/" + idHoaDon + "?status=fail");
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> zalopayCallback(HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            //  Đọc payload từ request
            Map<String, Object> payload;
            String contentType = request.getContentType();
            ObjectMapper mapper = new ObjectMapper();

            if (contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                payload = mapper.readValue(request.getInputStream(), Map.class);
            } else {
                // form-urlencoded
                Map<String, String[]> paramMap = request.getParameterMap();
                payload = paramMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue()[0]
                        ));
            }
            log.info("ZaloPay callback payload: {}", payload);

            // Xác thực MAC
            if (!zaloPayService.verifyCallback(payload)) {
                log.warn("ZaloPay callback invalid MAC: {}", payload);
                responseMap.put("return_code", 0);
                responseMap.put("return_message", "invalid mac");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }

            // Kiểm tra và parse data
            Object dataObj = payload.get("data");
            if (dataObj == null) {
                log.warn("Payload không chứa trường 'data': {}", payload);
                responseMap.put("return_code", 0);
                responseMap.put("return_message", "Missing data");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }

            Map<String, Object> dataMap = mapper.readValue(dataObj.toString(), Map.class);
            String appTransId = (String) dataMap.get("app_trans_id");
            if (appTransId == null) {
                log.warn("'app_trans_id' trống trong data: {}", dataMap);
                responseMap.put("return_code", 0);
                responseMap.put("return_message", "Missing app_trans_id");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }

            Integer idHoaDon = Integer.parseInt(appTransId.split("_")[1]);

            // Lấy return_code an toàn
            Object returnCodeObj = payload.get("type");
            if (returnCodeObj == null) {
                log.warn("Payload không chứa trường 'type': {}", payload);
                responseMap.put("return_code", 0);
                responseMap.put("return_message", "Missing return_code");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }

            int returnCode = (returnCodeObj instanceof Number)
                    ? ((Number) returnCodeObj).intValue()
                    : Integer.parseInt(returnCodeObj.toString());

            log.info("ZaloPay callback return_code={} for HĐ #{}", returnCode, idHoaDon);

            // Cập nhật trạng thái hóa đơn
            int trangThai = returnCode == 1 ? 1 : 4; // 1 = thành công, 4 = hủy
            hoaDonService.updateStatus(idHoaDon, trangThai);
            log.info("Cập nhật trạng thái HĐ #{} -> {}", idHoaDon, trangThai);

            // Trả về response cho ZaloPay
            responseMap.put("return_code", 1);
            responseMap.put("return_message", "OK");
            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            log.error("Lỗi callback ZaloPay: {}", e.getMessage(), e);
            responseMap.put("return_code", 0);
            responseMap.put("return_message", "Error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
    }

    @GetMapping("/result/{idHoaDon}")
    public String paymentResult(@PathVariable Integer idHoaDon,
                                @RequestParam(required = false) String status,
                                Model model) {
        HoaDon hoaDon = hoaDonService.getById(idHoaDon);
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("message", "fail".equals(status) ? "Không thể tạo thanh toán"
                : (hoaDon.getTrangThai() == 1 ? "Thanh toán thành công" : "Thanh toán chưa hoàn tất"));
        System.out.print("Status: "+ status);
        return "redirect:/home/orders/detail/" + idHoaDon;
    }
}

