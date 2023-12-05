package com.sideteam.groupsaver.domain.mypage.controller;

import com.sideteam.groupsaver.domain.mypage.dto.request.MyInfoUpdateRequest;
import com.sideteam.groupsaver.domain.mypage.dto.response.ClubFindResponse;
import com.sideteam.groupsaver.domain.mypage.dto.response.MyInfoFindResponse;
import com.sideteam.groupsaver.domain.mypage.service.MyPageService;
import com.sideteam.groupsaver.global.auth.userdetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 내 정보 조회
     * 내 정보 수정 ( 직무 수정,  관심사 ( 자기계발, 취미 ) 수정 )
     * 내 모임 목록
     * 찜한 모임 목록
     *
     * 회원 가입시 직무 & 자기계발 & 취미생활 데이터 받기
     * 탈퇴
     */

    @GetMapping("/myinfo/search")
    public ResponseEntity<MyInfoFindResponse> findMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(myPageService.findMyInfo(userDetails.getId()));
    }

    @PutMapping("/myinfo/update")
    public ResponseEntity<MyInfoFindResponse> updateMyInfo(@RequestBody @Valid MyInfoUpdateRequest dto,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(myPageService.updateMyInfo(dto, userDetails.getId()));
    }

    @GetMapping("/clubs/my")
    public ResponseEntity<Page<ClubFindResponse>> findMyClub(@RequestParam(required = false) Boolean star,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails,
                                                             @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(myPageService.findMyClub(star, userDetails.getId(), pageable));
    }
}
