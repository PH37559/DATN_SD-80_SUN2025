package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NhanVienService {
    Page<NhanVien> getAll(int page, int size);

    Page<NhanVien> search(String keyword, Integer chucVu, Integer trangThai, int page, int size);

    NhanVien getById(Integer id);

    void store(NhanVienCreate nhanVien);

    void update(Integer id, NhanVienUpdate nhanVien);

    void deleteById(Integer id);

    List<NhanVien> getAll();

    boolean existsBySdt(String sdt, Integer id);

    boolean existsByEmail(String email, Integer id);

    boolean existsByTaiKhoan(String tenTaiKhoan, Integer id);

    NhanVien findByTenTaiKhoan(String tenTaiKhoan);
}
