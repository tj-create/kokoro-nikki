package com.kokoronikki.kokoronikki.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class DiaryRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String content;

    // 클라이언트가 직접 입력할 경우
    @NotNull(message = "작성일자는 필수입니다.")
    private LocalDateTime createdAt;
}
