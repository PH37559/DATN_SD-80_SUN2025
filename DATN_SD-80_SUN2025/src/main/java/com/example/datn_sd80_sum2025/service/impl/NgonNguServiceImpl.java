package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.NgonNgu;
import com.example.datn_sd80_sum2025.repository.NgonNguRepository;
import com.example.datn_sd80_sum2025.service.NgonNguService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NgonNguServiceImpl implements NgonNguService {
    @Autowired
    private NgonNguRepository ngonNguRepository;

    @Override
    public List<NgonNgu> getAll() {
        return ngonNguRepository.findAll();
    }

    @Override
    public NgonNgu getById(Integer id) {
        return ngonNguRepository.findById(id).orElse(null);
    }

    @Override
    public void save(NgonNgu ngonNgu) {
        ngonNguRepository.save(ngonNgu);
    }

    @Override
    public void delete(Integer id) {
        NgonNgu ngonNgu = getById(id);
        if (ngonNgu != null) {
            ngonNgu.setTrangThai(0);
            ngonNguRepository.save(ngonNgu);
        }
    }
    @Override
    public List<NgonNgu> getAllActive() {
        return ngonNguRepository.findByTrangThai(1);
    }
}
