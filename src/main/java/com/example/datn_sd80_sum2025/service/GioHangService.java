package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.GioHang;

import java.util.List;

public interface GioHangService {
    List<GioHang> getAll();
    GioHang findById(Integer id);


}
