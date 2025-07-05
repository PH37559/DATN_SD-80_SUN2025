package com.example.datn_sd80_sum2025.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private BigDecimal donGia;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;

    public BigDecimal getThanhTien() {
        return donGia.multiply(BigDecimal.valueOf(soLuong));
    }
}

