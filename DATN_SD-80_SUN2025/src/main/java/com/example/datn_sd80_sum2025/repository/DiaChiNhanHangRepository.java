package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.DiaChiNhanHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiaChiNhanHangRepository extends JpaRepository<DiaChiNhanHang, Integer> {

    @Query(value = """
            SELECT dcnh.* 
            FROM dia_chi_nhan_hang dcnh
            JOIN dia_chi_chi_tiet dcct ON dcct.id_dia_chi = dcnh.id
            WHERE dcct.id_khach_hang = :idKhachHang
            """, nativeQuery = true)
    List<DiaChiNhanHang> findByKhachHangId(@Param("idKhachHang") Integer idKhachHang);



    @Query(value = """
                SELECT * FROM dia_chi_nhan_hang
                WHERE thanh_pho = :thanhPho 
                AND quan_huyen = :quanHuyen 
                AND phuong_xa = :phuongXa 
                AND dia_chi_chi_tiet = :diaChiChiTiet
            """, nativeQuery = true)
    Optional<DiaChiNhanHang> findByFullAddress(
            @Param("thanhPho") String thanhPho,
            @Param("quanHuyen") String quanHuyen,
            @Param("phuongXa") String phuongXa,
            @Param("diaChiChiTiet") String diaChiChiTiet
    );



}
