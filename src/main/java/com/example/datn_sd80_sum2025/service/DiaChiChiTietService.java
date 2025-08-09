package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangCreate;
import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangUpdate;
import com.example.datn_sd80_sum2025.entity.DiaChiChiTiet;
import com.example.datn_sd80_sum2025.entity.DiaChiNhanHang;

import java.util.List;

public interface DiaChiChiTietService {
    List<DiaChiChiTiet> getDCCTByKHId(Integer idKH);

    DiaChiChiTiet getDCCTByIdKHAndByIdDC(Integer idKH, Integer idDC);

    void delete(Integer idKH, Integer idDC);

}
