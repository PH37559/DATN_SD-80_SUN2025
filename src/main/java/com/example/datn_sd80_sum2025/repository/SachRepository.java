package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.dto.TonKhoDTO;
import com.example.datn_sd80_sum2025.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SachRepository extends JpaRepository<Sach, Integer> {
    Page<Sach> findByTenSachContainingIgnoreCase(String keyword, Pageable pageable);
    boolean existsByMaSach(String maSach);


//    @Query("SELECT new com.example.datn_sd80_sum2025.dto.TonKhoDTO(" +
//            " s.maSach, s.tenSach, s.soLuongTon) " +
//            "FROM Sach s WHERE s.soLuongTon > 0")
//    List<TonKhoDTO> thongKeTonKho();



}
