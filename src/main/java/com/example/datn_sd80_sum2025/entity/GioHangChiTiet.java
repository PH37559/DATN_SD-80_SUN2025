package com.example.datn_sd80_sum2025.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

=======
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "gio_hang_chi_tiet")
>>>>>>> ph30389
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
@Entity
@Table(name = "gio_hang_chi_tiet")
@IdClass(GioHangChiTietId.class)
public class GioHangChiTiet {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_gio_hang")
    private GioHang gioHang;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_sach")
    private Sach sach;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "don_gia", nullable = false)
=======
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

>>>>>>> ph30389
    private BigDecimal donGia;

    private String ghiChu;

<<<<<<< HEAD
    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;
}

=======
    private Integer trangThai;

    public BigDecimal getThanhTien() {
        return donGia.multiply(BigDecimal.valueOf(soLuong));
    }
}
>>>>>>> ph30389
