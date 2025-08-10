package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.HoaDonChiTiet;
import com.example.datn_sd80_sum2025.entity.Sach;
import com.example.datn_sd80_sum2025.repository.HoaDonChiTietRepository;
import com.example.datn_sd80_sum2025.repository.HoaDonRepository;
import com.example.datn_sd80_sum2025.repository.SachRepository;
import com.example.datn_sd80_sum2025.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepo;

    @Autowired
    private HoaDonRepository hoaDonRepo;

    @Autowired
    private SachRepository sachRepo;

    @Override
    public List<HoaDonChiTiet> getByHoaDonId(Integer hoaDonId) {
        return hoaDonChiTietRepo.findByHoaDon_Id(hoaDonId);
    }

    @Override
    public void save(HoaDonChiTiet hoaDonChiTiet) {
        hoaDonChiTietRepo.save(hoaDonChiTiet);
    }

    @Override
    public void themSanPham(Integer hoaDonId, Integer idSach, Integer soLuong) {
        HoaDon hoaDon = hoaDonRepo.findById(hoaDonId).orElseThrow();
        Sach sach = sachRepo.findById(idSach).orElseThrow();

        HoaDonChiTiet chiTiet = hoaDonChiTietRepo
                .findByHoaDonAndSach(hoaDon, sach)
                .orElseGet(() -> {
                    HoaDonChiTiet c = new HoaDonChiTiet();
                    c.setHoaDon(hoaDon);
                    c.setSach(sach);
                    c.setDonGia(sach.getGiaBan());
                    c.setSoLuong(0);
                    c.setTrangThai(1);
                    return c;
                });

        int slHienTai = chiTiet.getSoLuong() == null ? 0 : chiTiet.getSoLuong();
        chiTiet.setSoLuong(slHienTai + soLuong);
        BigDecimal donGia = chiTiet.getDonGia() == null ? BigDecimal.ZERO : chiTiet.getDonGia();
        chiTiet.setThanhTien(donGia.multiply(BigDecimal.valueOf(chiTiet.getSoLuong())));

        hoaDonChiTietRepo.save(chiTiet);

        List<HoaDonChiTiet> danhSachChiTiet = hoaDonChiTietRepo.findByHoaDon_Id(hoaDonId);
        BigDecimal tongTien = danhSachChiTiet.stream()
                .map(HoaDonChiTiet::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        hoaDon.setTongTien(tongTien);
        hoaDonRepo.save(hoaDon);
    }
    @Override
    @Transactional
    public void xoaSanPham(Integer hoaDonId, Integer idSach) {
        HoaDon hoaDon = hoaDonRepo.findById(hoaDonId).orElseThrow();
        Sach sach = sachRepo.findById(idSach).orElseThrow();
        hoaDonChiTietRepo.deleteByHoaDonAndSach(hoaDon, sach);
        List<HoaDonChiTiet> danhSachChiTiet = hoaDonChiTietRepo.findByHoaDon_Id(hoaDonId);
        BigDecimal tongTien = danhSachChiTiet.stream()
                .map(HoaDonChiTiet::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        hoaDon.setTongTien(tongTien);
        hoaDonRepo.save(hoaDon);
    }
    @Override
    public void capNhatSoLuong(Integer hoaDonId, Integer sachId, Integer soLuongMoi) {
        HoaDon hoaDon = hoaDonRepo.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn."));
        Sach sach = sachRepo.findById(sachId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách."));

        HoaDonChiTiet chiTiet = hoaDonChiTietRepo.findByHoaDonAndSach(hoaDon, sach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết hóa đơn."));

        chiTiet.setSoLuong(soLuongMoi);
        BigDecimal donGia = chiTiet.getDonGia() != null ? chiTiet.getDonGia() : BigDecimal.ZERO;
        chiTiet.setThanhTien(donGia.multiply(BigDecimal.valueOf(soLuongMoi)));

        hoaDonChiTietRepo.save(chiTiet);

        List<HoaDonChiTiet> danhSachChiTiet = hoaDonChiTietRepo.findByHoaDon_Id(hoaDonId);
        BigDecimal tongTien = danhSachChiTiet.stream()
                .map(HoaDonChiTiet::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        hoaDon.setTongTien(tongTien);
        hoaDonRepo.save(hoaDon);
    }
    @Override
    public int getSoLuongByHoaDonIdAndSachId(Integer hoaDonId, Integer sachId) {
        HoaDon hoaDon = hoaDonRepo.findById(hoaDonId).orElse(null);
        Sach sach = sachRepo.findById(sachId).orElse(null);

        if (hoaDon == null || sach == null) return 0;

        return hoaDonChiTietRepo.findByHoaDonAndSach(hoaDon, sach)
                .map(HoaDonChiTiet::getSoLuong)
                .orElse(0);
    }

}