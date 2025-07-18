package com.example.datn_sd80_sum2025.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DiaChiChiTietId implements Serializable {

    @Column(name = "id_khach_hang")
    private Integer idKhachHang;

    @Column(name = "id_dia_chi")
    private Integer idDiaChi;

}
