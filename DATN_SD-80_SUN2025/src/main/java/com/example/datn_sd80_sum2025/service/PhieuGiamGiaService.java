package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.PhieuGiamGia;

import java.util.List;

public interface PhieuGiamGiaService {
    List<PhieuGiamGia> getAll();
    List<PhieuGiamGia> searchByTenPhieu(String keyword);
    PhieuGiamGia getById(Integer id);
    void save(PhieuGiamGia pgg);
    void delete(Integer id); // đổi trạng thái
    boolean existsByMaPhieu(String maPhieu);
}
