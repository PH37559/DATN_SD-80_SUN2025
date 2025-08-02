package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangCreate;
import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangUpdate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangUpdate;
import com.example.datn_sd80_sum2025.entity.DiaChiChiTiet;
import com.example.datn_sd80_sum2025.entity.DiaChiNhanHang;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.service.DiaChiChiTietService;
import com.example.datn_sd80_sum2025.service.DiaChiNhanHangService;
import com.example.datn_sd80_sum2025.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/khach-hang/edit")
public class DiaChiNhanHangController {

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private DiaChiNhanHangService diaChiNhanHangService;

    @Autowired
    private DiaChiChiTietService diaChiChiTietService;

    @GetMapping("/{idKH}/add-dia-chi")
    public String addDiaChiForm(@PathVariable Integer idKH, Model model) {
        DiaChiNhanHangCreate diaChi = new DiaChiNhanHangCreate();
        return renderForm(idKH, diaChi, model, false, "/khach-hang/edit/" + idKH + "/store-dia-chi");
    }

    @PostMapping("/{idKH}/store-dia-chi")
    public String store(@PathVariable Integer idKH,
                        @ModelAttribute("diaChi") @Valid DiaChiNhanHangCreate diaChi,
                        BindingResult result,
                        Model model,
                        @RequestParam(name = "diaChiConfirmFlag", defaultValue = "false") boolean diaChiConfirmFlag,
                        RedirectAttributes redirect) {
        if (result.hasErrors())
            return renderForm(idKH, diaChi, model, false,
                    "/khach-hang/edit/" + idKH + "/store-dia-chi");
        if (!diaChiConfirmFlag) {
            model.addAttribute("showDiaChiConfirmModal", true);
            return renderForm(idKH, diaChi, model, false,
                    "/khach-hang/edit/" + idKH + "/store-dia-chi");
        }
        try {
            DiaChiChiTiet dcct = diaChiNhanHangService.store(idKH, diaChi);
            redirect.addFlashAttribute("showDiaChiResultModal", true);
            redirect.addFlashAttribute("diaChiResultMessage", "Thêm địa chỉ thành công!");
            System.out.print("DiaCHiCHiTiet");
            return "redirect:/khach-hang/edit/" + idKH + "/edit-dia-chi/" + dcct.getDiaChiNhanHang().getId();
        } catch (Exception e) {
            redirect.addFlashAttribute("showDiaChiResultModal", true);
            redirect.addFlashAttribute("diaChiResultMessage", "Thêm địa chỉ thất bại!");
            return "redirect:/khach-hang/edit/" + idKH;
        }
    }

    @GetMapping("/{idKH}/edit-dia-chi/{idDC}")
    public String updateForm(@PathVariable Integer idKH,
                             @PathVariable Integer idDC,
                             Model model,
                             RedirectAttributes redirect) {
        try {
            DiaChiChiTiet dcct = diaChiChiTietService.getDCCTByIdKHAndByIdDC(idKH, idDC);
            DiaChiNhanHang dc = dcct.getDiaChiNhanHang();
            DiaChiNhanHangUpdate diaChi = new DiaChiNhanHangUpdate();
            BeanUtils.copyProperties(dc, diaChi);
            diaChi.setTrangThaiDCCT(dcct.getTrangThai());
            diaChi.setGhiChu(dcct.getGhiChu());
            return renderForm(idKH, diaChi, model, true, "/khach-hang/edit/" + idKH + "/update-dia-chi/" + idDC);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("showDCResultModal", true);
            redirect.addFlashAttribute("dcResultMessage", "Không tìm thấy địa chỉ phù hợp với khách hàng.");
            return "redirect:/khach-hang/edit/" + idKH;
        }
    }

    @PostMapping("/{idKH}/update-dia-chi/{idDC}")
    public String update(@PathVariable Integer idKH,
                         @PathVariable Integer idDC,
                         @ModelAttribute("diaChi") @Valid DiaChiNhanHangUpdate diaChi,
                         BindingResult result,
                         Model model,
                         @RequestParam(name = "diaChiConfirmFlag", defaultValue = "false") boolean diaChiConfirmFlag,
                         RedirectAttributes redirect) {
        if (result.hasErrors())
            return renderForm(idKH, diaChi, model, true,
                    "/khach-hang/edit/" + idKH + "/update-dia-chi/" + idDC);
        if (!diaChiConfirmFlag) {
            model.addAttribute("showDiaChiConfirmModal", true);
            return renderForm(idKH, diaChi, model, true,
                    "/khach-hang/edit/" + idKH + "/update-dia-chi/" + idDC);
        }
        try {
            diaChiNhanHangService.update(idKH, idDC, diaChi);
            redirect.addFlashAttribute("showDiaChiResultModal", true);
            redirect.addFlashAttribute("diaChiResultMessage", "Cập nhật địa chỉ thành công!");
            return "redirect:/khach-hang/edit/" + idKH + "/edit-dia-chi/" + idDC;
        } catch (Exception e) {
            e.printStackTrace();
            redirect.addFlashAttribute("showDiaChiResultModal", true);
            redirect.addFlashAttribute("diaChiResultMessage", "Cập nhật địa chỉ thất bại!");
            return "redirect:/khach-hang/edit/" + idKH + "/edit-dia-chi/" + idDC;
        }
    }

    @PostMapping("/{idKH}/xoa-dia-chi/{idDC}")
    public String delete(@PathVariable Integer idKH,
                         @PathVariable Integer idDC,
                         RedirectAttributes redirect) {
        try {
            diaChiChiTietService.delete(idKH, idDC);
            redirect.addFlashAttribute("showDiaChiResultModal", true);
            redirect.addFlashAttribute("diaChiResultMessage", "Xóa địa chỉ thành công!");
        } catch (Exception e) {
            redirect.addFlashAttribute("showDiaChiResultModal", true);
            redirect.addFlashAttribute("diaChiResultMessage", "Xóa địa chỉ thất bại!");
        }
        return "redirect:/khach-hang/edit/" + idKH;
    }

    private String renderForm(Integer idKH,
                              Object diaChi,
                              Model model,
                              boolean isUpdate,
                              String actionUrl) {
        KhachHang kh = khachHangService.getById(idKH);
        KhachHangUpdate khachHang = new KhachHangUpdate();
        BeanUtils.copyProperties(kh, khachHang);

        List<DiaChiChiTiet> list = diaChiChiTietService.getDCCTByKHId(idKH);

        model.addAttribute("khachHang", khachHang);
        model.addAttribute("diaChi", diaChi);
        model.addAttribute("listDiaChi", list);
        model.addAttribute("isUpdate", isUpdate);
        model.addAttribute("actionDiaChiUrl", actionUrl);
        return "khach-hang/admin/update";
    }
}
