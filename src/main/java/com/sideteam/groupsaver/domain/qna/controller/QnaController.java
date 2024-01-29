package com.sideteam.groupsaver.domain.qna.controller;

import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.dto.request.QnaRequestDto;
import com.sideteam.groupsaver.domain.qna.dto.response.QnaResponseDto;
import com.sideteam.groupsaver.domain.qna.service.QnaService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/qna")
public class QnaController {

    private final QnaService qnaService;

    /* 전체 조회 */
    @GetMapping
    public ResponseEntity<List<QnaResponseDto>> getAllQna() {
        return ResponseEntity.ok(qnaService.getAllQna());
    }

    /* 단건 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<QnaResponseDto> getQnaById(@PathVariable Long id) {
        return ResponseEntity.ok(qnaService.getQnaById(id));
    }

    /* 질문 등록 */
    @PostMapping
    public ResponseEntity<QnaResponseDto> createQna(@Valid @RequestBody QnaRequestDto qnaRequestDto) {
        return ResponseEntity.ok(qnaService.createQna(qnaRequestDto));
    }

    /* 질문 수정 */
    @PatchMapping("/{id}")
    public ResponseEntity<QnaResponseDto> updateQna(@PathVariable Long id, @Valid @RequestBody QnaRequestDto qnaRequestDto) {
        return  ResponseEntity.ok(qnaService.updateQna(id, qnaRequestDto));
    }

    /* 질문 삭제 */
    // Void = 해당 메서드를 반환하는데 반환할 데이터가 없음을 나타내기 위해
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQna(@PathVariable Long id) {
        qnaService.deleteQna(id);
        return ResponseEntity.noContent().build();
    }

}
