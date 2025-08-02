package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.NgonNgu;
import com.example.datn_sd80_sum2025.service.NgonNguService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ngon-ngu")
public class NgonNguController {
    @Autowired
    private NgonNguService ngonNguService;

    // Hiển thị danh sách
    @GetMapping("/hien-thi")
    public String hienThi(Model model) {
        List<NgonNgu> list = ngonNguService.getAll();
        model.addAttribute("listNgonNgu", list);
        return "ngon-ngu/list";
    }

    // Form thêm mới
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("ngonNgu", new NgonNgu());
        return "ngon-ngu/add";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        NgonNgu ngonNgu = ngonNguService.getById(id);
        model.addAttribute("ngonNgu", ngonNgu);
        return "ngon-ngu/add";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("ngonNgu") NgonNgu ngonNgu, BindingResult result) {
        System.out.println("Save ngonNgu: " + ngonNgu);
        if (result.hasErrors()) {
            return "ngon-ngu/add";
        }
        ngonNguService.save(ngonNgu);
        return "redirect:/ngon-ngu/hien-thi";
    }

    // Xóa (chuyển trạng thái về 0)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        ngonNguService.delete(id);
        return "redirect:/ngon-ngu/hien-thi";
    }
}
