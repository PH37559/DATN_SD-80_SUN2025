package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.Nxb;
import com.example.datn_sd80_sum2025.service.NXBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nxb")
public class NXBController {
    @Autowired
    private NXBService nxbService;

    @GetMapping("/hien-thi")
    public String list(Model model) {
        model.addAttribute("listNxb", nxbService.getAll());
        return "nxb/list"; // Thymeleaf template
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("nxb", new Nxb());
        return "nxb/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("nxb") Nxb nxb) {
        nxbService.save(nxb);
        return "redirect:/nxb/hien-thi";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Nxb nxb = nxbService.getById(id);
        model.addAttribute("nxb", nxb);
        return "nxb/add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        nxbService.deleteById(id);
        return "redirect:/nxb/hien-thi";
    }
}
