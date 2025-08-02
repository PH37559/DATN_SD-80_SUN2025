package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;
import com.example.datn_sd80_sum2025.dto.TopSanPhamDTO;


import java.util.List;

public interface ThongKeService {
    List<DoanhThuDTO> thongKeDoanhThu(int year, Integer month);
//    List<ThongKeSanPhamDTO> thongKeSanPham();

//    List<TopSanPhamDTO> thongKeTopSanPham(int year, Integer month);
    void exportDoanhThuPDF(Integer year, Integer month);

}
