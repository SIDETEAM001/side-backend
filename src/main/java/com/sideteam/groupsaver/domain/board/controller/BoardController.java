package com.sideteam.groupsaver.domain.board.controller;

import com.sideteam.groupsaver.domain.board.dto.BoardRequestDto;
import com.sideteam.groupsaver.domain.board.dto.BoardResponseDto;
import com.sideteam.groupsaver.domain.board.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController extends RootController{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    final BoardListService boardListService;
    final BoardCreateService boardCreateService;
    final BoardUpdateService boardUpdateService;
    final BoardDeleteService boardDeleteService;
    final BoardReadService boardReadService;

    //List 가져오기
    @GetMapping("/getBoardList")
    public ResponseEntity<List<BoardResponseDto>> getBoardList () throws Exception {
        logger.info("getBoardList");
        return ResponseEntity.ok(boardListService.service());
    }

    //게시판 글 작성
    @PostMapping("/create")
    public ResponseEntity<BoardResponseDto> createPost(@Valid @RequestBody BoardRequestDto dto) throws Exception{
        logger.info("createPost");
        return ResponseEntity.ok().body(boardCreateService.service(dto));
    }

    //게시판 글 수정
    @PatchMapping("/update/{id}")
    public ResponseEntity<BoardResponseDto> updatePost(@Valid @RequestBody BoardRequestDto dto) throws Exception{
        logger.info("createPost");
        return ResponseEntity.ok().body(boardUpdateService.service(dto));
    }


    //게시판 삭제
    @DeleteMapping("/delete/{id}")
    public String deletePost(@RequestParam(name = "id", required = true) Long id) throws Exception{
        logger.info("createPost");

        return boardDeleteService.service(id);
    }

    //게시판 읽기
    @GetMapping("/read/{id}")
    public ResponseEntity<BoardResponseDto> readPost (@RequestParam(name = "id", required = true) Long id) throws Exception {
        logger.info("getNoticeList");
        return ResponseEntity.ok(boardReadService.service(id));
    }
}
