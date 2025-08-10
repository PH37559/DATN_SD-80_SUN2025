package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.datn_sd80_sum2025.entity.GioHangChiTietId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, GioHangChiTietId> {
    List<GioHangChiTiet> findByGioHang_Id(Integer idGioHang);

    void deleteAllByGioHang_Id(Integer idGioHang);

    @Query(value = """
            SELECT * FROM gio_hang_chi_tiet
            WHERE id_gio_hang = :idGioHang
            """, nativeQuery = true)
    List<GioHangChiTiet> findByGioHangId(@Param("idGioHang") Integer idGioHang);

    @Query(value = """
            SELECT * FROM gio_hang_chi_tiet ghct
            WHERE ghct.id_gio_hang = :idGioHang AND ghct.id_sach = :idSach
            """, nativeQuery = true)
    Optional<GioHangChiTiet> findByIdCardAndIdBook(@Param("idGioHang") Integer idGioHang,
                                                   @Param("idSach") Integer idSach);

    @Query(value = """
            SELECT SUM(ghct.so_luong) FROM gio_hang_chi_tiet ghct
            WHERE ghct.id_gio_hang = :idGioHang
            """, nativeQuery = true)
    int countBooksByIdCart(Integer idGioHang);
}