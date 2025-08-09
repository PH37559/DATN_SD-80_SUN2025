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
public class ThongKeSanPhamDTO {
    private String maSach;
    private String tenSach;
    private int soLuongBan;
    private BigDecimal doanhThu;
}
