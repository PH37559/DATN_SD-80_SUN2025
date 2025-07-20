package com.example.datn_sd80_sum2025.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mã code để nhập
    @Column(nullable = false, unique = true)
    private String ma;

    // PERCENT, FIXED, FREE, SPECIAL
    @Column(nullable = false)
    private String loai;

    // Giá trị giảm: 10%, 50000, hoặc 0 nếu FREE
    @Column(name = "gia_tri", nullable = false)
    private Double giaTri;

    // Giá trị đơn hàng tối thiểu để áp dụng
    @Column(name = "dieu_kien")
    private Double dieuKien;

    // Số tiền giảm tối đa (dùng cho loại PERCENT)
    @Column(name = "giam_toi_da")
    private Double giamToiDa;

    // Ngày hết hạn
    @Column(name = "ngay_het_han")
    private LocalDate ngayHetHan;

    // Trạng thái: true = còn hiệu lực, false = vô hiệu
    @Column(name = "trang_thai")
    private Boolean trangThai;

    // =================== Custom Method ====================

    public boolean isConHieuLuc() {
        return (trangThai != null && trangThai)
                && (ngayHetHan == null || !ngayHetHan.isBefore(LocalDate.now()));
    }
}
