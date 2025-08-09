package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangCreate;
import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangUpdate;
import com.example.datn_sd80_sum2025.entity.DiaChiChiTiet;
import com.example.datn_sd80_sum2025.entity.DiaChiNhanHang;
import com.example.datn_sd80_sum2025.repository.DiaChiChiTietRepository;
import com.example.datn_sd80_sum2025.repository.DiaChiNhanHangRepository;
import com.example.datn_sd80_sum2025.repository.KhachHangRepository;
import com.example.datn_sd80_sum2025.service.DiaChiChiTietService;
import com.example.datn_sd80_sum2025.service.DiaChiNhanHangService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaChiChiTietServiceImpl implements DiaChiChiTietService {

    @Autowired
    private DiaChiChiTietRepository diaChiChiTietRepository;

    @Override
    public List<DiaChiChiTiet> getDCCTByKHId(Integer idKH) {
        return diaChiChiTietRepository.findByIdKH(idKH);
    }

    @Override
    public DiaChiChiTiet getDCCTByIdKHAndByIdDC(Integer idKH, Integer idDC) {
        return diaChiChiTietRepository.findByIdKHAndIdDCNH(idKH, idDC)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ phù hợp với khách hàng."));
    }

    @Override
    public void delete(Integer idKH, Integer idDC) {
        DiaChiChiTiet dcct = diaChiChiTietRepository.findByIdKHAndIdDCNH(idKH, idDC)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ phù hợp với khách hàng."));
        dcct.setTrangThai(0);
        diaChiChiTietRepository.save(dcct);
    }
}
