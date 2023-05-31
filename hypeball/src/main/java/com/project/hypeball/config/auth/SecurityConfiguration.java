package com.project.hypeball.config.auth;

import com.project.hypeball.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                    .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/", "/home").permitAll()
                                .anyRequest().authenticated())
                .logout().logoutUrl("/oauth2/logout")
                        .logoutSuccessUrl("/map/home")// 성공 후 리다이렉트
                        .invalidateHttpSession(true) // 세션 전체 삭제 여부
                .and()
                .oauth2Login()
                  .defaultSuccessUrl("/map/home", true) // 성공 후 리다이렉트
                  .userInfoEndpoint().userService(customOAuth2UserService);

        return http.build();
    }
}
