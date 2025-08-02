package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.ChucVu;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.ChucVuRepository;
import com.example.datn_sd80_sum2025.repository.NhanVienRepository;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    ChucVuRepository chucVuRepository;

    @Override
    public Page<NhanVien> getAll(int page, int size) {
        return nhanVienRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<NhanVien> search(String keyword, Integer chucVu, Integer trangThai, int page, int size) {
        return nhanVienRepository.search(keyword, chucVu, trangThai, PageRequest.of(page, size));
    }

    @Override
    public NhanVien getById(Integer id) {
        return nhanVienRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
    }

    @Override
    public void store(NhanVienCreate nhanVien) {
        nhanVienRepository.store(
                nhanVien.getHoTen(),
                nhanVien.getGioiTinh(),
                nhanVien.getDiaChi(),
                nhanVien.getSdt(),
                nhanVien.getEmail(),
                nhanVien.getNgayLamViec(),
                nhanVien.getTenTaiKhoan(),
                nhanVien.getMatKhau(),
                nhanVien.getIdChucVu(),
                nhanVien.getTrangThai()
        );;
    }

    @Override
    public void update(Integer id, NhanVienUpdate nhanVien) {
        if(id == null){
            throw new IllegalArgumentException("Vui lòng chọn nhân viên để cập nhật");
        }
        nhanVienRepository.update(
                id,
                nhanVien.getHoTen(),
                nhanVien.getGioiTinh(),
                nhanVien.getDiaChi(),
                nhanVien.getSdt(),
                nhanVien.getEmail(),
                nhanVien.getNgayLamViec(),
                nhanVien.getTenTaiKhoan(),
                nhanVien.getMatKhau(),
                nhanVien.getIdChucVu(),
                nhanVien.getTrangThai()
        );;
    }

    @Override
    public void deleteById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException("Vui lòng chọn nhân viên để xóa");
        }
        NhanVien nv = nhanVienRepository.findById(id).get();
        if(nv == null){
            throw new IllegalArgumentException("Không tìm thấy nhân viên");
        }
        nv.setTrangThai(0);
        nhanVienRepository.save(nv);
    }

    @Override
    public List<NhanVien> getAll() {
        return nhanVienRepository.findAll();
    }

    @Override
    public boolean existsBySdt(String sdt, Integer id) {
        return nhanVienRepository.existsBySdt(sdt, id);
    }

    @Override
    public boolean existsByEmail(String email, Integer id) {
        return nhanVienRepository.existsByEmail(email, id);
    }

    @Override
    public boolean existsByTaiKhoan(String tenTaiKhoan, Integer id) {
        return nhanVienRepository.existsByTenTaiKhoan(tenTaiKhoan, id);
    }

    @Override
    public NhanVien findByTenTaiKhoan(String tenTaiKhoan) {
        return  nhanVienRepository.findByTenTaiKhoan(tenTaiKhoan)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: "+ tenTaiKhoan));
    }


}