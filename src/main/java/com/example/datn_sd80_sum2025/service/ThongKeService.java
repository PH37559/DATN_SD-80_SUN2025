package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;
import com.example.datn_sd80_sum2025.dto.TopKhachHangDTO;
import com.example.datn_sd80_sum2025.dto.TopSanPhamDTO;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ThongKeService {
    List<DoanhThuDTO> thongKeDoanhThu(int year, Integer month);
//    List<ThongKeSanPhamDTO> thongKeSanPham();

    //top san pham
    Page<TopSanPhamDTO> thongKeTopSanPham(Integer year, Integer month, int page, int size);

    //top khach hang
    Page<TopKhachHangDTO> thongKeTopKhachHang(Integer year, Integer month, int page, int size);



    //    List<TopSanPhamDTO> thongKeTopSanPham(int year, Integer month);

    byte[] exportDoanhThuPDF(Integer year, Integer month);
    byte[] exportTopSanPhamPDF(Integer year, Integer month);
    byte[] exportTopKhachHangPDF(Integer year, Integer month);

}
