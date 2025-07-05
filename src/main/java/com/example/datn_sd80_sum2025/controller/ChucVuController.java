package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.ChucVu;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.service.ChucVuService;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chuc-vu")
public class ChucVuController {

    @Autowired
    ChucVuService chucVuService;

    @GetMapping("/hien-thi")
    public List<ChucVu> hienThiDSChucVu(Model model) {
        return chucVuService.getAll();
    }
}
