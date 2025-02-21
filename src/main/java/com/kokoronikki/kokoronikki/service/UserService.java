package com.kokoronikki.kokoronikki.service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kokoronikki.kokoronikki.controller.dto.SignupDto;
import com.kokoronikki.kokoronikki.domain.User;
import com.kokoronikki.kokoronikki.exception.UserNotFoundException;
import com.kokoronikki.kokoronikki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String jwtSecret = "mySecretKey";
    private final long jwtExpirationMs = 86400000; // 24시간

    /**
     * 로그인 기능
     * 1. 클라이언트에서 전달받은 username과 password를 사용하여 사용자를 조회한다.
     * 2. 사용자가 존재하지 않으면 UsernameNotFoundException 발생.
     * 3. 비밀번호가 일치하지 않으면 BadCredentialsException 발생.
     * 4. 자격증명이 성공하면, JWT 토큰을 생성하여 반환한다.
     *
     * @param username 클라이언트가 전달한 username
     * @param password 클라이언트가 전달한 평문 password
     * @return JWT 토큰 문자열
     */
    @Transactional
    public String login(String username, String password) {
        // 1. username으로 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found username = " + username));

        // 2. 입력된 평문 password와 저장된 암호화된 password 비교
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }

        //3. 자격 증명이 성공하면 JWT 토근 생성
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        return JWT.create()
                .withSubject(Long.toString(user.getId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(algorithm);
    }


    @Transactional
    public void registerUser(SignupDto signupDto) {
        if (userRepository.findByUsername(signupDto.getUsername()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }

        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setNickname(signupDto.getNickname());


        userRepository.save(user);
    }


    //update user
    @Transactional
    public void updateUser(Long userId, User updateUser) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with id " + userId + "not found user"));
        findUser.update(updateUser);
    }


    //find user ( 전체 조회 )
    public List<User> findAll() {
        return userRepository.findAll();
    }


    //find user by id (id로 검색)
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with id " + userId + "not found user"));
    }

    //delete
    @Transactional
    public void deleteUser(Long userId) {
        User deleteUser = findById(userId);
        userRepository.delete(deleteUser);
    }
}
