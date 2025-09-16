package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;
import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query(value = """
            SELECT hd.ngay_lap AS ngayLap, COUNT(*) AS soHoaDon, SUM(hd.tong_tien) AS tongTien
            FROM hoa_don hd
            WHERE YEAR(hd.ngay_lap) = :year
              AND (:month IS NULL OR MONTH(hd.ngay_lap) = :month)
            GROUP BY hd.ngay_lap
            ORDER BY hd.ngay_lap
            """, nativeQuery = true)
    List<DoanhThuDTO> thongKeDoanhThu(@Param("year") int year, @Param("month") Integer month);

    List<HoaDon> findByTrangThai(Integer trangThai);

    @Query(value = """
            SELECT * FROM hoa_don
            WHERE id_khach_hang = :idKH
            """, nativeQuery = true)
    List<HoaDon> getByIdKhachHang(@Param("idKH") Integer idKH);

    @Query(value = """
            SELECT * FROM hoa_don
            WHERE id_khach_hang = :idKH AND trang_thai = :trangThai
            """, nativeQuery = true)
    List<HoaDon> getByIdKhachHangAndTrangThai(@Param("idKH") Integer idKH,
                                              @Param("trangThai") Integer trangThai);

    @Query(value = """
            SELECT hd.id, id_khach_hang, id_nhan_vien, id_phieu_giam_gia, ngay_lap, phuong_thuc_thanh_toan, tong_tien, hd.trang_thai, thanh_tien, phi_ship, id_dia_chi 
            FROM hoa_don hd
            JOIN khach_hang kh ON hd.id_khach_hang = kh.id
            WHERE (:keyword IS NULL OR :keyword = '' OR\s
                kh.ho_ten LIKE CONCAT('%', :keyword, '%') OR\s
                kh.email LIKE CONCAT('%', :keyword, '%') OR\s
                kh.sdt LIKE CONCAT('%', :keyword, '%'))
            AND (:trangThai IS NULL OR hd.trang_thai = :trangThai)
            AND (:phuongThucThanhToan IS NULL OR hd.phuong_thuc_thanh_toan LIKE CONCAT('%', :phuongThucThanhToan, '%'))
            AND (:startDate IS NULL OR hd.ngay_lap >= :startDate)
            AND (:endDate IS NULL OR hd.ngay_lap <= :endDate)
            AND (:priceRange IS NULL
                OR (:priceRange = '1' AND hd.thanh_tien < 100000)
                OR (:priceRange = '2' AND hd.thanh_tien BETWEEN 100000 AND 500000)
                OR (:priceRange = '3' AND hd.thanh_tien BETWEEN 500001 AND 1000000)
                OR (:priceRange = '4' AND hd.thanh_tien > 1000000))
                      """, nativeQuery = true)
    Page<HoaDon> search(
            @Param("keyword") String keyword,
            @Param("trangThai") Integer trangThai,
            @Param("phuongThucThanhToan") String phuongThucThanhToan,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("priceRange") String priceRange,
            Pageable pageable
    );

    @Query(value = """
    SELECT DISTINCT hd.* 
    FROM hoa_don hd
    JOIN hoa_don_chi_tiet ct ON hd.id = ct.id_hoa_don
    JOIN sach s ON ct.id_sach = s.id
    WHERE hd.id_khach_hang = :idKhachHang
    AND (:status IS NULL OR hd.trang_thai = :status)
    AND (
        :keyword IS NULL 
        OR CAST(hd.id AS NVARCHAR) LIKE CONCAT('%', :keyword, '%')
        OR LOWER(s.ten_sach) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    """, nativeQuery = true)
    List<HoaDon> searchOrders(@Param("idKhachHang") Integer idKhachHang,
                              @Param("status") Integer status,
                              @Param("keyword") String keyword);

}
