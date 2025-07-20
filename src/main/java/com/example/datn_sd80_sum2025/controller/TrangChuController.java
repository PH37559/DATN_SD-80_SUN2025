package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.entity.*;
import com.example.datn_sd80_sum2025.repository.GioHangRepository;
import com.example.datn_sd80_sum2025.repository.HoaDonRepository;
import com.example.datn_sd80_sum2025.repository.SachRepository;
import com.example.datn_sd80_sum2025.service.*;
import com.example.datn_sd80_sum2025.service.sanpham.SachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/trang-chu")
public class TrangChuController {

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private SachRepository sachRepository;

    @Autowired
    private SachService sachService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping("/hien-thi")
    public String hienThiTrangChu(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "") String keyword,
                                  @RequestParam(name = "hoaDonId", required = false) Integer hoaDonId) {

        int pageSize = 10;
        Page<Sach> sachPage = keyword != null && !keyword.isBlank()
                ? sachService.searchByTenSach(keyword, page, pageSize)
                : sachService.getAllPaged(page, pageSize);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String tenTaiKhoan = auth.getName();
        NhanVien nhanVien = nhanVienService.findByTenTaiKhoan(tenTaiKhoan);
        model.addAttribute("nhanVienDangNhap", nhanVien);

        model.addAttribute("sanPhamList", sachPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sachPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("donHangChoList", hoaDonService.getDonHangCho());
        model.addAttribute("listKhachHang", khachHangService.getAll());
        model.addAttribute("listNhanVien", nhanVienService.getAll());
        model.addAttribute("ngayHienTai", LocalDate.now());

        if (hoaDonId != null) {
            HoaDon hoaDon = hoaDonService.getById(hoaDonId);
            model.addAttribute("hoaDon", hoaDon);
            List<HoaDonChiTiet> chiTietList = hoaDonChiTietService.getByHoaDonId(hoaDonId);
            model.addAttribute("gioHangList", chiTietList);
        } else {
            model.addAttribute("gioHangList", gioHangChiTietService.getAll());
            gioHangChiTietService.clearTatCa();
        }

        return "trang_chu/home";
    }
    @PostMapping("/them-vao-gio")
    public String themSanPham(@RequestParam Integer idSach,
                              @RequestParam Integer soLuong,
                              @RequestParam(required = false) Integer hoaDonId,
                              RedirectAttributes redirectAttributes) {

        boolean daCapNhat = sachService.capNhatSoLuongSauKhiThem(idSach, soLuong);
        if (!daCapNhat) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không đủ số lượng hoặc đã hết hàng.");
            if (hoaDonId != null) {
                return "redirect:/trang-chu/hien-thi?hoaDonId=" + hoaDonId + "#form-don-hang";
            }
            return "redirect:/trang-chu/hien-thi";
        }
        if (hoaDonId != null) {
            hoaDonChiTietService.themSanPham(hoaDonId, idSach, soLuong);
            return "redirect:/trang-chu/hien-thi?hoaDonId=" + hoaDonId + "#form-don-hang";
        }
        gioHangChiTietService.themSanPham(1, idSach, soLuong);
        return "redirect:/trang-chu/hien-thi";
    }

    @PostMapping("/xoa")
    public String xoaSanPham(@RequestParam Integer idSach,
                             @RequestParam(required = false) Integer idGioHang,
                             @RequestParam(required = false) Integer hoaDonId) {

        if (hoaDonId != null) {
            int soLuong = hoaDonChiTietService.getSoLuongByHoaDonIdAndSachId(hoaDonId, idSach);
            sachService.congSoLuongSauKhiXoa(idSach, soLuong);
            hoaDonChiTietService.xoaSanPham(hoaDonId, idSach);
            return "redirect:/trang-chu/hien-thi?hoaDonId=" + hoaDonId + "#form-don-hang";
        }
        int gid = (idGioHang != null) ? idGioHang : 1;
        GioHang gioHang = gioHangRepository.findById(gid).orElseThrow();
        Sach sach = sachRepository.findById(idSach).orElseThrow();
        int soLuong = gioHangChiTietService.getSoLuongByGioHangIdAndSachId(gid, idSach);
        sachService.congSoLuongSauKhiXoa(idSach, soLuong);
        gioHangChiTietService.delete(new GioHangChiTietId(gioHang, sach));
        return "redirect:/trang-chu/hien-thi";
    }


    @PostMapping("/don-hang/tao")
    public String taoDonHang(@RequestParam("ngayTao") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayTao,
                             @RequestParam("khachHangId") Integer khachHangId,
                             @RequestParam("nhanVienId") Integer nhanVienId,
                             @RequestParam("phuongThucTT") String phuongThucTT,
                             @RequestParam(name = "tienKhachDua", required = false) BigDecimal tienKhachDua) {

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

        List<GioHangChiTiet> gioHang = gioHangChiTietService.getAll();
        for (GioHangChiTiet ghct : gioHang) {
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setHoaDon(hoaDon);
            hdct.setSach(ghct.getSach());
            hdct.setSoLuong(ghct.getSoLuong());
            hdct.setDonGia(ghct.getDonGia());
            hdct.setThanhTien(ghct.getDonGia().multiply(BigDecimal.valueOf(ghct.getSoLuong())));
            hdct.setTrangThai(1);
            hoaDonChiTietService.save(hdct);
        }

        gioHangChiTietService.clearTatCa();

        return "redirect:/trang-chu/hien-thi";
    }

    @GetMapping("/don-hang/chi-tiet/{id}")
    public String chonHoaDon(@PathVariable("id") Integer id) {
        return "redirect:/trang-chu/hien-thi?hoaDonId=" + id + "#form-don-hang";
    }
    @PostMapping("/sua-so-luong")
    public String suaSoLuong(@RequestParam Integer idSach,
                             @RequestParam Integer soLuongMoi,
                             @RequestParam(required = false, defaultValue = "1") Integer idGioHang,
                             @RequestParam(required = false) Integer hoaDonId,
                             @RequestParam(required = false, defaultValue = "0") Integer page) {

        if (hoaDonId != null) {
            int soLuongCu = hoaDonChiTietService.getSoLuongByHoaDonIdAndSachId(hoaDonId, idSach);
            int chenhLech = soLuongMoi - soLuongCu;
            boolean ok = sachService.capNhatSoLuongSauKhiThem(idSach, chenhLech);
            if (!ok) {
                return "redirect:/trang-chu/hien-thi?page=" + page + "&hoaDonId=" + hoaDonId + "&error=quantity";
            }
            hoaDonChiTietService.capNhatSoLuong(hoaDonId, idSach, soLuongMoi);
            return "redirect:/trang-chu/hien-thi?page=" + page + "&hoaDonId=" + hoaDonId;
        }
        int soLuongCu = gioHangChiTietService.getSoLuongByGioHangIdAndSachId(idGioHang, idSach);
        int chenhLech = soLuongMoi - soLuongCu;
        boolean ok = sachService.capNhatSoLuongSauKhiThem(idSach, chenhLech);
        if (!ok) {
            return "redirect:/trang-chu/hien-thi?page=" + page + "&error=quantity";
        }
        gioHangChiTietService.capNhatSoLuong(idGioHang, idSach, soLuongMoi);
        return "redirect:/trang-chu/hien-thi?page=" + page;
    }

    @PostMapping("/hoa-don/hoan-thanh/{id}")
    public String hoanThanhHoaDon(@PathVariable("id") Integer id) {
        HoaDon hoaDon = hoaDonService.getById(id);
        if (hoaDon != null) {
            hoaDon.setTrangThai(1);
            hoaDonService.save(hoaDon);
        }
        return "redirect:/trang-chu/hien-thi";
    }
    @PostMapping("/hoa-don/huy/{id}")
    public String huyHoaDon(@PathVariable("id") Integer id) {
        HoaDon hoaDon = hoaDonService.getById(id);
        if (hoaDon != null) {
            hoaDon.setTrangThai(2);
            hoaDonService.save(hoaDon);
        }
        return "redirect:/trang-chu/hien-thi";
    }

}
