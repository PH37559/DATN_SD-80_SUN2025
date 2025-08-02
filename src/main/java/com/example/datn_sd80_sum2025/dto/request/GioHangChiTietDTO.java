package com.example.datn_sd80_sum2025.dto.request;

import com.example.datn_sd80_sum2025.entity.GioHang;
import com.example.datn_sd80_sum2025.entity.Sach;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GioHangChiTietDTO {

    private Integer idGioHang;

    private Integer idSach;

    private BigDecimal donGia;

    private Integer soLuong;

    private Integer ghiChu;

    private Integer trangThai;
}
