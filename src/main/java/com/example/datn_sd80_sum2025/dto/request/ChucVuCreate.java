package com.example.datn_sd80_sum2025.dto.request;

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
public class ChucVuCreate {

    @NotEmpty(message = "Tên chức vụ không được để trống")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Tên không hợp lệ")
    @Size(max = 100, message = "Tên chức vụ tối đa 50 ký tự")
    private String tenChucVu;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThai;
}