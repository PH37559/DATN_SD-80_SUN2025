package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.ChucVuRepository;
import com.example.datn_sd80_sum2025.service.ChucVuService;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private ChucVuService chucVuService;

    @GetMapping("/hien-thi")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "15") int size,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Integer chucVu,
                       @RequestParam(required = false) Integer trangThai,
                       Model model) {
        Page<NhanVien> listNhanVien = nhanVienService.search(keyword, chucVu, trangThai, page, size);
        model.addAttribute("list", listNhanVien.getContent());
        model.addAttribute("page", page);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", listNhanVien.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("chucVu", chucVu);
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("listChucVu", chucVuService.getAll());
        return "nhan-vien/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("nhanVien", new NhanVienCreate());
        model.addAttribute("listChucVu", chucVuService.getAll());
        model.addAttribute("errors", new HashMap<String, String>());
        model.addAttribute("isUpdate", false);
        model.addAttribute("actionUrl", "/nhan-vien/store");
        return "nhan-vien/add";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute("nhanVien") @Valid NhanVienCreate nhanVien,
                      BindingResult result,
                      @RequestParam(name = "confirmFlag", defaultValue = "false") boolean confirmFlag,
                      Model model,
                      RedirectAttributes redirectAttributes) {
        if (nhanVienService.existsByTaiKhoan(nhanVien.getTenTaiKhoan(), null)) {
            result.rejectValue("tenTaiKhoan", "tenTaiKhoan", "Tài khoản đã tồn tại");
        }
        if (nhanVienService.existsBySdt(nhanVien.getSdt(), null)) {
            result.rejectValue("sdt", "sdt", "Số điện thoại đã tồn tại");
        }
        if (nhanVienService.existsByEmail(nhanVien.getEmail(), null)) {
            result.rejectValue("email", "email", "Email đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("listChucVu", chucVuService.getAll());
            model.addAttribute("isUpdate", false);
            model.addAttribute("actionUrl", "/nhan-vien/store");
            return "nhan-vien/add";
        }

        if (!confirmFlag) {
            model.addAttribute("listChucVu", chucVuService.getAll());
            model.addAttribute("isUpdate", false);
            model.addAttribute("actionUrl", "/nhan-vien/store");
            model.addAttribute("showConfirmModal", true);
            return "nhan-vien/add";
        }

        try {
            nhanVienService.store(nhanVien);
            redirectAttributes.addFlashAttribute("showResultModal", true);
            redirectAttributes.addFlashAttribute("resultMessage", "Thêm nhân viên thành công!");
            return "redirect:/nhan-vien/hien-thi";
        } catch (Exception e) {
            model.addAttribute("listChucVu", chucVuService.getAll());
            model.addAttribute("isUpdate", false);
            model.addAttribute("actionUrl", "/nhan-vien/store");
            model.addAttribute("showResultModal", true);
            model.addAttribute("resultMessage", "Thêm nhân viên thất bại!");
            return "nhan-vien/add";
        }
    }


    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        NhanVien entity = nhanVienService.getById(id);
        NhanVienUpdate nhanVien = new NhanVienUpdate();
        BeanUtils.copyProperties(entity, nhanVien);
        nhanVien.setIdChucVu(entity.getChucVu().getId());
        model.addAttribute("nhanVien", nhanVien);
        model.addAttribute("listChucVu", chucVuService.getAll());
        model.addAttribute("errors", new HashMap<String, String>());
        model.addAttribute("isUpdate", true);
        model.addAttribute("actionUrl", "/nhan-vien/update/" + id);
        return "nhan-vien/add";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute("nhanVien") @Valid NhanVienUpdate nhanVien,
                         BindingResult result,
                         Model model,
                         @RequestParam(name = "confirmFlag", defaultValue = "false") boolean confirmFlag,
                         RedirectAttributes redirectAttributes) {
        if (nhanVienService.existsBySdt(nhanVien.getSdt(), id)) {
            result.rejectValue("sdt", "sdt", "Số điện thoại đã tồn tại");
        }
        if (nhanVienService.existsByEmail(nhanVien.getEmail(), id)) {
            result.rejectValue("email", "email", "Email đã tồn tại");
        }
        if (nhanVienService.existsByTaiKhoan(nhanVien.getTenTaiKhoan(), id)) {
            result.rejectValue("tenTaiKhoan", "tenTaiKhoan", "Tài khoản đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("listChucVu", chucVuService.getAll());
            model.addAttribute("isUpdate", true);
            model.addAttribute("actionUrl", "/nhan-vien/update/" + id);
            return "nhan-vien/add";
        }

        if (!confirmFlag) {
            model.addAttribute("listChucVu", chucVuService.getAll());
            model.addAttribute("isUpdate", true);
            model.addAttribute("actionUrl", "/nhan-vien/update/" + id);
            model.addAttribute("showConfirmModal", true);
            return "nhan-vien/add";
        }

        try {
            nhanVienService.update(id, nhanVien);
            redirectAttributes.addFlashAttribute("showResultModal", true);
            redirectAttributes.addFlashAttribute("resultMessage", "Cập nhật nhân viên thành công!");
            return "redirect:/nhan-vien/hien-thi";
        } catch (Exception e) {
            model.addAttribute("listChucVu", chucVuService.getAll());
            model.addAttribute("isUpdate", true);
            model.addAttribute("actionUrl", "/nhan-vien/update/" + id);
            model.addAttribute("showResultModal", true);
            model.addAttribute("resultMessage", "Cập nhật nhân viên thất bại!");
            return "nhan-vien/add";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        nhanVienService.deleteById(id);
        return "redirect:/nhan-vien/hien-thi";
    }

}
