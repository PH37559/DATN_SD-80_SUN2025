package com.example.datn_sd80_sum2025.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "phieu_giam_gia")
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_phieu", nullable = false, length = 30)
    private String maPhieu;

    @Column(name = "ten_phieu", nullable = false)
    private String tenPhieu;

    @Column(name = "dieu_kien_giam", nullable = false)
    private BigDecimal dieuKienGiam;

    @Column(name = "phan_tram_gia", nullable = false)
    private Float phanTramGia;

    @Column(name = "giam_toi_da", nullable = false)
    private BigDecimal giamToiDa;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc", nullable = false)
    private LocalDate ngayKetThuc;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;
}

