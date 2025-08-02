package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.DiaChiChiTiet;
import com.example.datn_sd80_sum2025.entity.DiaChiChiTietId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DiaChiChiTietRepository extends JpaRepository<DiaChiChiTiet, DiaChiChiTietId> {

    @Query(value = """
        SELECT * FROM dia_chi_chi_tiet
        WHERE id_khach_hang = :idKH
            """, nativeQuery = true)
    List<DiaChiChiTiet> findByIdKH(@Param("idKH") Integer idKhachHang);

    @Query(value = """
        SELECT * FROM dia_chi_chi_tiet
        WHERE id_khach_hang = :idKH AND id_dia_chi = :idDC
            """, nativeQuery = true)
    Optional<DiaChiChiTiet> findByIdKHAndIdDCNH(@Param("idKH") Integer idKhachHang,
                                               @Param("idDC") Integer idDCNH);


}
