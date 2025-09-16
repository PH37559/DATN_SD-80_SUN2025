package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.HoaDonRepository;
import com.example.datn_sd80_sum2025.repository.NhanVienRepository;
import com.example.datn_sd80_sum2025.service.HoaDonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service

public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

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
    public List<HoaDon> getByIdKHAndTrangThai(Integer idKH, Integer trangThai) {
        return hoaDonRepository.getByIdKhachHangAndTrangThai(idKH, trangThai);
    }

    @Override
    public int countByIdKHAndTrangThai(Integer idKH, Integer trangThai) {
        int count = 0;
        List<HoaDon> list = getByIdKHAndTrangThai(idKH, trangThai);
        if (list != null || !list.isEmpty()) {
            count = list.size();
        }
        return count;
    }

    @Override
    public void updateStatus(Integer idHoaDon, int trangThai) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với id: " + idHoaDon));
        hoaDon.setTrangThai(trangThai);
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public void updateOnlineOrder(Integer idHoaDon, int trangThai, Integer idNhanVien) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với id: " + idHoaDon));

        NhanVien nv = nhanVienRepository.findById(idNhanVien)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        hoaDon.setTrangThai(trangThai);
        hoaDon.setNhanVien(nv);
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public Page<HoaDon> search(String keyword,
                               Integer trangThai,
                               String phuongThucThanhToan,
                               LocalDate ngayLapFrom,
                               LocalDate ngayLapTo,
                               String priceRange,
                               Pageable pageable) {
        LocalDateTime startDateTime = (ngayLapFrom != null) ? ngayLapFrom.atStartOfDay() : null;
        LocalDateTime endDateTime = (ngayLapTo != null) ? ngayLapTo.atTime(LocalTime.MAX) : null;

        return hoaDonRepository.search(
                keyword,
                trangThai,
                phuongThucThanhToan,
                startDateTime,
                endDateTime,
                priceRange,
                pageable
        );
    }

    @Override
    public List<HoaDon> searchOrders(Integer idKhachHang, Integer status, String keyword) {
        return hoaDonRepository.searchOrders(idKhachHang, status, keyword);
    }

    private Integer toMMdd(LocalDate date) {
        return (date == null) ? null : date.getMonthValue() * 100 + date.getDayOfMonth();
    }

}
