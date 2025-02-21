package com.kokoronikki.kokoronikki.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiaryResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
