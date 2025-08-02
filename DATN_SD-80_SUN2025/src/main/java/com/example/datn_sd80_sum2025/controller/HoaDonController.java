package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.service.HoaDonChiTietService;
import com.example.datn_sd80_sum2025.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("/hien-thi")
    public String hienThiHoaDon(Model model) {
        model.addAttribute("listHoaDon", hoaDonService.getAll());
        return "hoa_don/list";
    }


    @GetMapping("/detail/{id}")
    public String chiTiet(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("hoaDon", hoaDonService.getById(id));
        model.addAttribute("listChiTiet", hoaDonChiTietService.getByHoaDonId(id));
        return "hoa_don/detail";
    }
}

