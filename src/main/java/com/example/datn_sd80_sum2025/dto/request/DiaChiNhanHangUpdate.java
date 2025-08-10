package com.example.datn_sd80_sum2025.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DiaChiNhanHangUpdate {

    private Integer id;

    @NotEmpty(message = "Họ tên không được để trống")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Tên không hợp lệ")
    @Size(max = 100, message = "Họ tên tối đa 50 ký tự")
    private String hoTen;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0\\d{9})$", message = "Số điện thoại không hợp lệ")
    private String sdt;

    @NotEmpty(message = "Vui lòng chọn thành phố")
    private String thanhPho;

    @NotEmpty(message = "Vui lòng chọn quận/huyện")
    private String quanHuyen;

    @NotEmpty(message = "Vui lòng chọn phường/xã")
    private String phuongXa;

    @NotEmpty(message = "Địa chỉ chi tiết không được để trống")
    private String diaChiChiTiet;

    private String ghiChu;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThaiDCCT;
}