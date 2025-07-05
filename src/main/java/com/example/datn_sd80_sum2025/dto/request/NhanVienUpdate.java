package com.example.datn_sd80_sum2025.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class NhanVienUpdate {

    private Integer id;

    @NotEmpty(message = "Họ tên không được để trống")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Tên không hợp lệ")
    private String hoTen;

    @NotNull(message = "Giới tính không được để trống")
    private Boolean gioiTinh;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0\\d{9})$", message = "Số điện thoại không hợp lệ")
    private String sdt;

    @NotEmpty(message = "Email không được để trống")
    @Pattern(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Email sai định dạng theo pattern")
    private String email;

    @NotEmpty(message = "Địa chỉ không được để trống")
    private String diaChi;

    @NotNull(message = "Ngày làm việc không được để trống")
    private LocalDate ngayLamViec;

    @NotEmpty(message = "Tên tài khoản không được để trống")
    private String tenTaiKhoan;

    @NotEmpty(message = "Mật khẩu không được để trống")
    private String matKhau;

    @NotNull(message = "Chức vụ không được để trống")
    private Integer idChucVu;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThai;
}