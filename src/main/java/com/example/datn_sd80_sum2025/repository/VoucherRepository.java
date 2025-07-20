package com.example.datn_sd80_sum2025.repository;

import com.example.datn_sd80_sum2025.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Voucher findByMa(String ma);
}
