package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.NgonNgu;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface NgonNguRepository extends JpaRepository<NgonNgu, Integer> {
=======
import java.util.List;

public interface NgonNguRepository extends JpaRepository<NgonNgu, Integer> {
    List<NgonNgu> findByTrangThai(Integer trangThai);
>>>>>>> ph30389
}
