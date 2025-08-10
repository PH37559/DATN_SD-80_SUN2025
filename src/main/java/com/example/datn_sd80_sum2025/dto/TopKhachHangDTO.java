package com.example.datn_sd80_sum2025.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TopKhachHangDTO {
    private String hoTen;
    private String email;
    private Long soLuongHoaDon;
    private BigDecimal tongTien;

    public TopKhachHangDTO(String hoTen, String email, Long soLuongHoaDon, BigDecimal tongTien) {
        this.hoTen = hoTen;
        this.email = email;
        this.soLuongHoaDon = soLuongHoaDon;
        this.tongTien = tongTien;
    }
}
