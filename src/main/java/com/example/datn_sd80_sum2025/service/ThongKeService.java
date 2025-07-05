package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.DoanhThu;

import java.util.List;

public interface ThongKeService {
    List<DoanhThu> thongKeDoanhThu(Integer year, Integer month);
    void exportDoanhThuPDF(Integer year, Integer month);

}
