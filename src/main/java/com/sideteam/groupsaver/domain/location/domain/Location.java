package com.sideteam.groupsaver.domain.location.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.location.converter.Region1TypeAttributeConverter;
import com.sideteam.groupsaver.domain.location.dto.LocationData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLInsert;

import static org.springframework.util.StringUtils.hasText;

@Getter
@ToString
@SQLInsert(sql =
        "INSERT INTO `location` (create_at, coord, region1, region2, region3, region4, update_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "create_at = VALUES(create_at), coord = VALUES(coord), " +
                "region1 = VALUES(region1), region2 = VALUES(region2), region3 = VALUES(region3), region4 = VALUES(region4), " +
                "update_at = VALUES(update_at)"
)
@Entity
@Table(name = "location",
        indexes = {
                @Index(name = "idx_point", columnList = "coord")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_name", columnNames = {"region1", "region2", "region3", "region4"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private LocationCoordinate locationCoordinate;

    @Convert(converter = Region1TypeAttributeConverter.class)
    @Column(nullable = false, length = 10)
    private Region1Type region1; // 광역시도

    @Column(nullable = false, length = 30)
    private String region2; // 구군

    @Column(nullable = false, length = 30)
    @ColumnDefault("''")
    private String region3; // 동읍면

    @Column(nullable = false, length = 30)
    @ColumnDefault("''")
    private String region4; // 리


    public String getName() {
        return region1.getName() + format(region2) + format(region3) + format(region4);
    }


    public static Location of(LocationData locationData) {
        return Location.of(
                locationData.longitude(), locationData.latitude(),
                locationData.region1(), locationData.region2(), locationData.region3(), locationData.region4()
        );
    }


    public static Location of(
            Double longitude, Double latitude,
            Region1Type region1, String region2, String region3, String region4) {
        return new Location(
                LocationCoordinate.of(longitude, latitude),
                region1, region2, region3, region4);
    }


    private Location(
            LocationCoordinate locationCoordinate,
            Region1Type region1, String region2, String region3, String region4) {
        this.locationCoordinate = locationCoordinate;
        this.region1 = region1;
        this.region2 = region2;
        this.region3 = region3;
        this.region4 = region4;
    }

    private static String format(String text) {
        return hasText(text) ? " " + text : "";
    }

}
