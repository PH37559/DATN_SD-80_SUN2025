package com.example.datn_sd80_sum2025.service;


import com.example.datn_sd80_sum2025.entity.HoaDon;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface HoaDonService {
    List<HoaDon> getAll();

    HoaDon getById(Integer id);

    List<HoaDon> getDonHangCho();

    HoaDon save(HoaDon hoaDon);

    List<HoaDon> getAllDaThanhToan();

    void add(HoaDon hoaDon);

    List<HoaDon> getByIdKH(Integer idKH);

    List<HoaDon> getByIdKHAndTrangThai(Integer idKH, Integer trangThai);

    int countByIdKHAndTrangThai(Integer idKH, Integer trangThai);

    void updateStatus(Integer idHoaDon, int trangThai);

    void updateOnlineOrder(Integer idHoaDon, int trangThai, Integer idNhanVien);

    Page<HoaDon> search(String keyword,
                        Integer trangThai,
                        String phuongThucThanhToan,
                        LocalDate ngayLapFrom,
                        LocalDate ngayLapTo,
                        String priceRange,
                        Pageable pageable);


}
