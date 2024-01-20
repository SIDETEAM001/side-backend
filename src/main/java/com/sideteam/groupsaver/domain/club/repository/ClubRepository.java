package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.location.service.LocationUtils;
import com.sideteam.groupsaver.global.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_NOT_FOUND;

public interface ClubRepository extends JpaRepository<Club, Long> {
    @Modifying
    @Query("UPDATE Club c SET c.isActive = false WHERE c.id = :clubId")
    void deactivateClub(@Param("clubId") Long clubId);

    @EntityGraph(attributePaths = {"location"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<Club> findByNameContaining(String name, Pageable pageable);


    @EntityGraph(attributePaths = {"location"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT c " +
            "FROM Club c JOIN Location lo ON c.location.id = lo.id " +
            "WHERE FUNCTION('ST_Contains', FUNCTION('ST_Buffer', FUNCTION('ST_GeomFromText', " +
            "CONCAT('POINT(', :longitude, ' ', :latitude, ')'), " + LocationUtils.SRID + "), :radius), lo.locationCoordinate.coord) = true " +
            "ORDER BY FUNCTION('ST_Distance',  FUNCTION('ST_GeomFromText', " +
            "CONCAT('POINT(', :longitude, ' ', :latitude, ')'), " + LocationUtils.SRID + "), c.location.locationCoordinate.coord) ")
    Slice<Club> findByLocation(
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude,
            @Param("radius") Integer radiusMeter,
            Pageable pageable);

    default Club findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new BusinessException(CLUB_NOT_FOUND, "잘못된 모임 아이디 : " + id));
    }

    Slice<Club> findByCategorySubAndType(ClubCategorySub categorySub, ClubType type, Pageable pageable);

    Slice<Club> findByCategorySub(ClubCategorySub categorySub, Pageable pageable);

    Slice<Club> findByCategoryMajorAndType(ClubCategoryMajor categoryMajor, ClubType type, Pageable pageable);

    Slice<Club> findByCategoryMajor(ClubCategoryMajor categoryMajor, Pageable pageable);

    Slice<Club> findByCategoryAndType(ClubCategory category, ClubType type, Pageable pageable);

    Slice<Club> findByCategory(ClubCategory category, Pageable pageable);

}
