package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, Integer> {
    List<PhieuGiamGia> findByTenPhieuContaining(String keyword);
    boolean existsByMaPhieu(String maPhieu);
}
