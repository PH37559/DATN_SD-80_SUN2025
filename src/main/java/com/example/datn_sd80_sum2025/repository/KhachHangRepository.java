package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {

    @Query(value = """
            SELECT * FROM khach_hang kh
            WHERE (:keyword IS NULL OR :keyword = '' OR\s
                kh.ho_ten LIKE CONCAT('%', :keyword, '%') OR\s
                kh.email LIKE CONCAT('%', :keyword, '%') OR\s
                kh.sdt LIKE CONCAT('%', :keyword, '%'))
            AND (:gioiTinh IS NULL OR kh.gioi_tinh = :gioiTinh)
            AND (:trangThai IS NULL OR kh.trang_thai = :trangThai)
            AND (:fromMMdd IS NULL OR (MONTH(kh.ngay_sinh) * 100 + DAY(kh.ngay_sinh)) >= :fromMMdd)
            AND (:toMMdd IS NULL OR (MONTH(kh.ngay_sinh) * 100 + DAY(kh.ngay_sinh)) <= :toMMdd)
            AND (:thanhPho IS NULL OR :thanhPho = '' OR EXISTS (
                    SELECT 1 FROM dia_chi_chi_tiet dcct
                    JOIN dia_chi_nhan_hang dcn ON dcct.id_dia_chi = dcn.id
                    WHERE dcct.id_khach_hang = kh.id
                    AND dcn.thanh_pho = :thanhPho
                    AND (:quanHuyen IS NULL OR :quanHuyen = '' OR dcn.quan_huyen = :quanHuyen)
            ))
            """, nativeQuery = true)
    Page<KhachHang> search(
            @Param("keyword") String keyword,
            @Param("gioiTinh") Boolean gioiTinh,
            @Param("trangThai") Integer trangThai,
            @Param("fromMMdd") Integer fromMMdd,
            @Param("toMMdd") Integer toMMdd,
            @Param("thanhPho") String thanhPho,
            @Param("quanHuyen") String quanHuyen,
            Pageable pageable
    );

    @Modifying
    @Transactional
    @Query(value = """
                INSERT INTO khach_hang (
                    ho_ten, gioi_tinh, ngay_sinh, email, sdt, trang_thai
                ) VALUES (
                    :hoTen, :gioiTinh, :ngaySinh, :email, :sdt, :trangThai
                )
            """, nativeQuery = true)
    void store(
            @Param("hoTen") String hoTen,
            @Param("gioiTinh") Boolean gioiTinh,
            @Param("ngaySinh") LocalDate ngaySinh,
            @Param("email") String email,
            @Param("sdt") String sdt,
            @Param("trangThai") Integer trangThai
    );

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE khach_hang SET
                    ho_ten = :hoTen,
                    gioi_tinh = :gioiTinh,
                    ngay_sinh = :ngaySinh,
                    email = :email,
                    sdt = :sdt,
                    trang_thai = :trangThai
                WHERE id = :id
            """, nativeQuery = true)
    void update(
            @Param("id") Integer id,
            @Param("hoTen") String hoTen,
            @Param("gioiTinh") Boolean gioiTinh,
            @Param("ngaySinh") LocalDate ngaySinh,
            @Param("email") String email,
            @Param("sdt") String sdt,
            @Param("trangThai") Integer trangThai
    );


    @Query(value = """
               SELECT CASE WHEN COUNT(*) > 0 THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END
               FROM khach_hang
               WHERE sdt = :sdt AND (:id IS NULL OR id <> :id)
            """, nativeQuery = true)
    boolean existsBySdt(@Param("sdt") String sdt, @Param("id") Integer id);

    @Query(value = """
               SELECT CASE WHEN COUNT(*) > 0 THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END
               FROM khach_hang
               WHERE email = :email AND (:id IS NULL OR id <> :id)
            """, nativeQuery = true)
    boolean existsByEmail(@Param("email") String email, @Param("id") Integer id);

    @Query(value = """
               SELECT CASE WHEN COUNT(*) > 0 THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END
               FROM khach_hang
               WHERE ten_tai_khoan = :tenTaiKhoan AND (:id IS NULL OR id <> :id)
            """, nativeQuery = true)
    boolean existsByTenTaiKhoan(@Param("tenTaiKhoan") String email, @Param("id") Integer id);

    Optional<KhachHang> findBySdt(String sdt);

    @Query(value = """
                SELECT * FROM khach_hang kh
                WHERE (kh.sdt = :keyword OR kh.email = :keyword OR kh.ten_tai_khoan = :keyword)
                AND kh.mat_khau = :matKhau
            """, nativeQuery = true)
    Optional<KhachHang> findByKeywordAndPassword(@Param("keyword") String keyword,
                                                 @Param("matKhau") String matKhau);

}