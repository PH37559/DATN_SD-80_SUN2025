package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.NhanVien;

import java.util.List;

public interface NhanVienService {
    List<NhanVien> getAll();
    NhanVien getById(Integer id);
    NhanVien getOne(Integer id);               // bổ sung
    NhanVien store(NhanVienCreate nhanVien);   // bổ sung
    NhanVien update(NhanVienUpdate nhanVien);  // bổ sung
    void deleteById(Integer id);               // bổ sung
}
