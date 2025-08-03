package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.repository.HoaDonRepository;
import com.example.datn_sd80_sum2025.service.HoaDonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDon> getAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public HoaDon getById(Integer id) {
        return hoaDonRepository.findById(id).orElse(null);
    }

    @Override
    public List<HoaDon> getAllDaThanhToan() {
        return hoaDonRepository.findByTrangThai(1);
    }

    @Override
    public List<HoaDon> getDonHangCho() {
        return hoaDonRepository.findByTrangThai(0);
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }
    @Override
    public void add(HoaDon hoaDon) {
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public List<HoaDon> getByIdKH(Integer idKH) {
        return hoaDonRepository.getByIdKhachHang(idKH);
    }

    @Override
    public List<HoaDon> getByIdKHAndTrangThai(Integer idKH, Integer trangThai){
        return hoaDonRepository.getByIdKhachHangAndTrangThai(idKH, trangThai);
    }

    @Override
    public int countByIdKHAndTrangThai(Integer idKH, Integer trangThai){
        int count = 0;
        List<HoaDon> list = getByIdKHAndTrangThai(idKH, trangThai);
        if(list != null || !list.isEmpty()){
            count = list.size();
        }
        return count;
    }

}
