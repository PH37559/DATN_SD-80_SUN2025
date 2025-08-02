package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SachRepository extends JpaRepository<Sach, Integer> {
    Page<Sach> findByTenSachContainingIgnoreCase(String keyword, Pageable pageable);
    boolean existsByMaSach(String maSach);

}
