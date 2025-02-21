package com.kokoronikki.kokoronikki.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String jwtSecret;

    public JwtAuthenticationFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                // JWT 토큰 검증
                Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
                var verifier = JWT.require(algorithm).build();
                var decodedJWT = verifier.verify(token);

                // subject에 사용자 ID를 저장했다고 가정
                String userId = decodedJWT.getSubject();
                // 인증 객체 생성 (여기서는 권한은 빈 리스트로 처리)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
                // SecurityContext에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException ex) {
                // 토큰이 유효하지 않은 경우, 여기서 로그를 남기거나 처리할 수 있습니다.
                logger.error("JWT 토큰 검증 실패: {}", ex);
            }
        }
        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}
