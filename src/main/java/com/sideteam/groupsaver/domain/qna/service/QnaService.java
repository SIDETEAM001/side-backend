package com.sideteam.groupsaver.domain.qna.service;

import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.dto.request.QnaRequestDto;
import com.sideteam.groupsaver.domain.qna.dto.response.QnaResponseDto;
import com.sideteam.groupsaver.domain.qna.repository.QnaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class QnaService {

    private final QnaRepository qnaRepository;

    /* 전체 조회 */
    @Transactional
    public List<Qna> getAllQna() {
        return qnaRepository.findAll();
    }

    /* 단건 조회 */
    @Transactional
    public QnaResponseDto getQnaById(Long id) {
        //id에 맞는 정보를 가져온다, 없으면 예외 발생
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 없습니다 ! : " + id));
        return QnaResponseDto.toDto(qna);
    }

    /* 질문 등록 */
    public QnaResponseDto createQna(QnaRequestDto qnaRequestDto) {
        // 등록 정보가 담긴 DTO를 Entity로 변환하여 저장
        Qna qna = qnaRepository.save(QnaRequestDto.toEntity(qnaRequestDto));
        return QnaResponseDto.toDto(qna);
    }

    /* 질문 수정 */
    public QnaResponseDto updateQna(Long id, QnaRequestDto qnaRequestDto) {
        /*
            findById = Entity 반환
            existByid = 존재 여부 Boolean 반환
        */
        // id가 존재한다면
        if(qnaRepository.existsById(id)) {
            Qna qna = qnaRepository.findById(id).orElseThrow();
            qna.qnaUpdate(qnaRequestDto);

            // 저장 및 DTO로 변환 후 반환
            return QnaResponseDto.toDto(qnaRepository.save(qna));
        } else {
            throw new IllegalArgumentException("ID가 없습니다 ! : " + id);
        }
    }

    /* 질문 삭제 */
    public void deleteQna(Long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 없습니다 ! : " + id));
        qnaRepository.delete(qna);
    }
}
