package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;
import com.example.datn_sd80_sum2025.entity.HoaDon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query("SELECT new com.example.datn_sd80_sum2025.dto.DoanhThuDTO(" +
            " hd.ngayLap, COUNT(hd), SUM(hd.tongTien)) " +
            " FROM HoaDon hd " +
            " WHERE YEAR(hd.ngayLap) = :year " +
            " AND (:month IS NULL OR MONTH(hd.ngayLap) = :month) " +
            " GROUP BY hd.ngayLap " +
            " ORDER BY hd.ngayLap")
    List<DoanhThuDTO> thongKeDoanhThu(@Param("year") int year, @Param("month") Integer month);
    List<HoaDon> findByTrangThai(Integer trangThai);
    @Query("SELECT hd FROM HoaDon hd " +
            "JOIN FETCH hd.khachHang " +
            "JOIN FETCH hd.nhanVien " +
            "WHERE hd.trangThai = :trangThai")
    List<HoaDon> findDonHangChoWithKhachHangAndNhanVien(@Param("trangThai") Integer trangThai);
    @Query("SELECT h FROM HoaDon h WHERE h.trangThai = 0 ORDER BY h.ngayLap DESC")
    List<HoaDon> findTop5ByTrangThaiCho(Pageable pageable);
}
