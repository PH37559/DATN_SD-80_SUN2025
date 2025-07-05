package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.NgonNgu;
import com.example.datn_sd80_sum2025.repository.NgonNguRepository;

import java.util.List;

public interface NgonNguService {

    List<NgonNgu> getAll();

    NgonNgu getById(Integer id);

    void save(NgonNgu ngonNgu);

    void delete(Integer id);

    List<NgonNgu> getAllActive();

}
