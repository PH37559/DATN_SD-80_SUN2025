package com.example.datn_sd80_sum2025.service.impl;


import com.example.datn_sd80_sum2025.entity.HoaDonChiTiet;
import com.example.datn_sd80_sum2025.repository.HoaDonChiTietRepository;
import com.example.datn_sd80_sum2025.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    @Autowired
    private HoaDonChiTietRepository repository;

    @Override
    public List<HoaDonChiTiet> getByHoaDonId(Integer hoaDonId) {
        return repository.findByHoaDon_Id(hoaDonId);
    }
}
