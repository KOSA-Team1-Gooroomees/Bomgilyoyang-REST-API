package com.gooroomees.neulbomgil_backend.domain.map.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFacility is a Querydsl query type for Facility
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFacility extends EntityPathBase<Facility> {

    private static final long serialVersionUID = 1837150755L;

    public static final QFacility facility = new QFacility("facility");

    public final NumberPath<Integer> capacityCnt = createNumber("capacityCnt", Integer.class);

    public final StringPath categoryName = createString("categoryName");

    public final NumberPath<Integer> currentCnt = createNumber("currentCnt", Integer.class);

    public final StringPath facilityImage = createString("facilityImage");

    public final StringPath facilityName = createString("facilityName");

    public final NumberPath<Integer> facilityScore = createNumber("facilityScore", Integer.class);

    public final StringPath facilityTel = createString("facilityTel");

    public final StringPath id = createString("id");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath newAddress = createString("newAddress");

    public final StringPath oldAddress = createString("oldAddress");

    public QFacility(String variable) {
        super(Facility.class, forVariable(variable));
    }

    public QFacility(Path<? extends Facility> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFacility(PathMetadata metadata) {
        super(Facility.class, metadata);
    }

}

