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
    public void delete(GioHangChiTietId id) {
        repository.deleteById(id);
    }
    @Override
    public void clearTatCa() {
        gioHangChiTietRepository.deleteAll();
    }

    @Override
    public void themSanPham(Integer idGioHang, Integer idSach, Integer soLuong) {
        GioHang gioHang = gioHangRepository.findById(idGioHang).orElseThrow();
        Sach sach = sachRepository.findById(idSach).orElseThrow();

        GioHangChiTietId id = new GioHangChiTietId(gioHang, sach);

        Optional<GioHangChiTiet> existing = repository.findById(id);

        if (existing.isPresent()) {
            GioHangChiTiet chiTiet = existing.get();
            chiTiet.setSoLuong(chiTiet.getSoLuong() + soLuong);
            repository.save(chiTiet);
        } else {
            GioHangChiTiet chiTiet = new GioHangChiTiet();
            chiTiet.setGioHang(gioHang);
            chiTiet.setSach(sach);
            chiTiet.setSoLuong(soLuong);
            chiTiet.setDonGia(sach.getGiaBan());
            chiTiet.setTrangThai(0);
            repository.save(chiTiet);
        }
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
    @Override
    public void capNhatSoLuong(Integer idGioHang, Integer idSach, Integer soLuongMoi) {
        GioHang gioHang = gioHangRepository.findById(idGioHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng."));
        Sach sach = sachRepository.findById(idSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách."));

        GioHangChiTietId id = new GioHangChiTietId(gioHang, sach);
        GioHangChiTiet chiTiet = gioHangChiTietRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng."));

        chiTiet.setSoLuong(soLuongMoi);
        gioHangChiTietRepository.save(chiTiet);
    }
    @Override
    public int getSoLuongByGioHangIdAndSachId(Integer gioHangId, Integer sachId) {
        // Lấy composite‑key
        GioHang gioHang = gioHangRepository.findById(gioHangId).orElse(null);
        Sach     sach    = sachRepository.findById(sachId).orElse(null);

        if (gioHang == null || sach == null) return 0;

        GioHangChiTietId id = new GioHangChiTietId(gioHang, sach);

        return repository.findById(id)
                .map(GioHangChiTiet::getSoLuong)
                .orElse(0);
    }

}
