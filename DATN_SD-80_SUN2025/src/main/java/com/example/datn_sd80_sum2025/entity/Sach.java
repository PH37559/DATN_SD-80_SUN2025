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
@Table(name = "sach")
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_sach", nullable = false, length = 20)
    private String maSach;

    @Column(name = "ten_sach", nullable = false)
    private String tenSach;

    @Column(name = "nam_xuat_ban", nullable = false)
    private Integer namXuatBan;

    @Column(name = "gia_ban", nullable = false)
    private BigDecimal giaBan;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "tac_gia", nullable = false)
    private String tacGia;

    @ManyToOne
    @JoinColumn(name = "id_ngon_ngu")
    private NgonNgu ngonNgu;

    @ManyToOne
    @JoinColumn(name = "id_the_loai")
    private TheLoai theLoai;

    @ManyToOne
    @JoinColumn(name = "id_nxb")
    private Nxb nxb;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;
}

