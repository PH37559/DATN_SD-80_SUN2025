package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.dto.request.KhachHangCreate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangUpdate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface KhachHangService {
    List<KhachHang> getAll();

    KhachHang getById(Integer id);

    Page<KhachHang> getAll(int page, int size);

    Page<KhachHang> search(String keyword,
                           Boolean gioiTinh,
                           Integer trangThai,
                           LocalDate ngaySinhFrom,
                           LocalDate ngaySinhTo,
                           String thanhPho,
                           String quanHuyen,
                           Pageable pageable);

    KhachHang store(KhachHangCreate khachHang);

    void update(Integer id, KhachHangUpdate khachHang);

    void deleteById(Integer id);

    boolean existsBySdt(String sdt, Integer id);

    boolean existsByEmail(String email, Integer id);

    boolean existsByTenTaiKhoan(String tenTaiKhoan, Integer id);

    KhachHang getKhachHangBySdtOrEmailOrTenTaiKhoan(String keyword, String matKhau);

}