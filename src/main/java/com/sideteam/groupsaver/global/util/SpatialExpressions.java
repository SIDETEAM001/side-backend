package com.sideteam.groupsaver.global.util;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpatialExpressions {

    public static BooleanExpression contains(Expression<Geometry> geometryExpression, ComparablePath<Point> point) {
        return Expressions.booleanTemplate("ST_Contains({0}, {1})", geometryExpression, point);
    }

    public static Expression<Geometry> buffer(Geometry geometry, Number radius) {
        return Expressions.template(Geometry.class, "ST_Buffer({0}, {1})", geometry, radius);
    }

    public static Point createPoint(Double longitude, Double latitude, int SRID) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), SRID);
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

}
