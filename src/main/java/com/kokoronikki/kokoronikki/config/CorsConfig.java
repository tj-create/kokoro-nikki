package com.kokoronikki.kokoronikki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //프론트엔드 오리진 설정 : React 앱이 실행되는 주소
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        //허용할 HTTP 메서드 실행
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        //모든 헤더 허용
        configuration.setAllowedHeaders(List.of("*"));
        //쿠키나 인증 정보를 허용할지 여부
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //모든 경로에 대해 위의 CORS 설정 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
