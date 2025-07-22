package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.Voucher;
import com.example.datn_sd80_sum2025.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/voucher/hien-thi")
    public String hienThiVoucher(Model model) {
        List<Voucher> list = voucherService.getAll();
        model.addAttribute("listVoucher", list);
        return "voucher/list";
    }
}
