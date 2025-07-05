package com.example.datn_sd80_sum2025.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoanhThu {
    private LocalDate ngay;        // hoặc kiểu khác tuỳ: "2025-07-01"
    private Long soDonHang;
    private BigDecimal tongTien;


}
