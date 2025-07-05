package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;
import com.example.datn_sd80_sum2025.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thong-ke")
public class ThongKeController {

    @Autowired
    private ThongKeService thongKeService;

    @GetMapping("/doanh-thu")
    public String doanhThu(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {

        if (year == null) {
            year = java.time.LocalDate.now().getYear();
        }

        List<DoanhThuDTO> list = thongKeService.thongKeDoanhThu(year, month);

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("list", list);
        return "thong-ke/doanh-thu";
    }

    @GetMapping("/doanh-thu/export")
    public String exportPDF(
            @RequestParam Integer year,
            @RequestParam(required = false) Integer month) {
        thongKeService.exportDoanhThuPDF(year, month);
        return "redirect:/thong-ke/doanh-thu?year=" + year + (month != null ? "&month=" + month : "");
    }
}
