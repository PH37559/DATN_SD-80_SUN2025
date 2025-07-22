package com.example.datn_sd80_sum2025.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "voucher")
@Getter
@Setter
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_voucher", unique = true, nullable = false)
    private String ma;

    @Column(name = "ten_voucher")
    private String ten;

    @Column(name = "loai")
    private Integer loai; // 1: %, 2: cố định, 3: không điều kiện, 4: đối tượng đặc biệt

    @Column(name = "phan_tram_giam")
    private Integer phanTramGiam;

    @Column(name = "so_tien_giam")
    private BigDecimal soTienGiam;

    @Column(name = "giam_toi_da")
    private BigDecimal giamToiDa;

    @Column(name = "don_toi_thieu")
    private BigDecimal dieuKien; // điều kiện áp dụng (đơn tối thiểu)

    @Column(name = "doi_tuong_ap_dung")
    private String doiTuongApDung;

    @Column(name = "ngay_bat_dau")
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDate ngayKetThuc;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Transient
    public boolean isConHieuLuc() {
        LocalDate now = LocalDate.now();
        return (trangThai != null && trangThai == 1)
                && (soLuong == null || soLuong > 0)
                && (ngayBatDau == null || !now.isBefore(ngayBatDau))
                && (ngayKetThuc == null || !now.isAfter(ngayKetThuc));
    }

    @Transient
    public String getLoaiAsString() {
        return switch (this.loai) {
            case 1 -> "PERCENT";
            case 2 -> "FIXED";
            case 3 -> "FREE";
            case 4 -> "SPECIAL";
            default -> "UNKNOWN";
        };
    }

    @Transient
    public BigDecimal getGiaTri() {
        return this.loai == 1 || this.loai == 3 || this.loai == 4
                ? BigDecimal.valueOf(phanTramGiam != null ? phanTramGiam : 0)
                : soTienGiam;
    }
}
