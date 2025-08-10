package com.example.datn_sd80_sum2025.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor

public class TopSanPhamDTO {
    private String tenSach;
    private Long tongSoLuong;
    private BigDecimal tongDoanhThu;

    public TopSanPhamDTO(String tenSach, Long tongSoLuong, BigDecimal tongDoanhThu) {
        this.tenSach = tenSach;
        this.tongSoLuong = tongSoLuong;
        this.tongDoanhThu = tongDoanhThu;
    }
}
