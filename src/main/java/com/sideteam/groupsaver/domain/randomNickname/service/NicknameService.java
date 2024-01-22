package com.sideteam.groupsaver.domain.randomNickname.service;

import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.randomNickname.dto.NicknameResponseDto;
import com.sideteam.groupsaver.domain.randomNickname.repository.RandomNicknameADResource;
import com.sideteam.groupsaver.domain.randomNickname.repository.RandomNicknameNounResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class NicknameService {
    private final RandomNicknameNounResource nounResource;
    private final RandomNicknameADResource adResource;
    private final MemberRepository memberRepository;

    // 만약 조합할 수 있는 단어롤 다 사용했다면 어떻게 처리할것인가
    public NicknameResponseDto createNickName() {
        String nickname;
        do {
            nickname = adResource.findAdById(new Random().nextLong(10) + 1) +
                    nounResource.findNounById(new Random().nextLong(10) + 1);
        } while (memberRepository.existsByNickname(nickname));
        return NicknameResponseDto.of(nickname);
    }
}