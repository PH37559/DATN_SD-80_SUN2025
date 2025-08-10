package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangCreate;
import com.example.datn_sd80_sum2025.dto.request.DiaChiNhanHangUpdate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangCreate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangUpdate;
import com.example.datn_sd80_sum2025.entity.DiaChiChiTiet;
import com.example.datn_sd80_sum2025.entity.DiaChiNhanHang;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.repository.DiaChiChiTietRepository;
import com.example.datn_sd80_sum2025.repository.DiaChiNhanHangRepository;
import com.example.datn_sd80_sum2025.repository.KhachHangRepository;
import com.example.datn_sd80_sum2025.service.DiaChiNhanHangService;
import com.example.datn_sd80_sum2025.service.KhachHangService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiaChiNhanHangServiceImpl implements DiaChiNhanHangService {

    @Autowired
    private DiaChiNhanHangRepository diaChiNhanHangRepository;

    @Autowired
    private DiaChiChiTietRepository diaChiChiTietRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public List<DiaChiNhanHang> getAllByKHId(Integer idKH) {
        return diaChiNhanHangRepository.findByKhachHangId(idKH);
    }

    @Override
    public DiaChiChiTiet store(Integer idKH, DiaChiNhanHangCreate dcnh) {
        DiaChiNhanHang diaChi = diaChiNhanHangRepository.findByFullAddress(
                dcnh.getThanhPho(),
                dcnh.getQuanHuyen(),
                dcnh.getPhuongXa(),
                dcnh.getDiaChiChiTiet()).orElse(null);
        DiaChiChiTiet chiTiet = new DiaChiChiTiet();

        if(diaChi != null) {
            if (diaChiChiTietRepository.findByIdKHAndIdDCNH(idKH, diaChi.getId()) != null) {
                return diaChiChiTietRepository.findByIdKHAndIdDCNH(idKH, diaChi.getId()).get();
            }else{
                chiTiet.setKhachHang(khachHangRepository.findById(idKH).orElseThrow());
                chiTiet.setDiaChiNhanHang(diaChi);
                chiTiet.setTrangThai(dcnh.getTrangThaiDCCT());
                chiTiet.setGhiChu(dcnh.getGhiChu());
            }
        }else {
            DiaChiNhanHang newDC = new DiaChiNhanHang();
            BeanUtils.copyProperties(dcnh, newDC);
            diaChi = diaChiNhanHangRepository.save(newDC);
            chiTiet.setKhachHang(khachHangRepository.findById(idKH).orElseThrow());
            chiTiet.setDiaChiNhanHang(diaChi);
            chiTiet.setTrangThai(dcnh.getTrangThaiDCCT());
            chiTiet.setGhiChu(dcnh.getGhiChu());
        }
        return diaChiChiTietRepository.save(chiTiet);
    }

    @Override
    public void update(Integer idKH, Integer idDCNH, DiaChiNhanHangUpdate dcnh) {
        DiaChiNhanHang diaChi = diaChiNhanHangRepository.findById(idDCNH)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));
        diaChi.setThanhPho(dcnh.getThanhPho());
        diaChi.setQuanHuyen(dcnh.getQuanHuyen());
        diaChi.setPhuongXa(dcnh.getPhuongXa());
        diaChi.setDiaChiChiTiet(dcnh.getDiaChiChiTiet());
        diaChiNhanHangRepository.save(diaChi);

        DiaChiChiTiet chiTiet = diaChiChiTietRepository.findByIdKHAndIdDCNH(idKH, idDCNH).
                orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ phù hợp với khách hàng"));
        chiTiet.setTrangThai(dcnh.getTrangThaiDCCT());
        chiTiet.setGhiChu(dcnh.getGhiChu());
        diaChiChiTietRepository.save(chiTiet);
    }

    @Override
    public List<DiaChiNhanHang> getByIdKhachHang(Integer idKhachHang) {
        return diaChiNhanHangRepository.findByKhachHangId(idKhachHang);
    }


}