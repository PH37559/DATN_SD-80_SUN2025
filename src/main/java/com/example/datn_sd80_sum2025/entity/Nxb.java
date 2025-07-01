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
@Table(name = "nxb")
public class Nxb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nha_xuat_ban", nullable = false)
    private String nhaXuatBan;

    @Column(nullable = false)
    private String diaChi;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;
}
