package com.example.datn_sd80_sum2025.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GioHangChiTietId implements Serializable {

    @Column(name = "id_gio_hang")
    private Integer idGioHang;

    @Column(name = "id_sach")
    private Integer idSach;
}
