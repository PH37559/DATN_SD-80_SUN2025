package com.example.datn_sd80_sum2025.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID; // nếu ID của bạn là UUID, thay đổi nếu cần

public class GioHangChiTietId implements Serializable {

    private GioHang gioHang;
    private Sach sach;

    public GioHangChiTietId() {}

    public GioHangChiTietId(GioHang gioHang, Sach sach) {
        this.gioHang = gioHang;
        this.sach = sach;
    }

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

    // Getter/setter
    public GioHang getGioHang() {
        return gioHang;
    }

    public void setGioHang(GioHang gioHang) {
        this.gioHang = gioHang;
    }

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }
}



