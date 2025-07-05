package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.TheLoai;
import com.example.datn_sd80_sum2025.service.TheLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/the-loai")
public class TheLoaiController {
    @Autowired
    private TheLoaiService theLoaiService;

    @GetMapping("/hien-thi")
    public String hienThi(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<TheLoai> list;
        if (keyword != null && !keyword.isBlank()) {
            list = theLoaiService.searchByName(keyword);
        } else {
            list = theLoaiService.getAll();
        }
        model.addAttribute("listTheLoai", list);
        model.addAttribute("keyword", keyword);
        return "the-loai/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("theLoai", new TheLoai());
        return "the-loai/add";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        TheLoai tl = theLoaiService.getById(id);
        model.addAttribute("theLoai", tl);
        return "the-loai/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("theLoai") TheLoai theLoai) {
        theLoaiService.save(theLoai);
        return "redirect:/the-loai/hien-thi";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        theLoaiService.delete(id);
        return "redirect:/the-loai/hien-thi";
    }
}
