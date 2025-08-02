package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.Nxb;
import com.example.datn_sd80_sum2025.repository.NxbRepository;
import com.example.datn_sd80_sum2025.service.NXBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NXBServiceImpl implements NXBService {
    @Autowired
    private NxbRepository nxbRepository;

    @Override
    public List<Nxb> getAll() {
        return nxbRepository.findAll();
    }

    @Override
    public Nxb getById(Integer id) {
        Optional<Nxb> optional = nxbRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Nxb save(Nxb nxb) {
        return nxbRepository.save(nxb);
    }

    @Override
    public void deleteById(Integer id) {
        Nxb nxb = getById(id);
        nxb.setTrangThai(0); // chuyển trạng thái về 0 thay vì xóa
        nxbRepository.save(nxb);
    }
}
