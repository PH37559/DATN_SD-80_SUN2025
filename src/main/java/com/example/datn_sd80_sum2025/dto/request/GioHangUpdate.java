package com.example.datn_sd80_sum2025.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GioHangUpdate {

    private Integer id;

    private Integer idKhachHang;

    private LocalDate ngayLap;

    private LocalDate ngayCapNhat;

    private String ghiChu;

    private Integer trangThai;
}