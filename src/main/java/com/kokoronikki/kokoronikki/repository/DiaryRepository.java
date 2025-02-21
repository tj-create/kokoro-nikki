package com.kokoronikki.kokoronikki.repository;

import com.kokoronikki.kokoronikki.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
