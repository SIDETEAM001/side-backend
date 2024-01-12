package com.sideteam.groupsaver.domain.board.service;

import com.sideteam.groupsaver.domain.board.dto.BoardRequestDto;
import com.sideteam.groupsaver.domain.board.dto.BoardResponseDto;
import com.sideteam.groupsaver.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardUpdateService {
    final BoardRepository noticeRepository;

    public BoardResponseDto service(BoardRequestDto dto) throws Exception {
        return noticeRepository.update(dto.toEntity());
    }
}
