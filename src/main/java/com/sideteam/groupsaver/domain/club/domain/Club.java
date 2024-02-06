package com.sideteam.groupsaver.domain.club.domain;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.dto.request.ClubRequest;
import com.sideteam.groupsaver.domain.club.dto.request.ClubRequestDto;
import com.sideteam.groupsaver.domain.common.BaseEntity;
import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.report.domain.ReportClub;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "club",
        indexes = {
                @Index(name = "idx_random_id", columnList = "randomId")
        }
)
@Entity
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

    @Column(nullable = false)
    private Integer randomId;

    private Integer memberMaxNumber;

    @Formula("(SELECT COUNT(*) FROM club_member cm WHERE cm.club_id = id)")
    private int memberCurrentNumber = 0;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    private final List<ClubMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    private final List<ReportClub> reports = new ArrayList<>();


    public static Club of(ClubRequestDto clubRequestDto, Location location) {
        ClubCategoryMajor categoryMajor = clubRequestDto.categoryMajor();
        return Club.builder()
                .name(clubRequestDto.name())
                .description(clubRequestDto.description())
                .memberMaxNumber(clubRequestDto.memberMaxNumber())
                .startAt(clubRequestDto.startAt())
                .mainImage(clubRequestDto.mainImage())
                .category(categoryMajor.getCategory())
                .categoryMajor(categoryMajor)
                .categorySub(clubRequestDto.categorySub())
                .type(clubRequestDto.type())
                .activityType(clubRequestDto.activityType())
                .location(location)
                .locationDetail(clubRequestDto.locationDetail())
                .randomId((int) (Math.random() * 100_000_000))
                .build();
    }


    public void update(ClubRequestDto clubRequestDto) {
        this.name = requireNonNullElse(clubRequestDto.name(), this.name);
        this.memberMaxNumber = requireNonNullElse(clubRequestDto.memberMaxNumber(), this.memberMaxNumber);
        this.type = requireNonNullElse(clubRequestDto.type(), this.type);
        this.description = requireNonNullElse(clubRequestDto.description(), this.description);
        this.mainImage = requireNonNullElse(clubRequestDto.mainImage(), this.mainImage);
        this.startAt = requireNonNullElse(clubRequestDto.startAt(), this.startAt);
        this.category = requireNonNullElse(clubRequestDto.categoryMajor().getCategory(), this.category);
        this.categoryMajor = requireNonNullElse(clubRequestDto.categoryMajor(), this.categoryMajor);
        this.categorySub = requireNonNullElse(clubRequestDto.categorySub(), this.categorySub);
        this.activityType = requireNonNullElse(clubRequestDto.activityType(), this.activityType);
    }


    public void updateLocation(Location location) {
        this.location = requireNonNullElse(location, this.location);
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void addReport(ReportClub reportClub) {
        this.reports.add(reportClub);
    }
}
