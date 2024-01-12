package com.sideteam.groupsaver.domain.board.service;

import com.sideteam.groupsaver.domain.board.dto.BoardResponseDto;
import com.sideteam.groupsaver.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardReadService {

    final BoardRepository boardRepository;

    public BoardResponseDto service(Long id) throws Exception {
        return boardRepository.read(id);
    }
}
