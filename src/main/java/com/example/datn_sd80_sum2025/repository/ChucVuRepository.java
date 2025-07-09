package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Integer> {

    @Query(value = """
            SELECT c.id, c.ten_chuc_vu, c.trang_thai
            FROM chuc_vu c
            """, nativeQuery = true)
    List<ChucVu> getAll();

}

