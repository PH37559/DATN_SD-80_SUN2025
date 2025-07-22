package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.HoaDonRepository;
import com.example.datn_sd80_sum2025.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<HoaDon> getDonHangCho() {
        return hoaDonRepository.findDonHangChoWithKhachHangAndNhanVien(0);
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public List<HoaDon> getAllDaThanhToan() {
        return hoaDonRepository.findByTrangThai(1);
    }

    @Override
    public void add(HoaDon hoaDon) {
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public List<HoaDon> getTop5DonHangCho() {
        return hoaDonRepository.findTop5ByTrangThaiCho(PageRequest.of(0, 5));
    }

    /**
     * Tạo hóa đơn mới với điều kiện không quá 5 đơn hàng chờ
     */
    @Override
    public HoaDon taoDonHang(KhachHang khachHang, NhanVien nhanVien) {
        List<HoaDon> donHangCho = hoaDonRepository.findByTrangThai(0);
        if (donHangCho.size() >= 5) {
            throw new RuntimeException("Đã có 5 đơn hàng chờ, không thể tạo thêm.");
        }

        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setTrangThai(0);
        hoaDon.setNgayLap(LocalDate.now());
        return hoaDonRepository.save(hoaDon);
    }
}
