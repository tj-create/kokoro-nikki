package com.kokoronikki.kokoronikki;

import com.kokoronikki.kokoronikki.domain.User;
import com.kokoronikki.kokoronikki.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 여기서 임시 데이터를 생성하고 저장
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("testuser");
            user.setPassword("testpassword"); // 실제로는 암호화해서 저장해야 함
            user.setNickname("테스트닉네임");
            userRepository.save(user);
        }
    }
}