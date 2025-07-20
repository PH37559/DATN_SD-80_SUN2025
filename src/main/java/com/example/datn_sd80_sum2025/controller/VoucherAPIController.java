package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.Voucher;
import com.example.datn_sd80_sum2025.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class VoucherAPIController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/api/voucher/ap-dung")
    public ResponseEntity<Map<String, Object>> apDungVoucher(
            @RequestParam String ma,
            @RequestParam double tongTien) {

        Voucher voucher = voucherService.findByMa(ma);

        if (voucher == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Mã không tồn tại"));
        }
        if (!voucher.isConHieuLuc()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Voucher đã hết hạn hoặc không còn hiệu lực"));
        }

        double giam = 0;

        switch (voucher.getLoai()) {
            case "PERCENT":
                if (tongTien >= voucher.getDieuKien()) {
                    giam = tongTien * voucher.getGiaTri() / 100;
                }
                break;
            case "FIXED":
                if (tongTien >= voucher.getDieuKien()) {
                    giam = voucher.getGiaTri();
                }
                break;
            case "FREE":
                giam = tongTien;
                break;
            case "SPECIAL":
                // nếu có phân loại khách hàng thì thêm điều kiện ở đây
                break;
            default:
                break;
        }

        if (voucher.getGiamToiDa() != null && giam > voucher.getGiamToiDa()) {
            giam = voucher.getGiamToiDa();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("giaTriGiam", giam);

        return ResponseEntity.ok(result);
    }
}
