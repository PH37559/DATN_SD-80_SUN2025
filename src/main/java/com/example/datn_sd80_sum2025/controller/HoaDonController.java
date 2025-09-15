package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.service.HoaDonChiTietService;
import com.example.datn_sd80_sum2025.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("/hien-thi")
    public String hienThiHoaDon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam Map<String, String> params,
            Model model) {
        String keyword = params.get("keyword");
        Integer trangThai = parseInteger(params.get("trangThai"));
        String phuongThucThanhToan = params.get("phuongThucThanhToan");
        LocalDate ngayLapFrom = parseDate(params.get("ngayLapFrom"));
        LocalDate ngayLapTo = parseDate(params.get("ngayLapTo"));
        String priceRange = params.get("priceRange");

        Page<HoaDon> listHoaDon = hoaDonService.search(
                keyword, trangThai, phuongThucThanhToan, ngayLapFrom, ngayLapTo, priceRange,
                PageRequest.of(page, size));

        model.addAttribute("listHoaDon", listHoaDon);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", listHoaDon.getTotalPages());

        model.addAttribute("keyword", keyword);
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("phuongThucThanhToan", phuongThucThanhToan);
        model.addAttribute("ngayLapFrom", params.get("ngayLapFrom"));
        model.addAttribute("ngayLapTo", params.get("ngayLapTo"));
        model.addAttribute("ngayLapTo", params.get("ngayLapTo"));
        model.addAttribute("priceRange", params.get("priceRange"));
        return "hoa_don/list";
    }


    @GetMapping("/detail/{id}")
    public String chiTiet(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("hoaDon", hoaDonService.getById(id));
        model.addAttribute("listChiTiet", hoaDonChiTietService.getByHoaDonId(id));
        return "hoa_don/detail";
    }

    @PostMapping("/confirm/{id}")
    @ResponseBody
    public ResponseEntity<?> confirmOrder(
            @PathVariable Integer id,
            Authentication authentication) {
        NhanVien nv = (NhanVien) authentication.getPrincipal();
        hoaDonService.updateOnlineOrder(id, 2, nv.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel/{id}")
    @ResponseBody
    public ResponseEntity<?> cancelOrder(
            @PathVariable Integer id,
            Authentication authentication) {
        NhanVien nv = (NhanVien) authentication.getPrincipal();
        hoaDonService.updateOnlineOrder(id, 4, nv.getId());
        return ResponseEntity.ok().build();
    }


    private Integer parseInteger(String value) {
        try {
            return value != null && !value.isBlank() ? Integer.valueOf(value) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate parseDate(String value) {
        try {
            return value != null && !value.isBlank() ? LocalDate.parse(value) : null;
        } catch (Exception e) {
            return null;
        }
    }
}

