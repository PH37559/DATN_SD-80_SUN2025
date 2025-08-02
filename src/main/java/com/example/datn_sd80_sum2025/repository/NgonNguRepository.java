package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.NgonNgu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NgonNguRepository extends JpaRepository<NgonNgu, Integer> {
    List<NgonNgu> findByTrangThai(Integer trangThai);

}
