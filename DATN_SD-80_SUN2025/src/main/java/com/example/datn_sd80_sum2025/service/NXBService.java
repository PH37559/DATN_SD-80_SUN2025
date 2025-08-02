package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.Nxb;

import java.util.List;

public interface NXBService {
    List<Nxb> getAll();

    Nxb getById(Integer id);

    Nxb save(Nxb nxb);

    void deleteById(Integer id);
}
