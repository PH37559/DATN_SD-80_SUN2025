package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.repository.HoaDonRepository;
import com.example.datn_sd80_sum2025.service.HoaDonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDon> getAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public HoaDon getById(Integer id) {
        return hoaDonRepository.findById(id).orElse(null);
    }

    @Override
    public List<HoaDon> getAllDaThanhToan() {
        return hoaDonRepository.findByTrangThai(1);
    }

    @Override
    public List<HoaDon> getDonHangCho() {
        return hoaDonRepository.findByTrangThai(0);
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }
    @Override
    public void add(HoaDon hoaDon) {
        hoaDonRepository.save(hoaDon);
    }

}
