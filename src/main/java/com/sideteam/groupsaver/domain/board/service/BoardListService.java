package com.sideteam.groupsaver.domain.board.service;

import com.sideteam.groupsaver.domain.board.domain.Board;
import com.sideteam.groupsaver.domain.board.dto.BoardResponseDto;
import com.sideteam.groupsaver.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardListService {

    final BoardRepository noticeRepository;

    public List<BoardResponseDto> service() throws Exception {
        // 메인 공지사항 목록 조회
        List<Board> list = noticeRepository.noticeList();

        List<BoardResponseDto> dto  = list.stream().map(BoardResponseDto::map).collect(Collectors.toList());
        return dto;
    }


}
