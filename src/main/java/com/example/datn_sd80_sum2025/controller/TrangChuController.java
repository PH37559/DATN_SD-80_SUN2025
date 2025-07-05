package com.example.datn_sd80_sum2025.controller;
import com.example.datn_sd80_sum2025.entity.GioHangChiTiet;
import com.example.datn_sd80_sum2025.entity.GioHangChiTietId;
import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.Sach;
import com.example.datn_sd80_sum2025.service.*;
import com.example.datn_sd80_sum2025.service.sanpham.SachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/trang-chu")
public class TrangChuController {

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private SachService sachService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping("/hien-thi")
    public String hienThiTrangChu(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "") String keyword,
                                  @RequestParam(value = "hoaDonId", required = false) Integer hoaDonId) {

        int pageSize = 10;
        Page<Sach> sachPage = keyword != null && !keyword.isBlank()
                ? sachService.searchByTenSach(keyword, page, pageSize)
                : sachService.getAllPaged(page, pageSize);

        model.addAttribute("sanPhamList", sachPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sachPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("donHangChoList", hoaDonService.getDonHangCho());
        model.addAttribute("gioHangList", gioHangChiTietService.getAll());
        model.addAttribute("listKhachHang", khachHangService.getAll());
        model.addAttribute("listNhanVien", nhanVienService.getAll());
        model.addAttribute("ngayHienTai", java.time.LocalDate.now());

        if (hoaDonId != null) {
            HoaDon hoaDon = hoaDonService.getById(hoaDonId);
            model.addAttribute("hoaDon", hoaDon);
        }


        return "trang_chu/home";
    }



    @PostMapping("/them-vao-gio")
    public String themVaoGio(@RequestParam("idGioHang") Integer idGioHang,
                             @RequestParam("idSach") Integer idSach,
                             @RequestParam("soLuong") Integer soLuong) {
        gioHangChiTietService.themSanPham(idGioHang, idSach, soLuong);
        return "redirect:/trang-chu/hien-thi";
    }


    @PostMapping("/xoa")
    public String xoaSanPham(@RequestParam Integer idGioHang,
                             @RequestParam Integer idSach) {
        gioHangChiTietService.delete(new GioHangChiTietId(idGioHang, idSach));
        return "redirect:/trang-chu/hien-thi";
    }

    @PostMapping("/don-hang/tao")
    public String taoDonHang(
            @RequestParam("ngayTao") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayTao,
            @RequestParam("khachHangId") Integer khachHangId,
            @RequestParam("nhanVienId") Integer nhanVienId,
            @RequestParam("phuongThucTT") String phuongThucTT,
            @RequestParam(name = "tienKhachDua", required = false) BigDecimal tienKhachDua,
            Model model) {

        Integer idGioHang = 1;
        BigDecimal tongTien = gioHangChiTietService.tinhTongTienTheoGioHang(idGioHang);

        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayLap(ngayTao);
        hoaDon.setPhuongThucThanhToan(phuongThucTT);
        hoaDon.setKhachHang(khachHangService.getById(khachHangId));
        hoaDon.setNhanVien(nhanVienService.getById(nhanVienId));
        hoaDon.setTongTien(tongTien);
        hoaDon.setTrangThai(0);

        hoaDonService.save(hoaDon);

        // ✅ Sau khi tạo đơn hàng xong thì xóa giỏ hàng
        gioHangChiTietService.clearByGioHangId(idGioHang);

        return "redirect:/trang-chu/hien-thi";
    }

    @GetMapping("/don-hang/chi-tiet/{id}")
    public String chonHoaDon(@PathVariable("id") Integer id) {
        return "redirect:/trang-chu/hien-thi?hoaDonId=" + id + "#form-don-hang";
    }



}
