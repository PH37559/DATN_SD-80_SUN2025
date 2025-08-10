package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.ChucVu;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Integer> {

    @Query(value = """
            SELECT c.id, c.ten_chuc_vu, c.trang_thai
            FROM chuc_vu c
            """, nativeQuery = true)
    List<ChucVu> getAll();

    @Query(value = """
                SELECT * FROM chuc_vu cv
                WHERE (:keyword IS NULL OR :keyword = '' OR cv.ten_chuc_vu LIKE %:keyword%) 
                AND (:trangThai IS NULL OR cv.trang_thai = :trangThai)
            """, nativeQuery = true)
    Page<ChucVu> search(@Param("keyword") String keyword,
                        @Param("trangThai") Integer trangThai,
                        Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO chuc_vu (ten_chuc_vu, trang_thai) 
            VALUES (:tenChucVu, :trangThai)
            """, nativeQuery = true)
    void store(@Param("tenChucVu") String tenChucVu,
               @Param("trangThai") Integer trangThai);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE chuc_vu 
            SET ten_chuc_vu = :tenChucVu, trang_thai =:trangThai
            WHERE id = :id
            """, nativeQuery = true)
    void update(@Param("id") Integer id,
                @Param("tenChucVu") String tenChucVu,
                @Param("trangThai") Integer trangThai);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END
               FROM chuc_vu
               WHERE ten_chuc_vu = :tenChucVu AND (:id IS NULL OR id <> :id)
            """, nativeQuery = true)
    boolean existsByTenChucVu(@Param("tenChucVu") String tenChucVu,
                              @Param("id") Integer id);

}