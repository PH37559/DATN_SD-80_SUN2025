package com.example.datn_sd80_sum2025.controller;


import com.example.datn_sd80_sum2025.entity.Sach;
import com.example.datn_sd80_sum2025.service.NXBService;
import com.example.datn_sd80_sum2025.service.NgonNguService;
import com.example.datn_sd80_sum2025.service.TheLoaiService;
import com.example.datn_sd80_sum2025.service.sanpham.SachService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sach")
public class SachController {

    @Autowired
    private SachService sachService;
    @Autowired
    private NgonNguService ngonNguService;
    @Autowired
    private TheLoaiService theLoaiService;
    @Autowired
    private NXBService nxbService;

    // Hiển thị danh sách sách với phân trang + tìm kiếm
    @GetMapping("/hien-thi")
    public String listPaged(@RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "keyword", required = false) String keyword,
                            Model model) {

        int pageSize = 100;

        Page<Sach> sachPage;

        if (keyword != null && !keyword.isEmpty()) {
            sachPage = sachService.searchByTenSach(keyword, page, pageSize);
            model.addAttribute("keyword", keyword);
        } else {
            sachPage = sachService.getAllPaged(page, pageSize);
        }

        model.addAttribute("listSach", sachPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sachPage.getTotalPages());

        return "sach/list"; // Đường dẫn tới file Thymeleaf
    }


    // Hiển thị form thêm sách mới
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("sach", new Sach());
        model.addAttribute("listNgonNgu", ngonNguService.getAllActive());
        model.addAttribute("listTheLoai", theLoaiService.getAll());
        model.addAttribute("listNxb", nxbService.getAll());
        model.addAttribute("errors", new HashMap<String, String>()); // ✅ thêm errors trống
        return "sach/add";
    }

    // Lưu sách mới
    @PostMapping("/save")
    public String save(@ModelAttribute("sach") Sach sach, Model model) {
        Map<String, String> errors = new HashMap<>();

        if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
            errors.put("maSach", "Mã sách không được để trống");
        } else {
            // Xoá khoảng trắng đầu cuối trước
            String rawMaSach = sach.getMaSach().trim();
            // Tự động loại bỏ ký tự đặc biệt & khoảng trắng giữa chuỗi, chỉ giữ chữ và số
            String cleanedMaSach = rawMaSach.replaceAll("[^a-zA-Z0-9]", "");
            sach.setMaSach(cleanedMaSach);

            // Kiểm tra sau khi clean
            if (cleanedMaSach.isEmpty()) {
                errors.put("maSach", "Mã sách không được chỉ chứa ký tự đặc biệt hoặc khoảng trắng");
            } else if (sach.getId() == null && sachService.existsByMaSach(cleanedMaSach)) {
                errors.put("maSach", "Mã sách đã tồn tại");
            }
        }
        // isbn
        if (sach.getIsbn() == null || sach.getIsbn().trim().isEmpty()) {
            errors.put("isbn", "ISBN không được để trống");
        } else {
            String originalIsbn = sach.getIsbn();       // chưa trim
            String trimmedIsbn = originalIsbn.trim();   // đã trim

            // Kiểm tra khoảng trắng ở đầu hoặc cuối
            if (!originalIsbn.equals(trimmedIsbn)) {
                errors.put("isbn", "ISBN không được chứa khoảng trắng ở đầu hoặc cuối");
            }
            // Kiểm tra chỉ được chứa chữ số (0-9), không chứa khoảng trắng hoặc ký tự đặc biệt
            else if (!trimmedIsbn.matches("\\d+")) {
                errors.put("isbn", "ISBN chỉ được chứa số, không được có khoảng trắng hoặc ký tự đặc biệt");
            }
        }


        if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty()) {
            errors.put("tenSach", "Tên sách không được để trống");
        } else {
            String tenSach = sach.getTenSach().trim();

            // Kiểm tra xem có chứa ký tự đặc biệt không (chỉ cho phép chữ, số và khoảng trắng)
            // Ví dụ chỉ cho phép a-z, A-Z, 0-9 và dấu cách
            if (!tenSach.matches("[\\p{L}0-9 ]+")) {
                errors.put("tenSach", "Tên sách không được chứa ký tự đặc biệt");
            }
        }

        if (sach.getNamXuatBan() == null || sach.getNamXuatBan() <= 0) {
            errors.put("namXuatBan", "Năm xuất bản phải > 0");
        } else {
            int currentYear = java.time.Year.now().getValue();
            if (sach.getNamXuatBan() > currentYear) {
                errors.put("namXuatBan", "Năm xuất bản không được lớn hơn năm hiện tại");
            }
        }

        if (sach.getTacGia() == null || sach.getTacGia().trim().isEmpty()) {
            errors.put("tacGia", "Tác giả không được để trống");
        }
        // Giá bán không được để trống và phải > 0
        if (sach.getGiaBan() == null) {
            errors.put("giaBan", "Giá bán không được để trống");
        } else if (sach.getGiaBan().compareTo(BigDecimal.ZERO) <= 0) {
            errors.put("giaBan", "Giá bán phải > 0");
        }

// Số lượng không được để trống và phải >= 0
        if (sach.getSoLuong() == null) {
            errors.put("soLuong", "Số lượng không được để trống");
        } else if (sach.getSoLuong() < 0) {
            errors.put("soLuong", "Số lượng phải >= 0");
        }


        if (sach.getNgonNgu() == null || sach.getNgonNgu().getId() == null) {
            errors.put("ngonNgu", "Phải chọn ngôn ngữ");
        }

        if (sach.getTheLoai() == null || sach.getTheLoai().getId() == null) {
            errors.put("theLoai", "Phải chọn thể loại");
        }

        if (sach.getNxb() == null || sach.getNxb().getId() == null) {
            errors.put("nxb", "Phải chọn NXB");
        }

        if (!errors.isEmpty()) {
            // Đưa lỗi ra model
            model.addAttribute("errors", errors);
            // Load lại dữ liệu combobox
            model.addAttribute("listNgonNgu", ngonNguService.getAllActive());
            model.addAttribute("listTheLoai", theLoaiService.getAll());
            model.addAttribute("listNxb", nxbService.getAll());
            return "sach/add";
        }

        // Không lỗi thì lưu
        sachService.save(sach);
        return "redirect:/sach/hien-thi";
    }



    // Hiển thị form chỉnh sửa sách
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Sach sach = sachService.getById(id);
        model.addAttribute("sach", sach);
        model.addAttribute("listNgonNgu", ngonNguService.getAllActive());
        model.addAttribute("listTheLoai", theLoaiService.getAll());
        model.addAttribute("listNxb", nxbService.getAll());
        model.addAttribute("errors", new HashMap<String, String>()); // ✅ thêm errors trống

        return "sach/add";
    }

    // Xóa sách (chỉ đổi trạng thái)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        sachService.delete(id);
        return "redirect:/sach/hien-thi";
    }
}
