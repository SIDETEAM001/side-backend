package com.sideteam.groupsaver.domain.club.domain;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.dto.request.ClubRequest;
import com.sideteam.groupsaver.domain.common.BaseEntity;
import com.sideteam.groupsaver.domain.location.domain.Location;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Builder
@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ClubType type;

    @Column(name = "main_image")
    private String mainImage;

    @Builder.Default
    @Column(name = "is_active")
    private boolean isActive = true;
    // 장단기 모임 기간의 설정칸이 없기에 보류
    //private String period;
    private LocalDateTime startAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private ClubActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;
    private String locationDetail;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ClubCategory category;

    @Enumerated(value = EnumType.STRING)
    private ClubCategoryMajor categoryMajor;

    @Enumerated(value = EnumType.STRING)
    private ClubCategorySub categorySub;

    private Integer memberMaxNumber;

    @Formula("(SELECT COUNT(*) FROM club_member cm WHERE cm.club_id = id)")
    private int memberCurrentNumber = 0;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    private final List<ClubMember> members = new ArrayList<>();


    public static Club of(ClubRequest clubRequest, Location location) {
        ClubCategoryMajor categoryMajor = clubRequest.getMajor();
        return Club.builder()
                .name(clubRequest.name())
                .description(clubRequest.description())
                .memberMaxNumber(clubRequest.memberMaxNumber())
                .startAt(clubRequest.startAt())
                .mainImage(clubRequest.mainImage())
                .category(categoryMajor.getCategory())
                .categoryMajor(categoryMajor)
                .categorySub(clubRequest.categorySub())
                .type(clubRequest.type())
                .activityType(clubRequest.activityType())
                .location(location)
                .locationDetail(clubRequest.locationDetail())
                .build();
    }


    public void update(ClubRequest clubRequest) {
        this.name = requireNonNullElse(clubRequest.name(), this.name);
        this.memberMaxNumber = requireNonNullElse(clubRequest.memberMaxNumber(), this.memberMaxNumber);
        this.type = requireNonNullElse(clubRequest.type(), this.type);
        this.description = requireNonNullElse(clubRequest.description(), this.description);
        this.mainImage = requireNonNullElse(clubRequest.mainImage(), this.mainImage);
        this.startAt = requireNonNullElse(clubRequest.startAt(), this.startAt);
        this.category = requireNonNullElse(clubRequest.getMajor().getCategory(), this.category);
        this.categoryMajor = requireNonNullElse(clubRequest.categoryMajor(), this.categoryMajor);
        this.categorySub = requireNonNullElse(clubRequest.categorySub(), this.categorySub);
        this.activityType = requireNonNullElse(clubRequest.activityType(), this.activityType);
    }


    public void updateLocation(Location location) {
        this.location = requireNonNullElse(location, this.location);
    }

}
