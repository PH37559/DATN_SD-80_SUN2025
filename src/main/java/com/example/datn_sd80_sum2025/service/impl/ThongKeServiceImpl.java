package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.service.ThongKeService;


import com.example.datn_sd80_sum2025.dto.DoanhThuDTO;

import com.example.datn_sd80_sum2025.repository.HoaDonRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.Document;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<DoanhThuDTO> thongKeDoanhThu(int year, Integer month) {
        return hoaDonRepository.thongKeDoanhThu(year, month);
    }


    @Override
    public void exportDoanhThuPDF(Integer year, Integer month) {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("DoanhThu.pdf"));
            document.open();
            document.add(new Paragraph("Thống kê Doanh thu - " + year + (month != null ? " / Tháng " + month : "")));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
