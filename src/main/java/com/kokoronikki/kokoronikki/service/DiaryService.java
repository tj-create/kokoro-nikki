package com.kokoronikki.kokoronikki.service;
import com.kokoronikki.kokoronikki.domain.Diary;
import com.kokoronikki.kokoronikki.domain.User;
import com.kokoronikki.kokoronikki.exception.DiaryNotFoundException;
import com.kokoronikki.kokoronikki.exception.UserNotFoundException;
import com.kokoronikki.kokoronikki.repository.DiaryRepository;
import com.kokoronikki.kokoronikki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    //save diary
    @Transactional
    public Diary saveDiary(Long userId, Diary diary) {
        Diary savedDiary = saveDiaryAtUser(userId, diary);
        return diaryRepository.save(savedDiary);
    }

    //update diary
    @Transactional
    public Diary updateDiary(Long diaryId, Diary updateDiary) {
        Diary findDiary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("diary id = " + diaryId + " not found"));
        findDiary.update(updateDiary);

        return findDiary;
    }

    //find All
    public List<Diary> findAll(Long userId) {

        return diaryRepository.findByUserId(userId);
    }

    //find By Id
    public Diary findById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryNotFoundException("diary id = " + diaryId + " not found"));
    }
    //delete

    @Transactional
    public void deleteDiary(Long userId, Long diaryId) {
        Diary deleteDiary = findById(diaryId);

        deleteDiaryAtUser(userId, deleteDiary);

        diaryRepository.delete(deleteDiary);
    }

    private void deleteDiaryAtUser(Long userId, Diary deleteDiary) {
        User deleteUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with id " + userId + "not found user"));

        deleteUser.deleteDiary(deleteDiary);
    }

    private Diary saveDiaryAtUser(Long userId, Diary diary) {
        User saveUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with id " + userId + "not found user"));

        saveUser.addDiary(diary);

        return diary;
    }


}
