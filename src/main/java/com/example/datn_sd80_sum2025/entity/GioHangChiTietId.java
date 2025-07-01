package com.example.datn_sd80_sum2025.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID; // nếu ID của bạn là UUID, thay đổi nếu cần

public class GioHangChiTietId implements Serializable {

    private UUID gioHang; // hoặc Long nếu ID là Long
    private UUID sach;

    public GioHangChiTietId() {
    }

    public GioHangChiTietId(UUID gioHang, UUID sach) {
        this.gioHang = gioHang;
        this.sach = sach;
    }

    // equals() và hashCode() là BẮT BUỘC cho @IdClass
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GioHangChiTietId)) return false;
        GioHangChiTietId that = (GioHangChiTietId) o;
        return Objects.equals(gioHang, that.gioHang) &&
                Objects.equals(sach, that.sach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gioHang, sach);
    }

    // getter/setter nếu cần
    public UUID getGioHang() {
        return gioHang;
    }

    public void setGioHang(UUID gioHang) {
        this.gioHang = gioHang;
    }

    public UUID getSach() {
        return sach;
    }

    public void setSach(UUID sach) {
        this.sach = sach;
    }
}
