package com.example.datn_sd80_sum2025.service.impl;


import com.example.datn_sd80_sum2025.entity.Voucher;
import com.example.datn_sd80_sum2025.repository.VoucherRepository;
import com.example.datn_sd80_sum2025.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
    public class VoucherServiceImpl implements VoucherService {

        @Autowired
        private VoucherRepository voucherRepository;

        @Override
        public Voucher findByMa(String ma) {
            return voucherRepository.findByMa(ma);
        }
    }

