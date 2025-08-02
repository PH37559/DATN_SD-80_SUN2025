package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.dto.request.GioHangCreate;
import com.example.datn_sd80_sum2025.entity.GioHang;

import java.util.List;

public interface GioHangService {
    List<GioHang> getAll();

    GioHang findById(Integer id);

    GioHang getByIdKhachHang(Integer idKhachHang);

    void store(GioHangCreate gioHang);

    GioHang createAnonymousCart();

}
