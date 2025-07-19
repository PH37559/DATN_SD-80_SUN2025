package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.HoaDonChiTiet;
import com.example.datn_sd80_sum2025.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    List<HoaDonChiTiet> findByHoaDon_Id(Integer hoaDonId);
    Optional<HoaDonChiTiet> findByHoaDonAndSach(HoaDon hoaDon, Sach sach);
    void deleteByHoaDonAndSach(HoaDon hoaDon, Sach sach);
}


