package com.sideteam.groupsaver.domain.member.repository;

import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.domain.MemberActive;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.member.MemberErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    default Member findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(()
                -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND, "멤버 정보를 가져올 수 없습니다."));
    }

    boolean existsByNickname(String nickname);

    @Query("SELECT COUNT(*) FROM Member m WHERE REPLACE(m.nickname, ' ', '') = REPLACE(:nickname, ' ', '')")
    int countByNickname(String nickname);

    @Query("SELECT m.createAt FROM Member m WHERE m.email = :email")
    Instant findCreateAtByEmail(String email);

    @Query("SELECT m.email FROM Member m WHERE m.phoneNumber = :phone")
    String findEmailByPhoneNumber(String phone);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByIdAndActiveStatus(Long id, MemberActive activeStatus);

    @Query("SELECT m FROM Member m WHERE m.activeStatus = ACTIVE")
    List<Member> findAllByActiveStatusIsActive();
}
