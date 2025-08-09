package com.example.datn_sd80_sum2025.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class DoanhThuDTO {

    private LocalDate ngayLap;
    private Long soHoaDon;
    private BigDecimal tongTien;

    public DoanhThuDTO(LocalDate ngayLap, Long soHoaDon, BigDecimal tongTien) {
        this.ngayLap = ngayLap;
        this.soHoaDon = soHoaDon;
        this.tongTien = tongTien;
    }

    // Getter Setter đầy đủ
}
