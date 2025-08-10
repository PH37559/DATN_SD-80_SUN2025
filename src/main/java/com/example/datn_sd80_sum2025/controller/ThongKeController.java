package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;
import com.example.datn_sd80_sum2025.dto.TonKhoDTO;
import com.example.datn_sd80_sum2025.dto.TopKhachHangDTO;
import com.example.datn_sd80_sum2025.dto.TopSanPhamDTO;
import com.example.datn_sd80_sum2025.service.ThongKeService;
import com.example.datn_sd80_sum2025.service.sanpham.SachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/thong-ke")
public class ThongKeController {

    @Autowired
    private ThongKeService thongKeService;

    @Autowired
    private SachService sachService;
    @GetMapping("/doanh-thu")
    public String doanhThu(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {

        if (year == null) {
            year = java.time.LocalDate.now().getYear();
        }

        List<DoanhThuDTO> list = thongKeService.thongKeDoanhThu(year, month);

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("list", list);
        return "thong-ke/doanh-thu";
    }

    @GetMapping("/doanh-thu/export")
    public ResponseEntity<byte[]> exportPDF(
            @RequestParam Integer year,
            @RequestParam(required = false) Integer month) {

        byte[] pdfBytes = thongKeService.exportDoanhThuPDF(year, month);

        String fileName = "doanh-thu-" + year + (month != null ? "-" + month : "") + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(pdfBytes);
    }
    @GetMapping("/top-san-pham/export")
    public ResponseEntity<byte[]> exportTopSanPhamPDF(
            @RequestParam Integer year,
            @RequestParam(required = false) Integer month) {

        byte[] pdfBytes = thongKeService.exportTopSanPhamPDF(year, month);

        String fileName = "top-san-pham-" + year + (month != null ? "-" + month : "") + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(pdfBytes);
    }
    @GetMapping("/top-khach-hang/export")
    public ResponseEntity<byte[]> exportTopKhachHangPDF(
            @RequestParam Integer year,
            @RequestParam(required = false) Integer month) {

        byte[] pdfBytes = thongKeService.exportTopKhachHangPDF(year, month);

        String fileName = "top-khach-hang-" + year + (month != null ? "-" + month : "") + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(pdfBytes);
    }




    //    @GetMapping("/san-pham")
//    public String thongKeSanPham(
//            @RequestParam(required = false) Integer year,
//            @RequestParam(required = false) Integer month,
//            Model model) {
//
//        if (year == null) {
//            year = java.time.LocalDate.now().getYear();
//        }
//
//        List<ThongKeSanPhamDTO> list = thongKeService.thongKeSanPham();
//
//        model.addAttribute("year", year);
//        model.addAttribute("month", month);
//        model.addAttribute("list", list);
//        return "thong-ke/san-pham";  // sẽ tạo file HTML này
//    }
    //top san pham
@GetMapping("/top-san-pham")
public String hienThiTopSanPham(
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) Integer month,
        @RequestParam(defaultValue = "0") int page,
        Model model) {

    // Nếu không chọn thì mặc định là năm hiện tại
    if (year == null) {
        year = LocalDate.now().getYear();
    }

    // Gọi service lọc top sản phẩm theo tháng/năm
    Page<TopSanPhamDTO> topSanPham = thongKeService.thongKeTopSanPham(year, month, page,1000);

    model.addAttribute("topSanPham", topSanPham);
    model.addAttribute("year", year);
    model.addAttribute("month", month);

    return "thong-ke/topsanpham";
}

    //top khach hang
    @GetMapping("/top-khach-hang")
    public String topKhachHang(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        if (year == null) {
            year = LocalDate.now().getYear();
        }

        Page<TopKhachHangDTO> result = thongKeService.thongKeTopKhachHang(year, month, page, 1000);
        model.addAttribute("topKhachHang", result.getContent());
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        return "thong-ke/topkhachhang";
    }



//@GetMapping("/tonkho")
//public String hienThiTonKho(Model model) {
//    List<TonKhoDTO> list = sachService.thongKeTonKho();
//    model.addAttribute("listTonKho", list);
//    return "thong-ke/tonkho";
//}
}
