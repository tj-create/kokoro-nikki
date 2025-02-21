package com.kokoronikki.kokoronikki.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kokoronikki.kokoronikki.controller.dto.DiaryRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @Setter
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void update(Diary updateDiary) {
        this.title = updateDiary.getTitle();
        this.content = updateDiary.getContent();
        this.createdAt = updateDiary.getCreatedAt();
    }

    public void convert(DiaryRequestDto diaryDto) {
        this.title = diaryDto.getTitle();
        this.content = diaryDto.getContent();
        this.createdAt = diaryDto.getCreatedAt();
    }
}
