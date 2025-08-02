package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.dto.request.GioHangCreate;
import com.example.datn_sd80_sum2025.entity.GioHang;
import com.example.datn_sd80_sum2025.repository.GioHangRepository;
import com.example.datn_sd80_sum2025.service.GioHangService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public List<GioHang> getAll() {
        return gioHangRepository.findAll();
    }

    @Override
    public GioHang findById(Integer id) {
        return gioHangRepository.findById(id).orElse(null);
    }

    @Override
    public GioHang getByIdKhachHang(Integer idKhachHang) {
        return gioHangRepository.findByIdKhachHang(idKhachHang);
    }

    @Override
    public void store(GioHangCreate gioHang) {
        GioHang gh = new GioHang();
        BeanUtils.copyProperties(gioHang, gh);
        gioHangRepository.save(gh);
    }

    @Override
    public GioHang createAnonymousCart() {
        GioHang gioHang = new GioHang();
        gioHang.setNgayLap(LocalDate.now());
        gioHang.setNgayCapNhat(LocalDate.now());
        gioHang.setTrangThai(1);
        gioHang.setKhachHang(null);
        return gioHangRepository.save(gioHang);
    }

}

