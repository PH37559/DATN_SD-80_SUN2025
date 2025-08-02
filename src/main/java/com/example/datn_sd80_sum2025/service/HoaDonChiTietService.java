package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.HoaDonChiTiet;

import java.util.List;

public interface HoaDonChiTietService {
    List<HoaDonChiTiet> getByHoaDonId(Integer hoaDonId);
    void save(HoaDonChiTiet hoaDonChiTiet);
    void themSanPham(Integer hoaDonId, Integer idSach, Integer soLuong);
    void xoaSanPham(Integer hoaDonId, Integer idSach);
    void capNhatSoLuong(Integer hoaDonId, Integer sachId, Integer soLuongMoi);
    int getSoLuongByHoaDonIdAndSachId(Integer hoaDonId, Integer sachId);

}
