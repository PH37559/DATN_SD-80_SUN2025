package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.repository.KhachHangRepository;
import com.example.datn_sd80_sum2025.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangServiceImpl implements KhachHangService {

        @Autowired
        private KhachHangRepository khachHangRepository;

        @Override
        public List<KhachHang> getAll() {
            return khachHangRepository.findAll();
        }

        @Override
        public KhachHang getById(Integer id) {
            return khachHangRepository.findById(id).orElse(null);
        }



}
