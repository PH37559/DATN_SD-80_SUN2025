package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    List<HoaDonChiTiet> findByHoaDon_Id(Integer hoaDonId);
}
