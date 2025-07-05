package com.example.datn_sd80_sum2025.service.impl;
import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.ChucVu;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.ChucVuRepository;
import com.example.datn_sd80_sum2025.repository.NhanVienRepository;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

    @Service
    public class NhanVienServiceImpl implements NhanVienService {

        @Autowired
        NhanVienRepository nhanVienRepository;

        @Autowired
        ChucVuRepository chucVuRepository;

        @Override
        public NhanVien getById(Integer id) {
            return nhanVienRepository.findById(id).orElse(null);
        }

        @Override
        public List<NhanVien> getAll() {
            return nhanVienRepository.findAll();
        }

        @Override
        public NhanVien getOne(Integer id) {
            return nhanVienRepository.findById(id).get();    }

        @Override
        public NhanVien store(NhanVienCreate nhanVien) {
            ChucVu chucVu = chucVuRepository.findById(nhanVien.getIdChucVu())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ với id = " + nhanVien.getIdChucVu()));

            NhanVien nv = new NhanVien();
            nv.setHoTen(nhanVien.getHoTen());
            nv.setGioiTinh(nhanVien.getGioiTinh());
            nv.setSdt(nhanVien.getSdt());
            nv.setEmail(nhanVien.getEmail());
            nv.setDiaChi(nhanVien.getDiaChi());
            nv.setNgayLamViec(nhanVien.getNgayLamViec());
            nv.setTenTaiKhoan(nhanVien.getTenTaiKhoan());
            nv.setMatKhau(nhanVien.getMatKhau());
            nv.setChucVu(chucVu);
            nv.setTrangThai(nhanVien.getTrangThai());

            return nhanVienRepository.save(nv);
        }

        @Override
        public NhanVien update(NhanVienUpdate nhanVien) {
            NhanVien nv = nhanVienRepository.findById(nhanVien.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với id = " + nhanVien.getId()));

            ChucVu chucVu = chucVuRepository.findById(nhanVien.getIdChucVu())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ với id = " + nhanVien.getIdChucVu()));

            nv.setHoTen(nhanVien.getHoTen());
            nv.setGioiTinh(nhanVien.getGioiTinh());
            nv.setSdt(nhanVien.getSdt());
            nv.setEmail(nhanVien.getEmail());
            nv.setDiaChi(nhanVien.getDiaChi());
            nv.setNgayLamViec(nhanVien.getNgayLamViec());
            nv.setTenTaiKhoan(nhanVien.getTenTaiKhoan());
            nv.setMatKhau(nhanVien.getMatKhau());
            nv.setChucVu(chucVu);
            nv.setTrangThai(nhanVien.getTrangThai());

            return nhanVienRepository.save(nv);
        }

        @Override
        public void deleteById(Integer id) {
            nhanVienRepository.deleteById(id);
        }

    }