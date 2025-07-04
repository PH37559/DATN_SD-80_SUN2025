package com.example.datn_sd80_sum2025.repository;

<<<<<<< HEAD
import com.example.datn_sd80_sum2025.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
=======
import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;
import com.example.datn_sd80_sum2025.entity.DoanhThu;
import com.example.datn_sd80_sum2025.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

>>>>>>> ph30389
}
