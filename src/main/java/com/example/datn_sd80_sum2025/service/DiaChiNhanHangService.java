package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangCreate;
import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangUpdate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangCreate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangUpdate;
import com.example.datn_sd80_sum2025.entity.DiaChiChiTiet;
import com.example.datn_sd80_sum2025.entity.DiaChiNhanHang;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface DiaChiNhanHangService {
    List<DiaChiNhanHang> getAllByKHId(Integer idKH);

    DiaChiChiTiet store(Integer idKH, DiaChiNhanHangCreate dcnh);

    void update(Integer idKH, Integer idDC, DiaChiNhanHangUpdate dcnh);

}
