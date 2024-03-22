package com.sideteam.groupsaver.domain.likes.controller;


import com.sideteam.groupsaver.domain.likes.domain.Likes;
import com.sideteam.groupsaver.domain.likes.dto.request.LikesRequestDto;
import com.sideteam.groupsaver.domain.likes.service.LikesService;
import com.sideteam.groupsaver.domain.report.dto.ReportClubRequest;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/likes")
public class LikesController {

    private final LikesService likesService;

    // 좋아요
    @PostMapping
    public ResponseEntity<Likes> addLikesQna(@RequestBody LikesRequestDto likesRequestDto, @MemberIdParam Long memberId) {
        return ResponseEntity.ok((Likes) likesService.addLikesQna(likesRequestDto, memberId));
    }

    /* 좋아요 취소 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLikesQna(@PathVariable Long id, @MemberIdParam Long memberId) {
        likesService.deleteLikesQna(id, memberId);
        return ResponseEntity.noContent().build();
    }

}
