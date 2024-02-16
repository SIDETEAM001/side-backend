package com.sideteam.groupsaver.domain.member.controller;

import com.sideteam.groupsaver.domain.member.service.MemberUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class MemberStatusController {

    private final MemberUpdateService memberUpdateService;

    @Operation(summary = "사용자 정지")
    @PostMapping("/suspend/{memberId}")
    public ResponseEntity<Void> suspendMember(@PathVariable Long memberId) {
        memberUpdateService.suspendMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "사용자 탈퇴")
    @DeleteMapping("/withdraw/{memberId}")
    public ResponseEntity<Void> withdrawMember(@PathVariable Long memberId) {
        memberUpdateService.withdrawMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
