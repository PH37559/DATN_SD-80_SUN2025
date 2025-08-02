package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.dto.request.ChucVuCreate;
import com.example.datn_sd80_sum2025.dto.request.ChucVuUpdate;
import com.example.datn_sd80_sum2025.entity.ChucVu;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.repository.ChucVuRepository;
import com.example.datn_sd80_sum2025.repository.NhanVienRepository;
import com.example.datn_sd80_sum2025.service.ChucVuService;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChucVuServiceImpl implements ChucVuService {

    @Autowired
    ChucVuRepository chucVuRepository;

    @Override
    public List<ChucVu> getAll() {
        return chucVuRepository.getAll();
    }

    @Override
    public Page<ChucVu> getAll(int page, int size) {
        return chucVuRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<ChucVu> search(String keyword, Integer trangThai, int page, int size) {
        return chucVuRepository.search(keyword, trangThai, PageRequest.of(page, size));
    }

    @Override
    public ChucVu getById(Integer id) {
        return chucVuRepository.findById(id).get();
    }

    @Override
    public void store(ChucVuCreate chucVu) {
        chucVuRepository.store(
                chucVu.getTenChucVu(),
                chucVu.getTrangThai()
        );
    }

    @Override
    public void update(Integer id, ChucVuUpdate chucVu) {
        if (id != null) {
            chucVuRepository.update(
                    id,
                    chucVu.getTenChucVu(),
                    chucVu.getTrangThai()
            );
            return;
        }
        throw new IllegalArgumentException("Vui lòng chọn chức vụ");
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Vui lòng chọn chức vụ");
        }
        ChucVu cv = chucVuRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Không tìm thấy chức vụ"));
        cv.setTrangThai(0);
        chucVuRepository.save(cv);
    }

    @Override
    public boolean existByTenChucVu(String tenChucVu, Integer id) {
        return chucVuRepository.existsByTenChucVu(tenChucVu, id);
    }
}