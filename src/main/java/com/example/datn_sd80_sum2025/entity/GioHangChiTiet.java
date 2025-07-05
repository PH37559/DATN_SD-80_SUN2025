package com.example.datn_sd80_sum2025.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "gio_hang_chi_tiet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GioHangChiTiet {

    @EmbeddedId
    private GioHangChiTietId id;

    @ManyToOne
    @MapsId("idGioHang")
    @JoinColumn(name = "id_gio_hang")
    private GioHang gioHang;

    @ManyToOne
    @MapsId("idSach")
    @JoinColumn(name = "id_sach")
    private Sach sach;

    private Integer soLuong;

    private BigDecimal donGia;

    private String ghiChu;

    private Integer trangThai;

    public BigDecimal getThanhTien() {
        return donGia.multiply(BigDecimal.valueOf(soLuong));
    }
}
