package com.kokoronikki.kokoronikki.controller;

import com.kokoronikki.kokoronikki.controller.dto.DiaryRequestDto;
import com.kokoronikki.kokoronikki.controller.dto.DiaryResponseDto;
import com.kokoronikki.kokoronikki.domain.Diary;
import com.kokoronikki.kokoronikki.service.CustomUserDetails;
import com.kokoronikki.kokoronikki.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    //일기 작성: POST /api/diaries
    @PostMapping
    public ResponseEntity<DiaryResponseDto> createDiary(@Valid @RequestBody DiaryRequestDto diaryRequestDto) {
        Long userId = getCurrentUserId();
        Diary diary = diaryService.saveDiary(userId, convertToEntity(diaryRequestDto));
        DiaryResponseDto diaryResponseDto = convertToDto(diary);
        return ResponseEntity.status(201).body(diaryResponseDto);
    }

    //일기 목록 조회: GET /api/diaries
    @GetMapping
    public ResponseEntity<List<DiaryResponseDto>> getDiaries() {
        List<DiaryResponseDto> collect = diaryService.findAll(getCurrentUserId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }

    //일기 상세 조회: GET /api/diaries/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DiaryResponseDto> getDiary(@PathVariable("id") Long id) {
        Diary findDiary = diaryService.findById(id);
        DiaryResponseDto diaryResponseDto = convertToDto(findDiary);
        return ResponseEntity.ok(diaryResponseDto);
    }

    //일기 수정: PUT /api/diaries/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DiaryResponseDto> updateDiary(@PathVariable("id") Long id,
                                                        @Valid @RequestBody DiaryRequestDto diaryRequestDto) {
        Diary diary = diaryService.updateDiary(id, convertToEntity(diaryRequestDto));
        DiaryResponseDto diaryResponseDto = convertToDto(diary);
        return ResponseEntity.ok(diaryResponseDto);
    }

    //일기 삭제: DELETE /api/diaries/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable("id") Long id) {
        Long userId = getCurrentUserId();
        diaryService.deleteDiary(userId, id);
        return ResponseEntity.noContent().build();
    }

    private Diary convertToEntity(DiaryRequestDto diaryRequestDto) {
        Diary diary = new Diary();
        diary.convert(diaryRequestDto);
        return diary;
    }

    // 도메인 객체를 DTO로 변환하는 헬퍼 메서드
    private DiaryResponseDto convertToDto(Diary diary) {
        DiaryResponseDto dto = new DiaryResponseDto();
        dto.setId(diary.getId());
        dto.setTitle(diary.getTitle());
        dto.setContent(diary.getContent());
        dto.setCreatedAt(diary.getCreatedAt());
        return dto;
    }

    //현재 사용 유저의 id값을 가져오기
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdStr = (String) authentication.getPrincipal();
        return Long.parseLong(userIdStr);
    }
}
