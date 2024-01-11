package com.sideteam.groupsaver.domain.qna.controller;

import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.dto.request.QnaRequestDto;
import com.sideteam.groupsaver.domain.qna.dto.response.QnaResponseDto;
import com.sideteam.groupsaver.domain.qna.service.QnaService;
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
    @GetMapping("/getQnaList")
    public ResponseEntity<List<QnaResponseDto>> getAllQna() {
        /* Service에서 모든 정보를 조회하여 List<Qna>로 받아옴 */
        List<Qna> qnaEntityList = qnaService.getAllQna();

        List<QnaResponseDto> qnaDtoList = qnaEntityList
                .stream() // List -> stream()으로 변환
                .map(QnaResponseDto::toDto) // 각각의 Entity를 DTO로 변환
                .collect(Collectors.toList()); // stream() -> List 변환
        return new ResponseEntity<>(qnaDtoList, HttpStatus.OK);
    }

    /* 단건 조회 */
    @GetMapping("/getQna/{id}")
    public ResponseEntity<QnaResponseDto> getQnaById(@PathVariable Long id) {
        /* Service에서 Controller로 넘겨줄 때 Entity -> DTO로 변환함 */
        QnaResponseDto qnaResponseDto = qnaService.getQnaById(id);

        if(qnaResponseDto == null) { // DTO가 null이면 404 에러 코드 반환
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else { // 성공 시 200 코드 반환
            return new ResponseEntity<>(qnaResponseDto, HttpStatus.OK);
        }
    }

    /* 질문 등록 */
    @PostMapping("/createQna")
    public ResponseEntity<QnaResponseDto> createQna(@Valid @RequestBody QnaRequestDto qnaRequestDto) {
        // 등록 정보가 담긴 DTO를 Service로 넘겨줌
        QnaResponseDto createdQna = qnaService.createQna(qnaRequestDto);

        // 정보를 ResponseEntity로 감싸서 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQna);
    }

    /* 질문 수정 */
    @PatchMapping("/updateQna/{id}")
    public ResponseEntity<QnaResponseDto> updateQna(@PathVariable Long id, @Valid @RequestBody QnaRequestDto qnaRequestDto) {
        try {
            // Service에서 업데이트하고 결과를 가져옴
            QnaResponseDto qnaResponseDto = qnaService.updateQna(id, qnaRequestDto);
            // 성공 시 OK 응답 반환
            return  ResponseEntity.ok(qnaResponseDto);
        } catch (IllegalArgumentException e) {
            // 실패 시 Not Found 응답 반환
            return ResponseEntity.notFound().build();
        }
    }

    /* 질문 삭제 */
    // Void = 해당 메서드를 반환하는데 반환할 데이터가 없음을 나타내기 위해
    @DeleteMapping("/deleteQna/{id}")
    public ResponseEntity<Void> deleteQna(@PathVariable Long id) {
        qnaService.deleteQna(id);
        return ResponseEntity.noContent().build();
    }

}
