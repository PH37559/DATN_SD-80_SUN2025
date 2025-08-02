package com.example.datn_sd80_sum2025.config;

import com.example.datn_sd80_sum2025.entity.NhanVien;
import com.example.datn_sd80_sum2025.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    NhanVienService nhanVienService;

    @Bean
    public PasswordEncoder myPasswordEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return username -> {
            NhanVien nv = nhanVienService.findByTenTaiKhoan(username);

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
        http.csrf().disable();

        http.authorizeHttpRequests()
                .requestMatchers("/thong-ke/**", "/nhan-vien/**", "/chuc-vu/**").hasRole("ADMIN")
                .requestMatchers("/hoa-don/delete/**", "/pgg/delete/**", "/khach-hang/delete/**",
                        "/san-pham/delete/**", "/ngon-ngu/delete/**", "/nxb/delete/**", "/the-loai/delete/**").hasRole("ADMIN")
                .requestMatchers
                        ("/trang-chu/**", "/hoa-don/**", "/pgg/**", "/khach-hang/**",
                                "/san-pham/**", "/ngon-ngu/**", "/nxb/**", "/the-loai/**"
                        ).authenticated()
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/security/login")
                .loginProcessingUrl("/security/login")
                .defaultSuccessUrl("/trang-chu/hien-thi", false)
                .failureUrl("/security/login/error");

        http.rememberMe().tokenValiditySeconds(86400);
        http.exceptionHandling().accessDeniedPage("/security/unauthorized");

        http.logout()
                .logoutUrl("/security/logout")
                .logoutSuccessUrl("/security/logout/success");

        return http.build();
    }
}
