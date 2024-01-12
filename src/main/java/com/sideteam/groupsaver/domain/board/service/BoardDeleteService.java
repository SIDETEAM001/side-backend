package com.sideteam.groupsaver.domain.board.service;

import com.sideteam.groupsaver.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardDeleteService {

    final BoardRepository noticeRepository;

    public String service(long seq) throws Exception {
        return noticeRepository.delete(seq) ? "SUCCESS" : "FAIL";
    }
}
