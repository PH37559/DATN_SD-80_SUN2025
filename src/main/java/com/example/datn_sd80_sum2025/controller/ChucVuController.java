package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.dto.request.ChucVuCreate;
import com.example.datn_sd80_sum2025.dto.request.ChucVuUpdate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.ChucVu;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.service.ChucVuService;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/chuc-vu")
public class ChucVuController {

    @Autowired
    ChucVuService chucVuService;

    @GetMapping("/hien-thi")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "15") int size,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Integer trangThai,
                       Model model) {
        Page<ChucVu> listChucVu = chucVuService.search(keyword, trangThai, page, size);
        model.addAttribute("list", listChucVu.getContent());
        model.addAttribute("page", page);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", listChucVu.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("trangThai", trangThai);
        return "chuc-vu/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("chucVu", new ChucVuCreate());
        model.addAttribute("errors", new HashMap<String, String>());
        model.addAttribute("isUpdate", false);
        model.addAttribute("actionUrl", "/chuc-vu/store");
        return "chuc-vu/add";
    }

    @PostMapping("/store")
    public String add(@ModelAttribute("chucVu") @Valid ChucVuCreate chucVu,
                      BindingResult result,
                      @RequestParam(name = "confirmFlag", defaultValue = "false") boolean confirmFlag,
                      Model model,
                      RedirectAttributes redirectAttributes) {
        if (chucVuService.existByTenChucVu(chucVu.getTenChucVu(), null)) {
            result.rejectValue("tenChucVu", "tenChucVu", "Tên chức vụ đã tồn tại");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("isUpdate", false);
            model.addAttribute("actionUrl", "/chuc-vu/store");
            return "chuc-vu/add";
        }

        if (!confirmFlag) {
            model.addAttribute("isUpdate", false);
            model.addAttribute("actionUrl", "/chuc-vu/store");
            model.addAttribute("showConfirmModal", true);
            return "chuc-vu/add";
        }

        try {
            chucVuService.store(chucVu);
            redirectAttributes.addFlashAttribute("showResultModal", true);
            redirectAttributes.addFlashAttribute("resultMessage", "Thêm chức vụ thành công!");
            return "redirect:/chuc-vu/hien-thi";
        } catch (Exception e) {
            model.addAttribute("isUpdate", false);
            model.addAttribute("actionUrl", "/chuc-vu/store");
            model.addAttribute("showResultModal", true);
            model.addAttribute("resultMessage", "Thêm chức vụ thất bại!");
            return "chuc-vu/add";
        }
    }


    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        ChucVu entity = chucVuService.getById(id);
        ChucVuUpdate chucVu = new ChucVuUpdate();
        BeanUtils.copyProperties(entity, chucVu);

        model.addAttribute("chucVu", chucVu);
        model.addAttribute("errors", new HashMap<String, String>());
        model.addAttribute("isUpdate", true);
        model.addAttribute("actionUrl", "/chuc-vu/update/" + id);
        return "chuc-vu/add";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute("chucVu") @Valid ChucVuUpdate chucVu,
                         BindingResult result,
                         Model model,
                         @RequestParam(name = "confirmFlag", defaultValue = "false") boolean confirmFlag,
                         RedirectAttributes redirectAttributes) {
        if (chucVuService.existByTenChucVu(chucVu.getTenChucVu(), null)) {
            result.rejectValue("tenChucVu", "tenChucVu", "Tên chức vụ đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("isUpdate", true);
            model.addAttribute("actionUrl", "/chuc-vu/update/" + id);
            return "chuc-vu/add";
        }

        if (!confirmFlag) {
            model.addAttribute("isUpdate", true);
            model.addAttribute("actionUrl", "/chuc-vu/update/" + id);
            model.addAttribute("showConfirmModal", true);
            return "chuc-vu/add";
        }

        try {
            chucVuService.update(id, chucVu);
            redirectAttributes.addFlashAttribute("showResultModal", true);
            redirectAttributes.addFlashAttribute("resultMessage", "Cập nhật chức vụ thành công!");
            return "redirect:/chuc-vu/hien-thi";
        } catch (Exception e) {
            model.addAttribute("isUpdate", true);
            model.addAttribute("actionUrl", "/chuc-vu/update/" + id);
            model.addAttribute("showResultModal", true);
            model.addAttribute("resultMessage", "Cập nhật chức vụ thất bại!");
            return "chuc-vu/add";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        chucVuService.deleteById(id);
        return "redirect:/chuc-vu/hien-thi";
    }
}
