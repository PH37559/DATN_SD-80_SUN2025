package com.example.datn_sd80_sum2025.controller;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.example.datn_sd80_sum2025.entity.PhieuGiamGia;
import com.example.datn_sd80_sum2025.service.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pgg")
public class PhieuGiamGiaController {
    @Autowired
    private PhieuGiamGiaService pggService;

    @GetMapping("/hien-thi")
    public String hienThi(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<PhieuGiamGia> list;

        // Tìm kiếm theo tên phiếu (nếu có keyword)
        if (keyword != null && !keyword.isEmpty()) {
            list = pggService.searchByTenPhieu(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            list = pggService.getAll();
        }

        // Cập nhật trạng thái tự động cho phiếu đã hết hạn
        LocalDate today = LocalDate.now();
        for (PhieuGiamGia p : list) {
            if (p.getNgayKetThuc().isBefore(today) && p.getTrangThai() != 0) {
                // Nếu đã hết hạn thì bắt buộc set = 0 (ngừng áp dụng)
                p.setTrangThai(0);
                pggService.save(p);
            }
            // Nếu chưa hết hạn thì giữ nguyên trạng thái
            // => để admin có thể bật/tắt thủ công
        }

        model.addAttribute("listPhieuGiamGia", list);
        return "pgg/list";
    }


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("pgg", new PhieuGiamGia());
        return "pgg/add";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("pgg") PhieuGiamGia pgg,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();

        if (pgg.getMaPhieu() == null || pgg.getMaPhieu().isBlank()) {
            errors.put("maPhieu", "Mã phiếu không được để trống");
        } else if (!pgg.getMaPhieu().matches("^[a-zA-Z0-9]+$")) {
            errors.put("maPhieu", "Mã phiếu chỉ được chứa chữ cái và số, không chứa dấu cách hoặc ký tự đặc biệt");
        } else if (pgg.getId() == null && pggService.existsByMaPhieu(pgg.getMaPhieu())) {
            errors.put("maPhieu", "Mã phiếu đã tồn tại");
        }

        if (pgg.getTenPhieu() == null || pgg.getTenPhieu().isBlank()) {
            errors.put("tenPhieu", "Tên phiếu không được để trống");
        }

        if (pgg.getPhanTramGia() == null || pgg.getPhanTramGia() > 100) {
            errors.put("phanTramGia", "Phần trăm giảm phải ≤ 100");
        }

        if (pgg.getGiamToiDa() == null) {
            errors.put("giamToiDa", "Giảm tối đa không được để trống");
        } else if (pgg.getGiamToiDa().compareTo(BigDecimal.ZERO) <= 0) {
            errors.put("giamToiDa", "Giảm tối đa phải lớn hơn 0");
        } else if (pgg.getGiamToiDa().compareTo(new BigDecimal("1000000")) > 0) {
            errors.put("giamToiDa", "Giảm tối đa không được vượt quá 1.000.000 VNĐ");
        }

        if (pgg.getSoLuong() == null) {
            errors.put("soLuong", "Số lượng không được để trống");
        } else if (pgg.getSoLuong() < 0) {
            errors.put("soLuong", "Số lượng phải lớn hơn hoặc bằng 0");
        }

        if (pgg.getNgayBatDau() == null) {
            errors.put("ngayBatDau", "Ngày bắt đầu không được để trống");
        }

        if (pgg.getNgayKetThuc() == null) {
            errors.put("ngayKetThuc", "Ngày kết thúc không được để trống");
        } else if (pgg.getNgayBatDau() != null && pgg.getNgayKetThuc().isBefore(pgg.getNgayBatDau())) {
            errors.put("ngayKetThuc", "Ngày kết thúc phải sau ngày bắt đầu");
        }

        // Nếu có lỗi
        if (!errors.isEmpty() || result.hasErrors()) {
            model.addAttribute("errors", errors);
            return "pgg/add";
        }

        // Lưu phiếu
        pggService.save(pgg);

        // Thêm flash attribute để hiển thị thông báo
        if (pgg.getId() == null) {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm phiếu giảm giá thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật phiếu giảm giá thành công!");
        }

        return "redirect:/pgg/hien-thi";
    }


    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        PhieuGiamGia pgg = pggService.getById(id);
        if (pgg != null) {
            if (pgg.getMaPhieu() != null) {
                pgg.setMaPhieu(pgg.getMaPhieu().trim());
            }
            if (pgg.getTenPhieu() != null) {
                pgg.setTenPhieu(pgg.getTenPhieu().trim());
            }
        }
        model.addAttribute("pgg", pgg);
        return "pgg/add";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        pggService.delete(id);
        return "redirect:/pgg/hien-thi";
    }
}
