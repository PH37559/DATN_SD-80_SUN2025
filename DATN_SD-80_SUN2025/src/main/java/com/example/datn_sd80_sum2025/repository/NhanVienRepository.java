package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    @Query(value = """
                SELECT * FROM nhan_vien nv
                WHERE (:keyword IS NULL OR :keyword = '' OR nv.ho_ten LIKE %:keyword% OR nv.sdt LIKE %:keyword% OR nv.email LIKE %:keyword% OR nv.ten_tai_khoan LIKE %:keyword%) 
                AND (:chucVu IS NULL OR nv.id_chuc_vu = :chucVu) 
                AND (:trangThai IS NULL OR nv.trang_thai = :trangThai)
            """, nativeQuery = true)
    Page<NhanVien> search(@Param("keyword") String keyword,
                          @Param("chucVu") Integer chucVu,
                          @Param("trangThai") Integer trangThai,
                          Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = """
                INSERT INTO nhan_vien (
                    ho_ten, gioi_tinh, dia_chi, sdt, email, 
                    ngay_lam_viec, 
                    ten_tai_khoan, mat_khau, 
                    id_chuc_vu, trang_thai
                ) VALUES (
                    :hoTen, :gioiTinh, :diaChi, :sdt, :email, 
                    :ngayLamViec, 
                    :tenTaiKhoan,:matKhau, 
                    :idChucVu, :trangThai
                )
            """, nativeQuery = true)
    void store(
            @Param("hoTen") String hoTen,
            @Param("gioiTinh") Boolean gioiTinh,
            @Param("diaChi") String diaChi,
            @Param("sdt") String sdt,
            @Param("email") String email,
            @Param("ngayLamViec") LocalDate ngayLamViec,
            @Param("tenTaiKhoan") String tenTaiKhoan,
            @Param("matKhau") String matKhau,
            @Param("idChucVu") Integer idChucVu,
            @Param("trangThai") Integer trangThai
    );

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE nhan_vien SET
                    ho_ten = :hoTen,
                    gioi_tinh = :gioiTinh,
                    dia_chi = :diaChi,
                    sdt = :sdt,
                    email = :email,
                    ngay_lam_viec = :ngayLamViec,
                    ten_tai_khoan = :tenTaiKhoan,
                    mat_khau = :matKhau,
                    id_chuc_vu = :idChucVu,
                    trang_thai = :trangThai
                WHERE id = :id
            """, nativeQuery = true)
    void update(
            @Param("id") Integer id,
            @Param("hoTen") String hoTen,
            @Param("gioiTinh") Boolean gioiTinh,
            @Param("diaChi") String diaChi,
            @Param("sdt") String sdt,
            @Param("email") String email,
            @Param("ngayLamViec") LocalDate ngayLamViec,
            @Param("tenTaiKhoan") String tenTaiKhoan,
            @Param("matKhau") String matKhau,
            @Param("idChucVu") Integer idChucVu,
            @Param("trangThai") Integer trangThai
    );


    @Query(value = """
               SELECT CASE WHEN COUNT(*) > 0 THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END
               FROM nhan_vien
               WHERE sdt = :sdt AND (:id IS NULL OR id <> :id)
            """, nativeQuery = true)
    boolean existsBySdt(@Param("sdt") String sdt, @Param("id") Integer id);

    @Query(value = """
               SELECT CASE WHEN COUNT(*) > 0 THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END
               FROM nhan_vien
               WHERE email = :email AND (:id IS NULL OR id <> :id)
            """, nativeQuery = true)
    boolean existsByEmail(@Param("email") String email, @Param("id") Integer id);

    @Query(value = """
               SELECT CASE WHEN COUNT(*) > 0 THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END
               FROM nhan_vien
               WHERE ten_tai_khoan = :tenTaiKhoan AND (:id IS NULL OR id <> :id)
            """, nativeQuery = true)
    boolean existsByTenTaiKhoan(@Param("tenTaiKhoan") String tenTaiKhoan, @Param("id") Integer id);


    @Query(value = """
            SELECT * FROM nhan_vien n 
            WHERE n.ten_tai_khoan = :tenTaiKhoan AND n.trang_thai = 1
            """, nativeQuery = true)
    Optional<NhanVien> findByTenTaiKhoan(@Param("tenTaiKhoan") String taiKhoan);

}
