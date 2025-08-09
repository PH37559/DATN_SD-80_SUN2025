package com.example.datn_sd80_sum2025.service;


import com.example.datn_sd80_sum2025.dto.request.ChucVuCreate;
import com.example.datn_sd80_sum2025.dto.request.ChucVuUpdate;
import com.example.datn_sd80_sum2025.entity.ChucVu;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChucVuService {

    List<ChucVu> getAll();

    Page<ChucVu> getAll(int page, int size);

    Page<ChucVu> search(String keyword, Integer trangThai, int page, int size);

    ChucVu getById(Integer id);

    void store(ChucVuCreate chucVu);

    void update(Integer id, ChucVuUpdate chucVu);

    void deleteById(Integer id);

    boolean existByTenChucVu(String tenChucVu, Integer id);

}