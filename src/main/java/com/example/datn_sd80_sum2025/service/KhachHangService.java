package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.KhachHang;

import java.util.List;

public interface KhachHangService {
    List<KhachHang> getAll();
    KhachHang getById(Integer id);
}
