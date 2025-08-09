package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // Hoặc dùng @Controller nếu bạn trả về view và thêm @ResponseBody cho method
@RequestMapping("/api/khach-hang")
public class APIController {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @GetMapping("/tim-theo-sdt")
    public ResponseEntity<?> timKhachHangTheoSdt(@RequestParam("sdt") String sdt) {
        Optional<KhachHang> kh = khachHangRepository.findBySdt(sdt);
        return kh.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
