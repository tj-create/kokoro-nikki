package com.kokoronikki.kokoronikki.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Column(nullable = false)
    @NotBlank
    private String nickname;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();

    // 일기장 추가 로직 해당 일기장에도 user의 정보가 들어감
    public void addDiary(Diary diary) {
        diaries.add(diary);
        diary.setUser(this);
    }

    // 일기장 삭제 로직 해당 일기장에도 user의 정보가 삭제 고아객체 이므로 연결이 끊어지면 자동 삭제
    public void deleteDiary(Diary diary) {
        diaries.remove(diary);
        diary.setUser(null);
    }

    public void update(User updateUser) {
       this.username = updateUser.getUsername();
       this.password = updateUser.getPassword();
       this.nickname = updateUser.getNickname();
    }
}
