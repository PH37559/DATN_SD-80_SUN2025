package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangCreate;
import com.example.datn_sd80_sum2025.dto.request.GioHangChiTietDTO;
import com.example.datn_sd80_sum2025.dto.request.GioHangCreate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangCreate;
import com.example.datn_sd80_sum2025.entity.DiaChiChiTiet;
import com.example.datn_sd80_sum2025.entity.DiaChiNhanHang;
import com.example.datn_sd80_sum2025.entity.GioHang;
import com.example.datn_sd80_sum2025.entity.GioHangChiTiet;
import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.HoaDonChiTiet;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.Sach;
import com.example.datn_sd80_sum2025.repository.SachRepository;
import com.example.datn_sd80_sum2025.service.DiaChiChiTietService;
import com.example.datn_sd80_sum2025.service.DiaChiNhanHangService;
import com.example.datn_sd80_sum2025.service.GioHangChiTietService;
import com.example.datn_sd80_sum2025.service.GioHangService;
import com.example.datn_sd80_sum2025.service.HoaDonChiTietService;
import com.example.datn_sd80_sum2025.service.HoaDonService;
import com.example.datn_sd80_sum2025.service.KhachHangService;
import com.example.datn_sd80_sum2025.service.sanpham.SachService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/home")
public class MainController {
    @Autowired
    SachService sachService;

    @Autowired
    KhachHangService khachHangService;

    @Autowired
    DiaChiNhanHangService diaChiNhanHangService;

    @Autowired
    DiaChiChiTietService diaChiChiTietService;

    @Autowired
    GioHangService gioHangService;

    @Autowired
    GioHangChiTietService gioHangChiTietService;

    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    HoaDonChiTietService hoaDonChiTietService;

    @ModelAttribute("khachHang")
    public KhachHangCreate defaultKhachHang() {
        return new KhachHangCreate();
    }

    @GetMapping("/sach")
    public String getAllForClient(
            @RequestParam(name = "key", required = false, defaultValue = "") String key,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            Model model
    ) {
        Page<Sach> pageResult = sachService.searchByTenSach(key, page, size);

        model.addAttribute("list", pageResult.getContent());
        model.addAttribute("totalPage", pageResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("key", key);

        return "khach-hang/customer/san-pham/list";
    }

    @GetMapping("/san-pham-chi-tiet/{id}")
    public String getDetail(@PathVariable Integer id, Model model, HttpSession session) {
        Sach sach = sachService.getById(id);
        GioHang gioHang;
        int tongSach = 0;
        KhachHang khachHang = (KhachHang) session.getAttribute("session");
        if (khachHang != null) {
            gioHang = gioHangService.getByIdKhachHang(khachHang.getId());
            tongSach = gioHangChiTietService.countItemsInCart(gioHang.getId());
            model.addAttribute("cartItemCount", tongSach);
        }
        model.addAttribute("sach", sach);
        return "khach-hang/customer/san-pham/detail";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "khach-hang/customer/security/form";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("keyword") String keyword,
            @RequestParam("matKhau") String matKhau,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        KhachHang khachHang = khachHangService.getKhachHangBySdtOrEmailOrTenTaiKhoan(keyword, matKhau);

        if (khachHang != null) {
            session.setAttribute("khachHang", khachHang);

            GioHangCreate anonymousCart = (GioHangCreate) session.getAttribute("gioHang");

            if (anonymousCart != null && anonymousCart.getIdKhachHang() == null) {
                anonymousCart.setIdKhachHang(khachHang.getId());
                gioHangService.store(anonymousCart);
            }


            return "redirect:/home/sach";

        } else {
            redirectAttributes.addFlashAttribute("error", "Sai thông tin đăng nhập!");
            redirectAttributes.addFlashAttribute("khachHang", new KhachHangCreate());
            return "redirect:/home/login";
        }
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("khachHang") @Valid KhachHangCreate khachHang,
            BindingResult khErrors
    ) {
        if (khachHangService.existsBySdt(khachHang.getSdt(), null)) {
            khErrors.rejectValue("sdt", "sdt", "Số điện thoại đã tồn tại");
        }
        if (khachHangService.existsByEmail(khachHang.getEmail(), null)) {
            khErrors.rejectValue("email", "email", "Email đã tồn tại");
        }
        if (khachHangService.existsByTenTaiKhoan(khachHang.getTenTaiKhoan(), null)) {
            khErrors.rejectValue("tenTaiKhoan", "tenTaiKhoan", "Tên tài khoản đã tồn tại");
        }
        if (khErrors.hasErrors()) {
            return "khach-hang/customer/security/form";
        }
        khachHang.setTrangThai(1);
        KhachHang kh = khachHangService.store(khachHang);
        GioHangCreate gioHang = new GioHangCreate(kh.getId(), LocalDate.now(), LocalDate.now(), null, 1);
        gioHangService.store(gioHang);
        return "redirect:/home/sach";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home/sach";
    }

    @GetMapping("/orders")
    public String showOrder(
            @RequestParam(value = "status", required = false) Integer trangThai,
            Model model,
            HttpSession session
    ) {
        KhachHang kh = (KhachHang) session.getAttribute("khachHang");
        List<HoaDon> listHD;
        int soDon = 0;

        if (trangThai == null) {
            listHD = hoaDonService.getByIdKH(kh.getId());
            soDon = listHD.size();
        } else {
            listHD = hoaDonService.getByIdKHAndTrangThai(kh.getId(), trangThai);
        }
        Map<Integer, List<HoaDonChiTiet>> chiTietMap = new HashMap<>();
        Map<Integer, String> sachIdsMap = new HashMap<>();

        for (HoaDon hd : listHD) {
            List<HoaDonChiTiet> chiTietList = hoaDonChiTietService.getByHoaDonId(hd.getId());
            chiTietMap.put(hd.getId(), chiTietList);

            String ids = chiTietList.stream()
                    .map(ct -> String.valueOf(ct.getSach().getId()))
                    .collect(Collectors.joining(","));
            sachIdsMap.put(hd.getId(), ids);
        }

        model.addAttribute("listHD", listHD);
        model.addAttribute("chiTietMap", chiTietMap);
        model.addAttribute("sachIdsMap", sachIdsMap);
        model.addAttribute("currentStatus", trangThai);

        model.addAttribute("countAll", soDon);
        model.addAttribute("countStatus0", hoaDonService.countByIdKHAndTrangThai(kh.getId(), 0));
        model.addAttribute("countStatus1", hoaDonService.countByIdKHAndTrangThai(kh.getId(), 1));
        model.addAttribute("countStatus2", hoaDonService.countByIdKHAndTrangThai(kh.getId(), 2));
        model.addAttribute("countStatus3", hoaDonService.countByIdKHAndTrangThai(kh.getId(), 3));

        return "khach-hang/customer/don-hang/list";
    }

    @GetMapping("/orders/detail/{id}")
    public String showOrderDetail(
            @PathVariable Integer id,
            Model model
    ){
        HoaDon hd = hoaDonService.getById(id);
        List<HoaDonChiTiet> listHDCT = hoaDonChiTietService.getByHoaDonId(id);
        System.out.print("HDCT: "+ listHDCT.get(0).getHoaDon().getDiaChiNhanHang().getHoTen());
        model.addAttribute("hoaDon", hd);
        model.addAttribute("listHDCT", listHDCT);
        return "khach-hang/customer/don-hang/detail";
    }

    @PostMapping("/orders/cancel/{id}")
    public String cancelOrder(@PathVariable Integer id) {
        HoaDon hd = hoaDonService.getById(id);
        hd.setTrangThai(3);
        hoaDonService.save(hd);

        List<HoaDonChiTiet> chiTietList = hoaDonChiTietService.getByHoaDonId(id);

        for (HoaDonChiTiet hdct : chiTietList) {
            Sach sach = hdct.getSach();
            Integer soLuongMua = hdct.getSoLuong();
            sach.setSoLuong(sach.getSoLuong() + soLuongMua);
            sachService.save(sach);
        }
        return "redirect:/home/orders";
    }

    @GetMapping("/checkout")
    public String showCheckOutPage(
            @RequestParam(name = "selectedIds", required = false) String idsStr,
            @RequestParam(name = "quantities", required = false) String quantitiesStr,
            Model model,
            HttpSession session) {

        if (idsStr == null || idsStr.isEmpty()) {
            return "redirect:/cart";
        }

        List<Integer> selectedIds = Arrays.stream(idsStr.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> quantities = (quantitiesStr != null && !quantitiesStr.isEmpty())
                ? Arrays.stream(quantitiesStr.split(",")).map(Integer::parseInt).collect(Collectors.toList())
                : new ArrayList<>();

        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        if (khachHang != null) {
            GioHang gioHang = gioHangService.getByIdKhachHang(khachHang.getId());
            List<GioHangChiTiet> listGHCT = new ArrayList<>();

            if (!quantities.isEmpty() && quantities.size() == selectedIds.size()) {
                for (int i = 0; i < selectedIds.size(); i++) {
                    Sach sach = sachService.getById(selectedIds.get(i));
                    if (sach == null) continue;

                    GioHangChiTiet temp = new GioHangChiTiet();
                    temp.setSach(sach);
                    temp.setSoLuong(quantities.get(i));
                    temp.setDonGia(sach.getGiaBan());
                    listGHCT.add(temp);
                }
            }else {
                listGHCT = gioHangChiTietService.getByGioHangId(gioHang.getId())
                        .stream()
                        .filter(item -> selectedIds.contains(item.getSach().getId()))
                        .collect(Collectors.toList());
            }

            session.setAttribute("selectedGHCT", listGHCT);

            model.addAttribute("gioHang", gioHang);
            model.addAttribute("listGHCT", listGHCT);
            model.addAttribute("thanhTienText", listGHCT.stream()
                    .map(item -> item.getDonGia().multiply(BigDecimal.valueOf(item.getSoLuong())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            // Lấy địa chỉ nếu có
            List<DiaChiNhanHang> listDiaChi = diaChiNhanHangService.getByIdKhachHang(khachHang.getId());
            if (!listDiaChi.isEmpty()) {
                model.addAttribute("diaChi", listDiaChi.get(0));
            } else {
                model.addAttribute("diaChi", new DiaChiNhanHangCreate());
            }
            model.addAttribute("hoaDon", new HoaDon());
        }

        return "khach-hang/customer/don-hang/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(
            @ModelAttribute("diaChi") @Valid DiaChiNhanHangCreate diaChi,
            BindingResult dcErrors,
            @ModelAttribute("hoaDon") HoaDon hoaDon,
            @ModelAttribute("hoaDonChiTiet") HoaDonChiTiet hoaDonChiTiet,
            HttpSession session,
            Model model
    ) {
        KhachHang kh = (KhachHang) session.getAttribute("khachHang");

        //Địa chỉ
        diaChi.setTrangThaiDCCT(1);
        if (dcErrors.hasErrors()) {
            System.out.println("Có lỗi validate địa chỉ:");
            dcErrors.getAllErrors().forEach(e -> System.out.println(e.getDefaultMessage()));
            model.addAttribute("listGHCT", session.getAttribute("selectedGHCT")); // để hiện lại
            return "khach-hang/customer/don-hang/checkout";
        }
        DiaChiChiTiet dcct = diaChiNhanHangService.store(kh.getId(), diaChi);

        //List GHCT lấy từ session
        List<GioHangChiTiet> listGHCT = (List<GioHangChiTiet>) session.getAttribute("selectedGHCT");
        System.out.print("GHCT" + listGHCT);
        if (listGHCT == null || listGHCT.isEmpty()) {
            return "redirect:/cart";
        }

        //Tạo hóa đơn
        hoaDon.setKhachHang(kh);
        hoaDon.setNgayLap(LocalDate.now());
        hoaDon.setDiaChiNhanHang(dcct.getDiaChiNhanHang());
        hoaDon.setTrangThai(0); //chờ xác nhận
        HoaDon hoaDonSaved = hoaDonService.save(hoaDon);

        // Tạo hóa đơn chi tiết
        for (GioHangChiTiet item : listGHCT) {
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setHoaDon(hoaDonSaved);
            hdct.setSach(item.getSach());
            hdct.setSoLuong(item.getSoLuong());
            hdct.setDonGia(item.getDonGia());
            hdct.setThanhTien(item.getDonGia().multiply(BigDecimal.valueOf(item.getSoLuong())));
            hdct.setTrangThai(1);
            hoaDonChiTietService.save(hdct);

            Sach sach = item.getSach();
            int soLuongCon = sach.getSoLuong() - item.getSoLuong();
            if(soLuongCon<0) soLuongCon =0;
            sach.setSoLuong(soLuongCon);
            sachService.save(sach);
        }

        // Xóa GHCT đã mua khỏi DB
        gioHangChiTietService.deleteAll(listGHCT);

        // Xóa khỏi session
        session.removeAttribute("selectedGHCT");

        return "redirect:/home/orders";
    }
    
}
