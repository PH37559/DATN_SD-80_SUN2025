package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.GioHangChiTiet;
import com.example.datn_sd80_sum2025.entity.GioHangChiTietId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, GioHangChiTietId> {
    List<GioHangChiTiet> findByGioHang_Id(Integer idGioHang);
    void deleteAllByGioHang_Id(Integer idGioHang);
    List<GioHangChiTiet> findByGioHangId(Integer gioHangId);

}
