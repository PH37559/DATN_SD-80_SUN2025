package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.dto.TopSanPhamDTO;
import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.HoaDonChiTiet;
import com.example.datn_sd80_sum2025.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    List<HoaDonChiTiet> findByHoaDon_Id(Integer hoaDonId);
    Optional<HoaDonChiTiet> findByHoaDonAndSach(HoaDon hoaDon, Sach sach);
    void deleteByHoaDonAndSach(HoaDon hoaDon, Sach sach);

//    @Query("SELECT new com.example.datn_sd80_sum2025.dto.TopSanPhamDTO(" +
//            "s.tenSach, SUM(ct.soLuong), SUM(ct.soLuong * ct.donGia)) " +
//            "FROM HoaDonChiTiet ct " +
//            "JOIN ct.hoaDon hd " +
//            "JOIN ct.sach s " +
//            "WHERE FUNCTION('YEAR', hd.ngayLap) = :year " +
//            "AND (:month IS NULL OR FUNCTION('MONTH', hd.ngayLap) = :month) " +
//            "GROUP BY s.tenSach " +
//            "ORDER BY SUM(ct.soLuong) DESC")
//    List<TopSanPhamDTO> thongKeTopSanPham(@Param("year") int year, @Param("month") Integer month);
}


