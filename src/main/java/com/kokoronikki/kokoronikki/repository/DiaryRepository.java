package com.kokoronikki.kokoronikki.repository;

import com.kokoronikki.kokoronikki.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserId(Long userId);
}
