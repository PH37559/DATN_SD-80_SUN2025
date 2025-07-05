package com.example.datn_sd80_sum2025.controller;

import com.example.datn_sd80_sum2025.dto.request.NhanVienCreate;
import com.example.datn_sd80_sum2025.dto.request.NhanVienUpdate;
import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nhan-vien")
public class NhanVienController {

    @Autowired
    NhanVienService nhanVienService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<NhanVien>> getAll() {
        return ResponseEntity.ok(nhanVienService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(nhanVienService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody NhanVienCreate nhanVien, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(fieldErrors(result));
        }

        return ResponseEntity.ok(nhanVienService.store(nhanVien));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @Valid @RequestBody NhanVienUpdate nhanVien,
                                    BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(fieldErrors(result));
        }
        nhanVien.setId(id);
        return ResponseEntity.ok(nhanVienService.update(nhanVien));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        nhanVienService.deleteById(id);
    }

    private Map<String, String> fieldErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        result.getFieldErrors().forEach(e -> map.put(e.getField(), e.getDefaultMessage()));
        return map;
    }

}
