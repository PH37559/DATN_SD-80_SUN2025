package com.example.datn_sd80_sum2025.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopKhachHangDTO {
    private String hoTen;
    private String email;
    private int tongSoHoaDon;
    private BigDecimal tongChiTieu;
}
