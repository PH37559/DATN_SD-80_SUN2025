package com.example.datn_sd80_sum2025.service;

import com.example.datn_sd80_sum2025.entity.HoaDon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentTimeoutService {

    @Autowired
    private ZaloPayService zaloPayService;

    @Autowired
    private HoaDonService hoaDonService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleCancel(Integer idHoaDon, String appTransId) {
        scheduler.schedule(() -> {
            try {
                HoaDon hoaDon = hoaDonService.getById(idHoaDon);
                if (hoaDon != null && hoaDon.getTrangThai() == 0) { // 0 = chờ thanh toán
                    log.info("Kiểm tra trạng thái thanh toán cho HĐ #{}", idHoaDon);
                    zaloPayService.checkPaymentStatus(idHoaDon, appTransId);

                    HoaDon updated = hoaDonService.getById(idHoaDon);
                    if (updated.getTrangThai() == 0) {
                        hoaDonService.updateStatus(idHoaDon, 4); // 4 = đã hủy
                        log.warn("Hủy hóa đơn #{} vì quá hạn thanh toán", idHoaDon);
                    }
                }
            } catch (Exception e) {
                log.error("Lỗi khi xử lý timeout cho hóa đơn #{}: {}", idHoaDon, e.getMessage(), e);
            }
        }, 15, TimeUnit.MINUTES);
    }
}





