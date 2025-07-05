package com.example.datn_sd80_sum2025.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
