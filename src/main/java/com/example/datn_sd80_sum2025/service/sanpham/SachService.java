package com.example.datn_sd80_sum2025.service.sanpham;

import com.example.datn_sd80_sum2025.dto.SachDTO;
import com.example.datn_sd80_sum2025.entity.Sach;
import com.example.datn_sd80_sum2025.repository.NgonNguRepository;
import com.example.datn_sd80_sum2025.repository.NxbRepository;
import com.example.datn_sd80_sum2025.repository.SachRepository;
import com.example.datn_sd80_sum2025.repository.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class SachService {
    @Autowired
    private SachRepository sachRepository;
    @Autowired
    private NgonNguRepository ngonNguRepository;
    @Autowired
    private NxbRepository nxbRepository;
    @Autowired
    private TheLoaiRepository theLoaiRepository;

    public List<Sach> getAll() {
        return sachRepository.findAll();
    }

    public Sach getById(Integer id) {
        return sachRepository.findById(id).orElse(null);
    }

    public void save(Sach sach) {
        sachRepository.save(sach);
    }

    public void delete(Integer id) {
        Sach sach = sachRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với id: " + id));
        sach.setTrangThai(0);  // chuyển trạng thái về 0
        sachRepository.save(sach);

    }
    // Lấy danh sách tất cả sách theo trang
    public Page<Sach> getAllPaged(int page) {
        return sachRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    // Tìm kiếm theo tên sách (có phân trang)
    public Page<Sach> searchByTenSach(String keyword, int page) {
        return sachRepository.findByTenSachContainingIgnoreCase(keyword, PageRequest.of(page, PAGE_SIZE));
    }
    private static final int PAGE_SIZE = 1000;

    public void saveFromDTO(SachDTO dto) {
        Sach sach = new Sach();
        sach.setMaSach(dto.getMaSach());
        sach.setTenSach(dto.getTenSach());
        sach.setNamXuatBan(dto.getNamXuatBan());
        sach.setGiaBan(dto.getGiaBan());
        sach.setSoLuong(dto.getSoLuong());
        sach.setTacGia(dto.getTacGia());
        sach.setTrangThai(dto.getTrangThai());
        sach.setMoTa(dto.getMoTa());
        sach.setHinhAnh(dto.getHinhAnh());

        // findById để gán quan hệ
        sach.setNgonNgu(ngonNguRepository.findById(dto.getNgonNguId()).orElse(null));
        sach.setTheLoai(theLoaiRepository.findById(dto.getTheLoaiId()).orElse(null));
        sach.setNxb(nxbRepository.findById(dto.getNxbId()).orElse(null));

        sachRepository.save(sach);
    }

    public boolean existsByMaSach(String maSach) {
        if (maSach == null) return false;
        // Bước 1: loại bỏ khoảng trắng đầu cuối
        String cleaned = maSach.trim();
        // Bước 2: xoá toàn bộ khoảng trắng ở giữa và ký tự đặc biệt, chỉ giữ a–z, A–Z, 0–9
        cleaned = cleaned.replaceAll("[^a-zA-Z0-9]", "");
        return sachRepository.existsByMaSach(cleaned);
    }

}
