package com.example.datn_sd80_sum2025.dto.request;

import com.example.datn_sd80_sum2025.entity.KhachHang;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GioHangCreate {

    private Integer idKhachHang;

    private LocalDate ngayLap;

    private LocalDate ngayCapNhat;

    private String ghiChu;

    private Integer trangThai;
}
