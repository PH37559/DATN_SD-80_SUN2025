package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {

}
