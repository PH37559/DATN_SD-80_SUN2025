package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.dto.request.KhachHangCreate;
import com.example.datn_sd80_sum2025.dto.request.KhachHangUpdate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.KhachHang;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.KhachHangRepository;
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
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public List<KhachHang> getAll() {
        return khachHangRepository.findAll();
    }

    @Override
    public KhachHang getById(Integer id) {
        return khachHangRepository.findById(id).orElse(null);
    }

    @Override
    public Page<KhachHang> getAll(int page, int size) {
        return khachHangRepository.findAll(PageRequest.of(page, size));
    }

    private Integer toMMdd(LocalDate date) {
        return (date == null) ? null : date.getMonthValue() * 100 + date.getDayOfMonth();
    }

    @Override
    public Page<KhachHang> search(String keyword,
                                  Boolean gioiTinh,
                                  Integer trangThai,
                                  LocalDate ngaySinhFrom,
                                  LocalDate ngaySinhTo,
                                  String thanhPho,
                                  String quanHuyen, Pageable pageable) {
        return khachHangRepository.search(
                keyword,
                gioiTinh,
                trangThai,
                toMMdd(ngaySinhFrom),
                toMMdd(ngaySinhTo),
                thanhPho,
                quanHuyen,
                pageable
        );
    }

    @Override
    public KhachHang store(KhachHangCreate khachHang) {
        KhachHang kh = new KhachHang();
        BeanUtils.copyProperties(khachHang, kh);
        return khachHangRepository.save(kh);
    }

    @Override
    public void update(Integer id, KhachHangUpdate khachHang) {
        if(id == null){
            throw new IllegalArgumentException("Vui lòng chọn khách hàng để cập nhật");
        }
        khachHangRepository.update(
                id,
                khachHang.getHoTen(),
                khachHang.getGioiTinh(),
                khachHang.getNgaySinh(),
                khachHang.getEmail(),
                khachHang.getSdt(),
                khachHang.getTrangThai()
        );
    }

    @Override
    public void deleteById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException("Vui lòng chọn khách hàng để xóa");
        }
        KhachHang kh = khachHangRepository.findById(id).get();
        if(kh == null){
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }
        kh.setTrangThai(0);
        khachHangRepository.save(kh);
    }

    @Override
    public boolean existsBySdt(String sdt, Integer id) {
        return khachHangRepository.existsBySdt(sdt, id);
    }

    @Override
    public boolean existsByEmail(String email, Integer id) {
        return khachHangRepository.existsByEmail(email, id);
    }

    @Override
    public boolean existsByTenTaiKhoan(String tenTaiKhoan, Integer id) {
        return khachHangRepository.existsByEmail(tenTaiKhoan, id);
    }

    @Override
    public KhachHang getKhachHangBySdtOrEmailOrTenTaiKhoan(String keyword, String matKhau) {
        return khachHangRepository.findByKeywordAndPassword(keyword, matKhau).orElse(null);
    }
}