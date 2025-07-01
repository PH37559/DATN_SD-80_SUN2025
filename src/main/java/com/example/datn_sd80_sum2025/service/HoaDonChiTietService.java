package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.HoaDonChiTiet;

import java.util.List;

public interface HoaDonChiTietService {
    List<HoaDonChiTiet> getByHoaDonId(Integer hoaDonId); // Lấy danh sách chi tiết theo hóa đơn
}
