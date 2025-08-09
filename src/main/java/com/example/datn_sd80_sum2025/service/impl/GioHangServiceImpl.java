package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.GioHang;
import com.example.datn_sd80_sum2025.repository.GioHangRepository;
import com.example.datn_sd80_sum2025.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public List<GioHang> getAll() {
        return gioHangRepository.findAll();
    }

    @Override
    public GioHang findById(Integer id) {
        return gioHangRepository.findById(id).orElse(null);
    }

}

