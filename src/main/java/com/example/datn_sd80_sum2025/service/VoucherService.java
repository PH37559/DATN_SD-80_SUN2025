package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher findByMa(String ma);
    Voucher save(Voucher voucher);
    List<Voucher> getAll();

}
