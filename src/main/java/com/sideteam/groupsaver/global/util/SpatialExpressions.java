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

    public static BooleanExpression within(Point centerPoint, ComparablePath<Point> otherPoint, int radiusMeter) {
        Expression<Geometry> buffer = SpatialExpressions.buffer(centerPoint, radiusMeter);
        return SpatialExpressions.contains(buffer, otherPoint);
    }

    public static BooleanExpression within(double longitude, double latitude, int SRID, ComparablePath<Point> otherPoint, int radiusMeter) {
        Point point = SpatialExpressions.createPoint(longitude, latitude, SRID);
        return SpatialExpressions.within(point, otherPoint, radiusMeter);
    }

    public static BooleanExpression contains(Expression<Geometry> geometryExpression, ComparablePath<Point> point) {
        return Expressions.booleanTemplate("ST_Contains({0}, {1})", geometryExpression, point);
    }

    public static Expression<Geometry> buffer(Geometry geometry, Number radius) {
        return Expressions.template(Geometry.class, "ST_Buffer({0}, {1})", geometry, radius);
    }

    public static Point createPoint(double longitude, double latitude, int SRID) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), SRID);
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

}
