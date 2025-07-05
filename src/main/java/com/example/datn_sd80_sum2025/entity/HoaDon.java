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
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_phieu_giam_gia")
    private PhieuGiamGia phieuGiamGia;

    @Column(name = "ngay_lap", nullable = false)
    private LocalDate ngayLap;

    @Column(name = "phuong_thuc_thanh_toan", nullable = false)
    private String phuongThucThanhToan;

    @Column(name = "tong_tien", nullable = false)
    private BigDecimal tongTien;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;


    public void setIdKhachHang(Integer id) {
        this.khachHang = new KhachHang();
        this.khachHang.setId(id);
    }

    public void setIdNhanVien(Integer id) {
        this.nhanVien = new NhanVien();
        this.nhanVien.setId(id);
    }

}

