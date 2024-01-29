package com.sideteam.groupsaver.domain.qna_reply.controller;

import com.sideteam.groupsaver.domain.qna.dto.request.QnaRequestDto;
import com.sideteam.groupsaver.domain.qna.dto.response.QnaResponseDto;
import com.sideteam.groupsaver.domain.qna.service.QnaService;
import com.sideteam.groupsaver.domain.qna_reply.domain.QnaReply;
import com.sideteam.groupsaver.domain.qna_reply.dto.request.QnaReplyRequestDto;
import com.sideteam.groupsaver.domain.qna_reply.dto.response.QnaReplyResponseDto;
import com.sideteam.groupsaver.domain.qna_reply.service.QnaReplyService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/qnaReply")
public class QnaReplyController {

    private final QnaReplyService qnaReplyService;

    /* 댓글 등록 */
    @PostMapping("/{qnaId}")
    public ResponseEntity<QnaReplyResponseDto> createQnaReply(@PathVariable long qnaId,
                                                              @RequestParam(required = false) Long parentReplyId,
                                                              @Valid @RequestBody QnaReplyRequestDto qnaReplyRequestDto) {
        return ResponseEntity.ok(qnaReplyService.createQnaReply(qnaReplyRequestDto, qnaId, parentReplyId));
    }

    /* 댓글 수정 */
    @PatchMapping("/{qnaId}/{id}")
    public ResponseEntity<QnaReplyResponseDto> updateQnaReply(@PathVariable long qnaId, @PathVariable Long id,
                                                              @Valid @RequestBody QnaReplyRequestDto qnaReplyRequestDto) {
        return ResponseEntity.ok(qnaReplyService.updateQnaReply(qnaId, id, qnaReplyRequestDto));
    }

    /* 댓글 삭제 */
    @DeleteMapping("/{qnaId}/{id}")
    public ResponseEntity<Void> deleteQnaReply(@PathVariable long qnaId, @PathVariable Long id) {
        qnaReplyService.deleteQnaReply(qnaId, id);
        return ResponseEntity.noContent().build();
    }



}
