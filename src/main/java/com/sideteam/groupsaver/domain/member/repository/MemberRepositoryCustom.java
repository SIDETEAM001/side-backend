package com.sideteam.groupsaver.domain.member.repository;

import com.sideteam.groupsaver.domain.member.dto.response.MyProfileResponse;

public interface MemberRepositoryCustom {

    MyProfileResponse findMyProfileByMemberId(Long memberId);

}
