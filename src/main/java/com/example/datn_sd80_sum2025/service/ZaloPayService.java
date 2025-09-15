package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.utils.HMACUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ZaloPayService {

    @Value("${zalopay.app_id}")
    private String appId;

    @Value("${zalopay.key1}")
    private String key1;

    @Value("${zalopay.key2}")
    private String key2;

    @Value("${zalopay.endpoint.create}")
    private String createEndpoint;

    @Value("${zalopay.endpoint.status}")
    private String statusEndpoint;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Tạo thanh toán trên ZaloPay
     */
    public String createPayment(HoaDon hoaDon, String paymentMethod) throws JsonProcessingException {
        String appTransId = generateAppTransId(hoaDon.getId());

        Map<String, Object> embedData = Map.of(
                "redirecturl", "http://localhost:8080/payment/result/" + hoaDon.getId()
        );

        List<Map<String, Object>> items = hoaDonChiTietService.getByHoaDonId(hoaDon.getId())
                .stream()
                .map(hdct -> Map.<String, Object>of(
                        "itemid", hdct.getSach().getId(),
                        "itemname", hdct.getSach().getTenSach(),
                        "itemprice", hdct.getDonGia().longValue(),
                        "itemquantity", hdct.getSoLuong()
                ))
                .toList();

        String embedDataStr = objectMapper.writeValueAsString(embedData);
        String itemStr = objectMapper.writeValueAsString(items);
        long amount = hoaDon.getTongTien().longValue();
        String appUser = (hoaDon.getKhachHang() != null)
                ? hoaDon.getKhachHang().getId().toString()
                : "guest";
        long appTime = System.currentTimeMillis();

        String data = appId + "|" + appTransId + "|" + appUser + "|" + amount + "|"
                + appTime + "|" + embedDataStr + "|" + itemStr;

        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("app_id", appId);
        body.add("app_trans_id", appTransId);
        body.add("app_user", appUser);
        body.add("app_time", String.valueOf(appTime));
        body.add("amount", String.valueOf(amount));
        body.add("embed_data", embedDataStr);
        body.add("item", itemStr);
        body.add("description", "Thanh toan don hang #" + hoaDon.getId());
        body.add("mac", mac);
        body.add("payment_method", paymentMethod);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        log.debug("ZaloPay createPayment request: {}", body);

        String rawResponse = restTemplate.postForObject(createEndpoint, request, String.class);
        log.debug("ZaloPay raw response: {}", rawResponse);

        Map<String, Object> respMap = objectMapper.readValue(rawResponse, Map.class);
        if (respMap != null && respMap.containsKey("order_url")) {
            return (String) respMap.get("order_url");
        }

        throw new RuntimeException("ZaloPay error response: " + rawResponse);
    }

    private String generateAppTransId(Integer hoaDonId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        return LocalDate.now().format(formatter) + "_" + hoaDonId + "_" + System.currentTimeMillis();
    }

    /**
     * Kiểm tra trạng thái thanh toán qua API query
     */
    public void checkPaymentStatus(Integer idHoaDon, String appTransId) {
        try {
            String data = appId + "|" + appTransId + "|" + key2;
            String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key2, data);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("app_id", appId);
            body.add("app_trans_id", appTransId);
            body.add("mac", mac);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            String rawResponse = restTemplate.postForObject(statusEndpoint, request, String.class);
            log.debug("ZaloPay checkPaymentStatus raw response: {}", rawResponse);

            Map<String, Object> respMap = objectMapper.readValue(rawResponse, Map.class);

            if (respMap != null && (Integer) respMap.get("return_code") == 1) {
                int status = (int) respMap.get("return_code");
                if (status == 1) {
                    hoaDonService.updateStatus(idHoaDon, 1); // thành công
                }
            }
        } catch (Exception e) {
            log.error("Lỗi khi checkPaymentStatus cho hóa đơn #{}: {}", idHoaDon, e.getMessage(), e);
        }
    }

    /**
     * Xác thực callback từ ZaloPay
     */
    public boolean verifyCallback(Map<String, Object> payload) {
        try {
            String data = payload.get("data").toString();
            String reqMac = payload.get("mac").toString();

            String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key2, data);
            return mac.equals(reqMac);
        } catch (Exception e) {
            log.error("Lỗi verifyCallback: {}", e.getMessage(), e);
            return false;
        }
    }
}





