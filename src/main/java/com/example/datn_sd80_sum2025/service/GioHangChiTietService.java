package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.GioHang;
import com.example.datn_sd80_sum2025.entity.GioHangChiTiet;
import com.example.datn_sd80_sum2025.entity.GioHangChiTietId;

import java.math.BigDecimal;
import java.util.List;

public interface GioHangChiTietService {
    List<GioHangChiTiet> getAll();
    List<GioHangChiTiet> getByGioHangId(Integer idGioHang);
    void addOrUpdate(GioHangChiTiet gioHangChiTiet);
    void delete(GioHangChiTietId id);
    void themSanPham(Integer idGioHang, Integer idSach, Integer soLuong);
    void xoaTatCaTrongGio(Integer idGioHang);
    GioHang findOrCreateDefault();
    BigDecimal tinhTongTienTheoGioHang(Integer idGioHang);
    void clearByGioHangId(Integer idGioHang);

}
