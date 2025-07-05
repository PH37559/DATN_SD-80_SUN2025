package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.PhieuGiamGia;
import com.example.datn_sd80_sum2025.repository.PhieuGiamGiaRepository;
import com.example.datn_sd80_sum2025.service.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuGiamGiaServiceImpl implements PhieuGiamGiaService {
    @Autowired
    private PhieuGiamGiaRepository repository;

    @Override
    public List<PhieuGiamGia> getAll() {
        return repository.findAll();
    }
    @Override
    public List<PhieuGiamGia> searchByTenPhieu(String keyword) {
        return repository.findByTenPhieuContaining(keyword);
    }
    @Override
    public PhieuGiamGia getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(PhieuGiamGia pgg) {
        repository.save(pgg);
    }

    @Override
    public void delete(Integer id) {
        PhieuGiamGia pgg = getById(id);
        if (pgg != null) {
            pgg.setTrangThai(0);
            repository.save(pgg);
        }
    }
    @Override
    public boolean existsByMaPhieu(String maPhieu) {
        return repository.existsByMaPhieu(maPhieu);
    }
}
