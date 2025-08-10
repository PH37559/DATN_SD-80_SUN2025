package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;
import com.example.datn_sd80_sum2025.dto.ThongKeSanPhamDTO;
import com.example.datn_sd80_sum2025.dto.TopKhachHangDTO;
import com.example.datn_sd80_sum2025.entity.HoaDon;
import org.springframework.data.domain.Page;
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

//    @Query("SELECT new com.example.datn_sd80_sum2025.dto.ThongKeSanPhamDTO(" +
//            " s.maSach, s.tenSach, SUM(ct.soLuong), SUM(ct.soLuong * ct.donGia)) " +
//            " FROM HoaDon hd " +
//            " JOIN hd.chiTietHoaDons ct " +
//            " JOIN ct.sach s " +
//            " WHERE hd.trangThai = 1 " +
//            " GROUP BY s.maSach, s.tenSach " +
//            " ORDER BY SUM(ct.soLuong * ct.donGia) DESC")
//    List<ThongKeSanPhamDTO> thongKeSanPham();
    //top khach hang
@Query("""
    SELECT new com.example.datn_sd80_sum2025.dto.TopKhachHangDTO(
        kh.hoTen, kh.email, COUNT(hd.id), SUM(hd.tongTien)
    )
    FROM HoaDon hd
    JOIN hd.khachHang kh
    WHERE (:year IS NULL OR YEAR(hd.ngayLap) = :year)
      AND (:month IS NULL OR MONTH(hd.ngayLap) = :month)
    GROUP BY kh.hoTen, kh.email
    ORDER BY SUM(hd.tongTien) DESC
""")
Page<TopKhachHangDTO> thongKeTopKhachHangTheoThoiGian(
        @Param("year") Integer year,
        @Param("month") Integer month,
        Pageable pageable
);

    @Query(value = """
            SELECT * FROM hoa_don
            WHERE id_khach_hang = :idKH AND trang_thai = :trangThai
            """, nativeQuery = true)
    List<HoaDon> getByIdKhachHangAndTrangThai(@Param("idKH") Integer idKH,
                                              @Param("trangThai") Integer trangThai);
    @Query(value = """
            SELECT * FROM hoa_don
            WHERE id_khach_hang = :idKH
            """, nativeQuery = true)
    List<HoaDon> getByIdKhachHang (@Param("idKH") Integer idKH);

}
