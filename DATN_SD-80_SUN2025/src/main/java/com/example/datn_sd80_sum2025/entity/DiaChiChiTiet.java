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
@Table(name = "dia_chi_chi_tiet")
public class DiaChiChiTiet {

    @EmbeddedId
    private DiaChiChiTietId id = new DiaChiChiTietId();

    @ManyToOne
    @MapsId("idKhachHang")
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne
    @MapsId("idDiaChi")
    @JoinColumn(name = "id_dia_chi")
    private DiaChiNhanHang diaChiNhanHang;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;
}

