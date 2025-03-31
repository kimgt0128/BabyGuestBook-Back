package com.example.guestbook.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll() // 루트 경로는 누구나 접근 가능
                        .anyRequest().authenticated()); // 나머지 경로는 인증이 있어야 접근 가능

        //세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //oauth2
//        http
//                .oauth2Login(oauth ->
//                        oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
//                                .successHandler(oAuth2SuccessHandler)
//                                .failureHandler(oAuth2FailureHandler)
//                );
        return http.build();
    }
}