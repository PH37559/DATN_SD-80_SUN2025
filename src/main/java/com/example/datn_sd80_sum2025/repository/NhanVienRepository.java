package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    @Query(value = "SELECT * FROM NhanVien WHERE idChucVu = ?1", nativeQuery = true)
    List<NhanVien> findByChucVuId(@RequestParam("idChucVu") Integer id);


}