package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer> {
    List<TheLoai> findByTenTheLoaiContainingIgnoreCase(String keyword);

}
