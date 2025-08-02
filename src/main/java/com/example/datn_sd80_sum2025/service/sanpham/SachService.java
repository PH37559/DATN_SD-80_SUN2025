package com.example.datn_sd80_sum2025.service.sanpham;

import com.example.datn_sd80_sum2025.dto.SachDTO;
import com.example.datn_sd80_sum2025.dto.TonKhoDTO;
import com.example.datn_sd80_sum2025.entity.Sach;
import com.example.datn_sd80_sum2025.repository.NgonNguRepository;
import com.example.datn_sd80_sum2025.repository.NxbRepository;
import com.example.datn_sd80_sum2025.repository.SachRepository;
import com.example.datn_sd80_sum2025.repository.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class SachService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private SachRepository sachRepository;

    // Các repo khác ...
    @Autowired
    private NgonNguRepository ngonNguRepository;

    @Autowired
    private NxbRepository nxbRepository;

    @Autowired
    private TheLoaiRepository theLoaiRepository;

    // Lấy danh sách tất cả sách theo phân trang
    public Page<Sach> getAllPaged(int page, int size) {
        return sachRepository.findAll(PageRequest.of(page, size));
    }

    // Tìm kiếm sách theo tên, có phân trang
    public Page<Sach> searchByTenSach(String keyword, int page, int size) {
        return sachRepository.findByTenSachContainingIgnoreCase(keyword, PageRequest.of(page, size));
    }

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
        sach.setTrangThai(0);
        sachRepository.save(sach);
    }

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
        sach.setNgonNgu(ngonNguRepository.findById(dto.getNgonNguId()).orElse(null));
        sach.setTheLoai(theLoaiRepository.findById(dto.getTheLoaiId()).orElse(null));
        sach.setNxb(nxbRepository.findById(dto.getNxbId()).orElse(null));
        sachRepository.save(sach);
    }

    public boolean existsByMaSach(String maSach) {
        if (maSach == null) return false;
        String cleaned = maSach.trim().replaceAll("[^a-zA-Z0-9]", "");
        return sachRepository.existsByMaSach(cleaned);
    }

    public boolean capNhatSoLuongSauKhiThem(int idSach, int soLuongThem) {
        Optional<Sach> optional = sachRepository.findById(idSach);
        if (optional.isEmpty()) return false;

        Sach sach = optional.get();
        if (sach.getSoLuong() < soLuongThem) {
            return false;
        }
        sach.setSoLuong(sach.getSoLuong() - soLuongThem);
        if (sach.getSoLuong() == 0) {
            sach.setTrangThai(0);
        }

        sachRepository.save(sach);
        return true;
    }
    public void congSoLuongSauKhiXoa(int idSach, int soLuongXoa) {
        Optional<Sach> optional = sachRepository.findById(idSach);
        if (optional.isEmpty()) return;
        Sach sach = optional.get();
        sach.setSoLuong(sach.getSoLuong() + soLuongXoa);

        if (sach.getTrangThai() == 0 && sach.getSoLuong() > 0) {
            sach.setTrangThai(1);
        }

        sachRepository.save(sach);
    }
//    public List<TonKhoDTO> thongKeTonKho() {
//        return sachRepository.thongKeTonKho();
//    }
}

