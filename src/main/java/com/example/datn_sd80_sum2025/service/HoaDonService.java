package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.NhanVien;

import java.util.List;

public interface HoaDonService {
    List<HoaDon> getAll();
    HoaDon getById(Integer id);
    List<HoaDon> getDonHangCho();
    List<HoaDon> getTop5DonHangCho();
    HoaDon save(HoaDon hoaDon);
    List<HoaDon> getAllDaThanhToan();
    void add(HoaDon hoaDon);
    HoaDon taoDonHang(KhachHang khachHang, NhanVien nhanVien);

}
