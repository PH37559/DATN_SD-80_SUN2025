package com.example.datn_sd80_sum2025.config;

import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private NhanVienService nhanVienService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return username -> {
            NhanVien nv = nhanVienService.findByTenTaiKhoan(username);
            if (nv == null) {
                throw new RuntimeException("Không tìm thấy tài khoản: " + username);
            }

            String encodedPassword = encoder.encode(nv.getMatKhau());
            String role = nv.getChucVu().getTenChucVu().equalsIgnoreCase("Quản lý") ? "ADMIN" : "USER";

            return User.withUsername(username)
                    .password(encodedPassword)
                    .roles(role)
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/thong-ke/**", "/nhan-vien/**", "/chuc-vu/**",
                        "/hoa-don/delete/**", "/pgg/delete/**", "/khach-hang/delete/**",
                        "/san-pham/delete/**", "/ngon-ngu/delete/**", "/nxb/delete/**", "/the-loai/delete/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        "/trang-chu/**", "/hoa-don/**", "/pgg/**", "/khach-hang/**",
                        "/san-pham/**", "/ngon-ngu/**", "/nxb/**", "/the-loai/**"
                ).authenticated()
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/security/login")
                .loginProcessingUrl("/security/login")
                .defaultSuccessUrl("/trang-chu/hien-thi", false)
                .failureUrl("/security/login/error")
                .permitAll()
        );

        http.rememberMe(remember -> remember
                .tokenValiditySeconds(86400) // 1 ngày
        );

        http.exceptionHandling(ex -> ex
                .accessDeniedPage("/security/unauthorized")
        );

        http.logout(logout -> logout
                .logoutUrl("/security/logout")
                .logoutSuccessUrl("/security/logout/success")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
        );

        return http.build();
    }
}