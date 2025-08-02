package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.TheLoai;
import com.example.datn_sd80_sum2025.repository.TheLoaiRepository;
import com.example.datn_sd80_sum2025.service.TheLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheLoaiServiceImpl implements TheLoaiService {
    @Autowired
    private TheLoaiRepository theLoaiRepository;

    @Override
    public List<TheLoai> getAll() {
        return theLoaiRepository.findAll();
    }

    @Override
    public TheLoai getById(Integer id) {
        Optional<TheLoai> optional = theLoaiRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public TheLoai save(TheLoai theLoai) {
        return theLoaiRepository.save(theLoai);
    }

    @Override
    public void delete(Integer id) {
        TheLoai tl = getById(id);
        if (tl != null) {
            tl.setTrangThai(0);
            theLoaiRepository.save(tl);
        }
    }
    @Override
    public List<TheLoai> searchByName(String keyword) {
        return theLoaiRepository.findByTenTheLoaiContainingIgnoreCase(keyword);
    }
}
