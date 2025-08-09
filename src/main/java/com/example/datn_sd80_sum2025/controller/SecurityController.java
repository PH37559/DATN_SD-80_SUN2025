package com.example.datn_sd80_sum2025.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SecurityController {

    @RequestMapping("/security/login")
    public String loginForm(Model model){
//        model.addAttribute("message", "Vui lòng đăng nhập!");
        return "security/login";
    }

    @RequestMapping("/security/login/success")
    public String loginSuccess(Model model){
        model.addAttribute("message", "Đăng nhập thành công!");
        return "trang_chu/home";
    }

    @RequestMapping("/security/login/error")
    public String loginError(Model model) {
        model.addAttribute("message", "Tên tài khoản hoặc mật khẩu bị sai. Vui lòng nhập lại!");
        return "security/login";
    }

    @RequestMapping("/security/unauthorized")
    public String unauthorize(Model model) {
        model.addAttribute("message", "Không có quyền truy xuất!");
        return "security/unauthorized";
    }

    @RequestMapping("/security/logout/success")
    public String logout(Model model) {
//        model.addAttribute("message", "Sai thông tin đăng nhập!");
        return "security/login";
    }

}
