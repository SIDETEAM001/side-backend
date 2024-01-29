package com.sideteam.groupsaver.domain.qna_reply.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.dto.request.QnaRequestDto;
import com.sideteam.groupsaver.domain.qna.dto.response.QnaResponseDto;
import com.sideteam.groupsaver.domain.qna.repository.QnaRepository;
import com.sideteam.groupsaver.domain.qna_reply.domain.QnaReply;
import com.sideteam.groupsaver.domain.qna_reply.dto.request.QnaReplyRequestDto;
import com.sideteam.groupsaver.domain.qna_reply.dto.response.QnaReplyResponseDto;
import com.sideteam.groupsaver.domain.qna_reply.repository.QnaReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class QnaReplyService {

    private final QnaRepository qnaRepository;
    private final QnaReplyRepository qnaReplyRepository;

    /* 댓글 등록 */
    @Transactional
    public QnaReplyResponseDto createQnaReply(QnaReplyRequestDto qnaReplyRequestDto, long qnaId, Long parentReplyId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("QNA 게시글이 없습니다 ! : " + qnaId));

        QnaReply parentReply = null;

        // 부모 ID가 있으면 해당 부모 댓글을 가져옴
        if (parentReplyId != null) {
            parentReply = qnaReplyRepository.findById(parentReplyId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 없습니다 ! : " + parentReplyId));
        }

        QnaReply qnaReply = qnaReplyRepository.save(QnaReply.of(qna, qnaReplyRequestDto, parentReply));
        return QnaReplyResponseDto.toDto(qnaReply);
    }

    /* 댓글 수정 */
    @Transactional
    public QnaReplyResponseDto updateQnaReply(long qnaId, Long id, QnaReplyRequestDto qnaReplyRequestDto) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new EntityNotFoundException("QNA 게시글이 없습니다 ! : " + qnaId));

        Long parentReplyId = qnaReplyRequestDto.getParentReplyId();

        if (parentReplyId != null) {
            // 대댓글인 경우
            QnaReply parentReply = qnaReplyRepository.findById(parentReplyId)
                    .orElseThrow(() -> new EntityNotFoundException("QNA 대댓글이 없습니다 ! : " + parentReplyId));

            parentReply.qnaReplyUpdate(qnaReplyRequestDto);

            // 저장 및 DTO로 변환 후 반환
            return QnaReplyResponseDto.toDto(qnaReplyRepository.save(parentReply));
        } else {
            // 댓글인 경우
            QnaReply qnaReply = qnaReplyRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("QNA 댓글이 없습니다 ! : " + id));

            qnaReply.qnaReplyUpdate(qnaReplyRequestDto);

            // 저장 및 DTO로 변환 후 반환
            return QnaReplyResponseDto.toDto(qnaReplyRepository.save(qnaReply));
        }
    }

    /* 댓글 삭제 */
    @Transactional
    public void deleteQnaReply(long qnaId, Long id) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new EntityNotFoundException("QNA 게시글이 없습니다 ! : " + qnaId));
        QnaReply qnaReply = qnaReplyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("QNA 댓글이 없습니다 ! : " + id));

        // 자식 댓글이 있는지 확인
        List<QnaReply> childReply = qnaReplyRepository.findByParentReply(qnaReply);
        if (!childReply.isEmpty()) {
            // 자식 댓글이 없으면 부모 댓글 삭제
            qnaReplyRepository.delete(qnaReply);
        } else {
            // 부모 댓글과 자식 댓글 모두 삭제
            qnaReplyRepository.deleteAll(childReply);
            qnaReplyRepository.delete(qnaReply);
        }
    }
}
