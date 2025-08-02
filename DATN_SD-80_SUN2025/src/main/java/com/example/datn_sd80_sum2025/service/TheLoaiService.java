package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.TheLoai;

import java.util.List;

public interface TheLoaiService {
    List<TheLoai> getAll();

    TheLoai getById(Integer id);

    TheLoai save(TheLoai theLoai);

    void delete(Integer id);

    List<TheLoai> searchByName(String keyword);
}
