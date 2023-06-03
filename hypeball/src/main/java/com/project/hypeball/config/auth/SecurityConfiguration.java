package com.project.hypeball.config.auth;

import com.project.hypeball.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin().disable() // 로그인 폼 미사용
                .httpBasic().disable() // Http basic Auth 기반으로 로그인 인증창이 열림(disable 시 인증창 열리지 않음)
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
                .and()
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/", "/oauth2/**", "/map/home", "/css/**","/image/**","/js/**", "/reviews/**").permitAll()
                                .anyRequest().authenticated())
                .logout().logoutUrl("/logout")
                    .logoutSuccessUrl("/")// 성공 후 리다이렉트
                    .invalidateHttpSession(true) // 세션 전체 삭제 여부
                    .deleteCookies("JSESSIONID") // 쿠키 삭제
                .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/map/home", true) // 성공 후 리다이렉트
                .userInfoEndpoint().userService(customOAuth2UserService);

        return http.build();
    }
}
