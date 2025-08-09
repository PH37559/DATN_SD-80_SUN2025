package com.example.datn_sd80_sum2025.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class NhanVienCreate {

    @NotEmpty(message = "Họ tên không được để trống")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Tên không hợp lệ")
    @Size(max = 100, message = "Họ tên tối đa 50 ký tự")
    private String hoTen;

    @NotNull(message = "Giới tính không được để trống")
    private Boolean gioiTinh;

    @NotEmpty(message = "Địa chỉ không được để trống")
    @Size(max = 100, message = "Địa chỉ tối đa 100 ký tự")
    private String diaChi;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0\\d{9})$", message = "Số điện thoại không hợp lệ")
    private String sdt;

    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    @Size(max = 100, message = "Email tối đa 100 ký tự")
    private String email;

    @NotNull(message = "Ngày làm việc không được để trống")
    @PastOrPresent(message = "Ngày làm việc không được lớn hơn ngày hiện tại")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayLamViec;

    @NotEmpty(message = "Tên tài khoản không được để trống")
    @Size(max = 50, message = "Tài khoản tối đa 50 ký tự")
    private String tenTaiKhoan;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(max = 50, message = "Mật khẩu tối đa 50 ký tự")
    private String matKhau;

    @NotNull(message = "Chức vụ không được để trống")
    private Integer idChucVu;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThai;
}