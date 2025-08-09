package com.example.datn_sd80_sum2025.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DiaChiNhanHangCreate {

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