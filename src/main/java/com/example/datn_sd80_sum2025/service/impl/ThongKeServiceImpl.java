package com.example.datn_sd80_sum2025.service.impl;

import com.example.datn_sd80_sum2025.entity.DoanhThu;
import com.example.datn_sd80_sum2025.repository.HoaDonRepository;
import com.example.datn_sd80_sum2025.service.ThongKeService;
import com.lowagie.text.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<DoanhThu> thongKeDoanhThu(Integer year, Integer month) {
        return hoaDonRepository.thongKeDoanhThu(year, month);
    }


    @Override
    public void exportDoanhThuPDF(Integer year, Integer month) {
        // TODO: xuất file PDF (tạm thời export mẫu)
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
