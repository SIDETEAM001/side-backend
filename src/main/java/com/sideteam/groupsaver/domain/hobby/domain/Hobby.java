package com.sideteam.groupsaver.domain.hobby.domain;

import com.sideteam.groupsaver.domain.category.domain.HobbyCategory;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hobby extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int memberCurrentNum;
    private int memberNumMax;
    @Enumerated(value = EnumType.STRING)
    private ClubType type;
    private String description;
    @Column(name = "main_image")
    private String mainImage;
    @Column(name = "is_status")
    private boolean isStatus = Boolean.TRUE;
    // 장단기 모임 기간의 설정칸이 없기에 보류
    //private String period;
    @Column(name = "start_club")
    private LocalDateTime startClub;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "activity_type")
    private ClubActivityType activityType;
    private String location;
    @ManyToOne
    @JoinColumn(name = "hobby_category_id")
    private HobbyCategory hobbyCategory;
    @OneToMany(mappedBy = "hobby")
    List<HobbyMember> hobbyMemberList = new ArrayList<>();

    private Hobby(String name, int memberNumMax, ClubType type, String description, String mainImage, LocalDateTime startClub, ClubActivityType activityType, String location) {
        this.name = name;
        this.memberNumMax = memberNumMax;
        this.type = type;
        this.description = description;
        this.mainImage = mainImage;
        this.startClub = startClub;
        this.activityType = activityType;
        this.location = location;
    }

    public static Hobby of(String name, int memberNumMax, ClubType type, String description, String mainImage, LocalDateTime startClub, ClubActivityType activityType, String location) {
        return new Hobby(name, memberNumMax, type, description, mainImage, startClub, activityType, location);
    }

    public void updateImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateMemberCurrent() {
        this.setMemberCurrentNum(this.getMemberCurrentNum() + 1);
    }

    public void updateCategory(HobbyCategory category) {
        this.setHobbyCategory(category);
    }

    public void addAHobbyMember(HobbyMember hobbyMember) {
        this.getHobbyMemberList().add(hobbyMember);
    }
}
