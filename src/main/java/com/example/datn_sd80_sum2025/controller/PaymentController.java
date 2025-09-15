package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.service.HoaDonService;
import com.example.datn_sd80_sum2025.service.PaymentTimeoutService;
import com.example.datn_sd80_sum2025.service.ZaloPayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private ZaloPayService zaloPayService;

    @Autowired
    private PaymentTimeoutService paymentTimeoutService;

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/create/{idHoaDon}")
    public void createPayment(@PathVariable Integer idHoaDon,
                              @RequestParam(defaultValue = "ZALOPAY") String paymentMethod,
                              HttpServletResponse response) throws IOException {
        try {
            HoaDon hoaDon = hoaDonService.getById(idHoaDon);
            String orderUrl = zaloPayService.createPayment(hoaDon, paymentMethod);

            // Lấy appTransId từ orderUrl
            String appTransId = orderUrl.contains("apptransid=")
                    ? orderUrl.split("apptransid=")[1].split("&")[0]
                    : null;

            if (appTransId != null) {
                paymentTimeoutService.scheduleCancel(idHoaDon, appTransId);
            }

            response.sendRedirect(orderUrl);
        } catch (Exception e) {
            log.error("Lỗi khi tạo thanh toán: {}", e.getMessage(), e);
            response.sendRedirect("/payment/result/" + idHoaDon + "?status=fail");
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<String> zalopayCallback(@RequestBody Map<String, Object> payload) {
        log.debug("ZaloPay callback payload: {}", payload);
        try {
            if (!zaloPayService.verifyCallback(payload)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"return_code\":0,\"return_message\":\"invalid mac\"}");
            }

            Map<String, Object> dataMap = new ObjectMapper()
                    .readValue(payload.get("data").toString(), Map.class);

            String appTransId = (String) dataMap.get("app_trans_id");
            String[] parts = appTransId.split("_");
            Integer hoaDonId = Integer.parseInt(parts[1]);

            int returnCode = (int) dataMap.get("return_code");
            if (returnCode == 1) {
                hoaDonService.updateStatus(hoaDonId, 1); // thành công
            } else {
                hoaDonService.updateStatus(hoaDonId, 0); // thất bại
            }

            return ResponseEntity.ok("{\"return_code\":1,\"return_message\":\"OK\"}");
        } catch (Exception e) {
            log.error("Lỗi callback: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"return_code\":0,\"return_message\":\"Error\"}");
        }
    }

    @GetMapping("/result/{idHoaDon}")
    public String paymentResult(@PathVariable Integer idHoaDon,
                                @RequestParam(required = false) String status,
                                Model model) {
        HoaDon hoaDon = hoaDonService.getById(idHoaDon);
        model.addAttribute("hoaDon", hoaDon);

        if ("fail".equals(status)) {
            model.addAttribute("message", "Không thể tạo thanh toán");
        } else {
            model.addAttribute("message",
                    hoaDon.getTrangThai() == 1 ? "Thanh toán thành công" : "Thanh toán thất bại");
        }

        return "redirect:/home/orders/detail/"+idHoaDon;
    }
}
