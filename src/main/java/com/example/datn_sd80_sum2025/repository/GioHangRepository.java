package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {

    @Query(value = """
            SELECT * FROM gio_hang gh 
            WHERE gh.id_khach_hang = :idKhachHang
            """, nativeQuery = true)
    GioHang findByIdKhachHang(@Param("idKhachHang") Integer idKhachHang);

    Optional<GioHang> findFirstByTrangThai(int trangThai);

}
