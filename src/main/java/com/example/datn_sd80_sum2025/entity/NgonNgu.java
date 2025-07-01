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
@Table(name = "ngon_ngu")
public class NgonNgu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ngon_ngu", nullable = false)
    private String ngonNgu;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;
}

