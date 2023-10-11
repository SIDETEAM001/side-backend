package com.sideteam.groupsaver.global.auth.userdetails;

import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;


    /**
     * 로그인을 위해 아이디로 가입한 멤버 찾기
     *
     * @param identity - 구분을 위한 고유 정보. Member ID
     */
    @Override
    public UserDetails loadUserByUsername(final String identity) throws UsernameNotFoundException {
        try {
            final Long memberId = Long.parseLong(identity);
            return loadMemberById(memberId);
        } catch (NumberFormatException exception) {
            throw new UsernameNotFoundException(identity);
        }
    }


    private CustomUserDetails loadMemberById(final Long memberId) {
        final var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException(memberId.toString()));
        return CustomUserDetails.of(member);
    }

}
