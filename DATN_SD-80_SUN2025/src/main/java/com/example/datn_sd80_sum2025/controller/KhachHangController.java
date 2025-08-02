package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangCreate;
import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangUpdate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangCreate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangUpdate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.DiaChiChiTiet;
import com.example.datn_sd80_sum2025.entity.DiaChiNhanHang;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.ChucVuRepository;
import com.example.datn_sd80_sum2025.service.DiaChiChiTietService;
import com.example.datn_sd80_sum2025.service.DiaChiNhanHangService;
import com.example.datn_sd80_sum2025.service.KhachHangService;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private DiaChiNhanHangService diaChiNhanHangService;

    @Autowired
    private DiaChiChiTietService diaChiChiTietService;

    @GetMapping("/hien-thi")
    public String hienThi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam Map<String, String> params,
            Model model
    ) {
        String keyword = params.get("keyword");
        Boolean gioiTinh = params.get("gioiTinh") != null && !params.get("gioiTinh").isBlank()
                ? Boolean.parseBoolean(params.get("gioiTinh"))
                : null;
        Integer trangThai = parseInteger(params.get("trangThai"));
        LocalDate ngaySinhFrom = parseDate(params.get("ngaySinhFrom"));
        LocalDate ngaySinhTo = parseDate(params.get("ngaySinhTo"));
        String thanhPho = params.get("thanhPho");
        String quanHuyen = params.get("quanHuyen");

        if (thanhPho != null) {
            thanhPho = thanhPho.replaceAll("(?i)^Thành phố |^Tỉnh ", "").trim();
        }
        if (quanHuyen != null) {
            quanHuyen = quanHuyen.replaceAll("(?i)^Quận |^Huyện |^Thành phố ", "").trim();
        }

        Page<KhachHang> list = khachHangService.search(
                keyword, gioiTinh, trangThai, ngaySinhFrom, ngaySinhTo, thanhPho, quanHuyen,
                PageRequest.of(page, size)
        );

        model.addAttribute("list", list.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", list.getTotalPages());

        model.addAttribute("keyword", keyword);
        model.addAttribute("gioiTinh", gioiTinh);
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("ngaySinhFrom", params.get("ngaySinhFrom"));
        model.addAttribute("ngaySinhTo", params.get("ngaySinhTo"));
        model.addAttribute("thanhPho", thanhPho);
        model.addAttribute("quanHuyen", quanHuyen);

        return "khach-hang/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("khachHang", new KhachHangCreate());
        model.addAttribute("diaChi", new DiaChiNhanHangCreate());
        model.addAttribute("errors", new HashMap<String, String>());
        model.addAttribute("isUpdate", false);
        model.addAttribute("actionUrl", "/khach-hang/store");
        return "khach-hang/add";
    }

    @PostMapping("/store")
    public String add(@ModelAttribute("khachHang") @Valid KhachHangCreate khachHang,
                      BindingResult khErrors,
                      @ModelAttribute("diaChi") @Valid DiaChiNhanHangCreate diaChi,
                      BindingResult dcErrors,
                      @RequestParam(name = "confirmFlag", defaultValue = "false") boolean confirmFlag,
                      Model model,
                      RedirectAttributes redirectAttributes) {
        if (khachHangService.existsBySdt(khachHang.getSdt(), null)) {
            khErrors.rejectValue("sdt", "sdt", "Số điện thoại đã tồn tại");
        }
        if (khachHangService.existsByEmail(khachHang.getEmail(), null)) {
            khErrors.rejectValue("email", "email", "Email đã tồn tại");
        }

        if (khErrors.hasErrors()) {
            model.addAttribute("isUpdate", false);
            return "khach-hang/add";
        }

        if (khErrors.hasErrors() || dcErrors.hasErrors()) {
            return "khach-hang/add";
        }

        if (!confirmFlag) {
            model.addAttribute("showConfirmModal", true);
            return "khach-hang/add";
        }

        try {
            KhachHang kh = khachHangService.store(khachHang);
            diaChiNhanHangService.store(kh.getId(), diaChi);

            redirectAttributes.addFlashAttribute("showResultModal", true);
            redirectAttributes.addFlashAttribute("resultMessage", "Thêm khách hàng thành công!");
            return "redirect:/khach-hang/hien-thi";
        } catch (Exception e) {
            System.out.print(e.getMessage());
            model.addAttribute("showResultModal", true);
            model.addAttribute("resultMessage", "Thêm khách hàng thất bại!");
            return "khach-hang/add";
        }
    }

    @GetMapping("/edit/{idKH}")
    public String updateForm(@PathVariable Integer idKH,
                             Model model) {
        KhachHang kh = khachHangService.getById(idKH);
        KhachHangUpdate khachHang = new KhachHangUpdate();
        BeanUtils.copyProperties(kh, khachHang);

        renderForm(idKH, model);
        model.addAttribute("khachHang", khachHang);
        model.addAttribute("isUpdate", null);
        return "khach-hang/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute("khachHang") @Valid KhachHangUpdate khachHang,
                         BindingResult result,
                         Model model,
                         @RequestParam(name = "khachHangConfirmFlag", defaultValue = "false") boolean khachHangConfirmFlag,
                         RedirectAttributes redirectAttributes) {
        if (khachHangService.existsBySdt(khachHang.getSdt(), id)) {
            result.rejectValue("sdt", "sdt", "Số điện thoại đã tồn tại");
        }
        if (khachHangService.existsByEmail(khachHang.getEmail(), id)) {
            result.rejectValue("email", "email", "Email đã tồn tại");
        }

        if (result.hasErrors()) {
            return renderForm(id, model);
        }

        if (!khachHangConfirmFlag) {
            model.addAttribute("showKhachHangConfirmModal", true);
            return renderForm(id, model);
        }

        try {
            khachHangService.update(id, khachHang);
            redirectAttributes.addFlashAttribute("showKhachHangResultModal", true);
            redirectAttributes.addFlashAttribute("khachHangResultMessage", "Cập nhật khách hàng thành công!");
            return "redirect:/khach-hang/edit/" + id;
        } catch (Exception e) {
            renderForm(id, model);
            model.addAttribute("showKhachHangResultModal", true);
            model.addAttribute("khachHangResultMessage", "Cập nhật khách hàng thất bại!");
            return "khach-hang/update";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        khachHangService.deleteById(id);
        return "redirect:/khach-hang/hien-thi";
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

    private String cleanPrefix(String value) {
        return value == null ? null :
                value.replaceAll("(?i)^Thành phố |^Tỉnh |^Quận |^Huyện |^Thị xã |^Phường |^Xã ", "").trim();
    }

    private String renderForm(Integer id, Model model) {
        List<DiaChiChiTiet> listDiaChi = diaChiChiTietService.getDCCTByKHId(id);

        DiaChiNhanHangUpdate diaChi = new DiaChiNhanHangUpdate();
        if (!listDiaChi.isEmpty()) {
            DiaChiChiTiet dcct = listDiaChi.get(0);
            BeanUtils.copyProperties(dcct.getDiaChiNhanHang(), diaChi);
            diaChi.setGhiChu(dcct.getGhiChu());
            diaChi.setTrangThaiDCCT(dcct.getTrangThai());
        }

        model.addAttribute("isUpdate", true);
        model.addAttribute("diaChi", diaChi);
        model.addAttribute("listDiaChi", listDiaChi);
        model.addAttribute("errors", new HashMap<String, String>());
        return "khach-hang/update";
    }

}
