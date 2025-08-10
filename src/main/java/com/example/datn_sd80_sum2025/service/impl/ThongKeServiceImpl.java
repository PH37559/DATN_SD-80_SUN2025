package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.dto.TopKhachHangDTO;
import com.example.datn_sd80_sum2025.dto.TopSanPhamDTO;
import com.example.datn_sd80_sum2025.repository.HoaDonChiTietRepository;
import com.example.datn_sd80_sum2025.service.ThongKeService;


import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;

import com.example.datn_sd80_sum2025.repository.HoaDonRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.Document;

import java.io.FileOutputStream;
import java.util.List;

//xuat pdf
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

import java.io.ByteArrayOutputStream;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    private  HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<DoanhThuDTO> thongKeDoanhThu(int year, Integer month) {
        return hoaDonRepository.thongKeDoanhThu(year, month);
    }
//
//    @Override
//    public List<ThongKeSanPhamDTO> thongKeSanPham() {
//        return hoaDonRepository.thongKeSanPham();
//    }
//@Override
//public List<TopSanPhamDTO> thongKeTopSanPham(int year, Integer month) {
//    return hoaDonChiTietRepository.thongKeTopSanPham(year, month);
//}
@Override
public byte[] exportDoanhThuPDF(Integer year, Integer month) {
    List<DoanhThuDTO> danhSach = hoaDonRepository.thongKeDoanhThu(year, month);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Document document = new Document();

    try {
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Thống kê Doanh thu", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        Stream.of("Ngay", "So Hoa Don", "Tong tien").forEach(col -> {
            PdfPCell header = new PdfPCell();
            header.setPhrase(new Phrase(col, headerFont));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });

        for (DoanhThuDTO dto : danhSach) {
            table.addCell(dto.getNgayLap().toString());
            table.addCell(dto.getSoHoaDon().toString());
            table.addCell(dto.getTongTien().toString());
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    } catch (Exception e) {
        e.printStackTrace();
        return new byte[0];
    }
}
//xuat pdf top san pham
@Override
public byte[] exportTopSanPhamPDF(Integer year, Integer month) {
    Page<TopSanPhamDTO> page = hoaDonChiTietRepository
            .thongKeTopSanPhamTheoThoiGian(year, month, PageRequest.of(0, 10));

    List<TopSanPhamDTO> data = page.getContent();

    Document document = new Document();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    try {
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("Thống kê Top Sản phẩm bán chạy - " +
                year + (month != null ? "/" + month : "")));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.addCell("Ten Sach");
        table.addCell("So luong ban");
        table.addCell("Doanh thu");

        for (TopSanPhamDTO dto : data) {
            table.addCell(dto.getTenSach());
            table.addCell(String.valueOf(dto.getTongSoLuong()));
            table.addCell(dto.getTongDoanhThu().toString());
        }

        document.add(table);
        document.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return baos.toByteArray();
}
//export top khach hang
@Override
public byte[] exportTopKhachHangPDF(Integer year, Integer month) {
    Page<TopKhachHangDTO> page = hoaDonRepository
            .thongKeTopKhachHangTheoThoiGian(year, month, PageRequest.of(0, 50));

    List<TopKhachHangDTO> data = page.getContent();

    Document document = new Document();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    try {
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("Thống kê Top Khách hàng - " +
                year + (month != null ? "/" + month : "")));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell("Ho ten");
        table.addCell("Email");
        table.addCell("Tong don hang");
        table.addCell("Tong chi tieu");

        for (TopKhachHangDTO dto : data) {
            table.addCell(dto.getHoTen());
            table.addCell(dto.getEmail());
            table.addCell(String.valueOf(dto.getSoLuongHoaDon()));
            table.addCell(dto.getTongTien().toString());
        }

        document.add(table);
        document.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return baos.toByteArray();
}


    //top san pham
    @Override
    public Page<TopSanPhamDTO> thongKeTopSanPham(Integer year, Integer month, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hoaDonChiTietRepository.thongKeTopSanPhamTheoThoiGian(year, month, pageable);
    }
    //top khach hang
    @Override
    public Page<TopKhachHangDTO> thongKeTopKhachHang(Integer year, Integer month, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hoaDonRepository.thongKeTopKhachHangTheoThoiGian(year, month, pageable);
    }




}
