package com.sideteam.groupsaver.controller.member;

import com.sideteam.groupsaver.service.member.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
public class memberController {
    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody @Valid Request request) {
        return ResponseEntity.ok(memberService.saveMember(request.toRequirement()) + "님 환영합니다!");
    }

    @GetMapping("/authenticateTest")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.ok("Successful");
    }

    record Request(
            @NotBlank(message = "email을 입력해주세요")
            @Email(message = "email 형식이 아닙니다")
            String email,

            @NotBlank
            String pw,

            @NotBlank
            String name

    ) {
        public MemberService.Requirement toRequirement(){
            return new MemberService.Requirement(email, pw, name);
        }
    }
}