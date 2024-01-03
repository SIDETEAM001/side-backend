package com.sideteam.groupsaver.domain.category.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.hobby.domain.Hobby;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "hobby_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HobbyCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    private HobbySub sub;
    @OneToMany(mappedBy = "hobbyCategory")
    private List<Hobby> hobbyList = new ArrayList<>();

    public HobbyCategory(HobbySub sub) {
        this.sub = sub;
    }

    public static HobbyCategory of(Hobby hobby, HobbySub sub) {
        HobbyCategory entity = new HobbyCategory(sub);
        entity.hobbyList.add(hobby);
        return entity;
    }

    public void addAHobby(Hobby hobby) {
        this.getHobbyList().add(hobby);
    }
}