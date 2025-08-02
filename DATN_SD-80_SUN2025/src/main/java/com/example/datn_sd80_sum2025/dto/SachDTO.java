package com.example.datn_sd80_sum2025.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter
@Setter
public class SachDTO {
    @NotBlank(message = "Mã sách không được để trống")
    private String maSach;

    @NotBlank(message = "Tên sách không được để trống")
    private String tenSach;

    @NotNull(message = "Năm xuất bản không được để trống")
    @Min(value = 1900, message = "Năm xuất bản không hợp lệ")
    private Integer namXuatBan;

    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá bán phải lớn hơn 0")
    private BigDecimal giaBan;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải >= 0")
    private Integer soLuong;

    private String hinhAnh;

    @NotBlank(message = "Tác giả không được để trống")
    private String tacGia;

    @NotNull(message = "Ngôn ngữ không được để trống")
    private Integer ngonNguId;

    @NotNull(message = "Thể loại không được để trống")
    private Integer theLoaiId;

    @NotNull(message = "NXB không được để trống")
    private Integer nxbId;

    private String moTa;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThai;
}
