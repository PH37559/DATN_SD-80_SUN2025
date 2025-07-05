package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.GioHangChiTiet;
import com.example.datn_sd80_sum2025.entity.GioHangChiTietId;
import com.example.datn_sd80_sum2025.entity.GioHang;
import com.example.datn_sd80_sum2025.entity.Sach;
import com.example.datn_sd80_sum2025.repository.GioHangChiTietRepository;
import com.example.datn_sd80_sum2025.repository.GioHangRepository;
import com.example.datn_sd80_sum2025.repository.SachRepository;
import com.example.datn_sd80_sum2025.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository repository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private SachRepository sachRepository;
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Override
    public List<GioHangChiTiet> getAll() {
        return repository.findAll();
    }

    @Override
    public List<GioHangChiTiet> getByGioHangId(Integer idGioHang) {
        return repository.findByGioHang_Id(idGioHang);
    }

    @Override
    public void addOrUpdate(GioHangChiTiet gioHangChiTiet) {
        repository.save(gioHangChiTiet);
    }

    @Override
    public void delete(GioHangChiTietId id) {
        repository.deleteById(id);
    }

    @Override
    public void themSanPham(Integer idGioHang, Integer idSach, Integer soLuong) {
        GioHangChiTietId id = new GioHangChiTietId(idGioHang, idSach);
        Optional<GioHangChiTiet> existing = repository.findById(id);

        GioHang gioHang = gioHangRepository.findById(idGioHang).orElseThrow();
        Sach sach = sachRepository.findById(idSach).orElseThrow();

        if (existing.isPresent()) {
            GioHangChiTiet chiTiet = existing.get();
            chiTiet.setSoLuong(chiTiet.getSoLuong() + soLuong);
            repository.save(chiTiet);
        } else {
            GioHangChiTiet chiTiet = new GioHangChiTiet();
            chiTiet.setId(id);
            chiTiet.setGioHang(gioHang);
            chiTiet.setSach(sach);
            chiTiet.setSoLuong(soLuong);
            chiTiet.setDonGia(sach.getGiaBan());
            repository.save(chiTiet);
        }
    }

    @Override
    public void xoaTatCaTrongGio(Integer idGioHang) {
        repository.deleteAllByGioHang_Id(idGioHang);
    }
    @Override
    public GioHang findOrCreateDefault() {
        return gioHangRepository.findFirstByTrangThai(0)
                .orElseGet(() -> {
                    GioHang newGioHang = new GioHang();
                    newGioHang.setTrangThai(0);
                    return gioHangRepository.save(newGioHang);
                });
    }
    @Override
    public BigDecimal tinhTongTienTheoGioHang(Integer idGioHang) {
        List<GioHangChiTiet> list = getByGioHangId(idGioHang);
        return list.stream()
                .map(item -> item.getDonGia().multiply(BigDecimal.valueOf(item.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Override
    public void clearByGioHangId(Integer idGioHang) {
        List<GioHangChiTiet> list = gioHangChiTietRepository.findByGioHangId(idGioHang);
        gioHangChiTietRepository.deleteAll(list);
    }


}
