package com.sideteam.groupsaver.domain.member.repository;

import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.member.MemberErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    default Member findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(()
                -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND, "멤버 정보를 가져올 수 없습니다."));
    }

    boolean existsByNickname(String nickname);

    @Query("SELECT COUNT(*) FROM Member m WHERE LOWER(REPLACE(m.nickname, ' ', '')) = LOWER(REPLACE(:nickname, ' ', ''))")
    Long countByNickname(String nickname);

    LocalDateTime findCreateAtByEmail(String email);

    @Query("UPDATE Member m SET m.password = :password WHERE m.email = :email")
    void updatePasswordByEmail(String email, String password);

    String findEmailByPhoneNumber(String phone);

    @Query("UPDATE Member m SET m.password = :password WHERE m.phoneNumber = :phoneNumber")
    void updatePasswordByPhoneNumber(String password, String phoneNumber);
}
