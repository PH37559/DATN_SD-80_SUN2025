package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.ChucVu;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.ChucVuRepository;
import com.example.datn_sd80_sum2025.repository.NhanVienRepository;
import com.example.datn_sd80_sum2025.service.ChucVuService;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChucVuServiceImpl implements ChucVuService {

    @Autowired
    ChucVuRepository chucVuRepository;

    @Override
    public List<ChucVu> getAll() {
        return chucVuRepository.getAll();
    }
}