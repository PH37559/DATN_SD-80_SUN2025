package com.example.datn_sd80_sum2025.service;


import com.example.datn_sd80_sum2025.entity.HoaDon;

import java.util.List;

public interface HoaDonService {
    List<HoaDon> getAll();

    HoaDon getById(Integer id);

    List<HoaDon> getDonHangCho();

    HoaDon save(HoaDon hoaDon);

    List<HoaDon> getAllDaThanhToan();

    void add(HoaDon hoaDon);

    List<HoaDon> getByIdKH(Integer idKH);

    List<HoaDon> getByIdKHAndTrangThai(Integer idKH, Integer trangThai);

    int countByIdKHAndTrangThai(Integer idKH, Integer trangThai);

}
