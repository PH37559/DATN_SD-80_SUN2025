package com.example.datn_sd80_sum2025.controller;
import com.example.datn_sd80_sum2025.dto.request.KhachHangCreate;
import com.example.datn_sd80_sum2025.entity.GioHang;
import com.example.datn_sd80_sum2025.entity.GioHangChiTiet;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.Sach;
import com.example.datn_sd80_sum2025.service.GioHangChiTietService;
import com.example.datn_sd80_sum2025.service.GioHangService;
import com.example.datn_sd80_sum2025.service.KhachHangService;
import com.example.datn_sd80_sum2025.service.sanpham.SachService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/cart")
public class GioHangController {
    @Autowired
    KhachHangService khachHangService;

    @Autowired
    SachService sachService;

    @Autowired
    GioHangService gioHangService;

    @Autowired
    GioHangChiTietService gioHangChiTietService;

    @ModelAttribute("khachHang")
    public KhachHangCreate defaultKhachHang() {
        return new KhachHangCreate();
    }

    @GetMapping
    public String showCart(Model model, HttpSession session) {
        GioHang gioHang = createOrGetCart(session);

        List<GioHangChiTiet> listGHCT = gioHangChiTietService.getByGioHangId(gioHang.getId());
        int tongSach = 0;

        if (listGHCT != null && !listGHCT.isEmpty()) {
            tongSach = gioHangChiTietService.countItemsInCart(gioHang.getId());
        } else {
            listGHCT = new ArrayList<>();
        }

        session.setAttribute("listGHCT", listGHCT);
        model.addAttribute("gioHang", gioHang);
        model.addAttribute("listGHCT", listGHCT);
        model.addAttribute("cartItemCount", tongSach);

        return "khach-hang/customer/gio-hang/cart";
    }


    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addToCart(
            @RequestParam("idSach") Integer idSach,
            @RequestParam("soLuong") Integer soLuong,
            HttpSession session) {
        try {
            if (soLuong <= 0) {
                return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Số lượng phải lớn hơn 0"));
            }

            GioHang gioHang = createOrGetCart(session);
            Sach sach = sachService.getById(idSach);
            GioHangChiTiet ghct = gioHangChiTietService.getByIdCartAndIdBook(gioHang.getId(), idSach);

            int tongSoLuong = soLuong + (ghct != null ? ghct.getSoLuong() : 0);
            if (tongSoLuong > sach.getSoLuong()) {
                return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Không đủ số lượng sách còn lại"));
            }

            gioHangChiTietService.themSanPham(gioHang.getId(), idSach, soLuong);
            int totalItems = gioHangChiTietService.countItemsInCart(gioHang.getId());

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Đã thêm vào giỏ hàng",
                    "cartItemCount", totalItems));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateQuantity(@RequestParam("idGioHang") Integer idGioHang,
                                            @RequestParam("idSach") Integer idSach,
                                            @RequestParam("soLuong") Integer soLuong) {
        try {
            if (soLuong <= 0) {
                return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Số lượng phải lớn hơn 0"));
            }

            Sach sach = sachService.getById(idSach);
            if (soLuong > sach.getSoLuong()) {
                return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Không đủ số lượng sách còn lại"));
            }

            gioHangChiTietService.capNhatSoLuong(idGioHang, idSach, soLuong);
            int totalItems = gioHangChiTietService.countItemsInCart(idGioHang);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Cập nhật thành công",
                    "cartItemCount", totalItems));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", "Cập nhật thất bại"));
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> removeItem(@RequestParam("idGioHang") Integer idGioHang,
                                        @RequestParam("idSach") Integer idSach) {
        try {
            gioHangChiTietService.clearByIdCartAndIdBook(idGioHang, idSach);
            int totalItems = gioHangChiTietService.countItemsInCart(idGioHang);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Đã xoá sản phẩm khỏi giỏ",
                    "cartItemCount", totalItems
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", "Xoá thất bại"));
        }
    }

    public GioHang createOrGetCart(HttpSession session) {
        KhachHang kh = (KhachHang) session.getAttribute("khachHang");
        if (kh != null) {
            return gioHangService.getByIdKhachHang(kh.getId());
        } else {
            GioHang anDanh = (GioHang) session.getAttribute("gioHangAnDanh");
            if (anDanh == null) {
                anDanh = gioHangService.createAnonymousCart();
                session.setAttribute("gioHangAnDanh", anDanh);
            }
            return anDanh;
        }
    }
}
