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

    @GetMapping("/api/voucher/kiem-tra")
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

        switch (voucher.getLoaiAsString()) {
            case "PERCENT":
                if (tongTien >= voucher.getDieuKien().doubleValue()) {
                    giam = tongTien * voucher.getGiaTri().doubleValue() / 100;
                }
                break;
            case "FIXED":
                if (tongTien >= voucher.getDieuKien().doubleValue()) {
                    giam = voucher.getGiaTri().doubleValue();
                }
                break;
            case "FREE":
                giam = tongTien;
                break;
            case "SPECIAL":
                if ("sinh_nhat".equalsIgnoreCase(voucher.getDoiTuongApDung())) {
                    giam = tongTien * voucher.getGiaTri().doubleValue() / 100;
                }
                break;
            default:
                return ResponseEntity.badRequest().body(Map.of("error", "Loại voucher không hợp lệ"));
        }

        if (voucher.getGiamToiDa() != null && giam > voucher.getGiamToiDa().doubleValue()) {
            giam = voucher.getGiamToiDa().doubleValue();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("giaTriGiam", giam);
        result.put("maVoucher", voucher.getMa());
        result.put("tenVoucher", voucher.getTen());
        return ResponseEntity.ok(result);
    }
}
