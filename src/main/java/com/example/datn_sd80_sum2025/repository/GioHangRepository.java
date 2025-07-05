package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    List<GioHang> findByKhachHang_Id(Integer idKhachHang);
    Optional<GioHang> findFirstByTrangThai(int trangThai);

}
