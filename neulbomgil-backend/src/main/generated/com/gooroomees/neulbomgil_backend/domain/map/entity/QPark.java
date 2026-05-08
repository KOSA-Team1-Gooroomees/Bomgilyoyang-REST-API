package com.gooroomees.neulbomgil_backend.domain.map.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPark is a Querydsl query type for Park
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPark extends EntityPathBase<Park> {

    private static final long serialVersionUID = -964401302L;

    public static final QPark park = new QPark("park");

    public final NumberPath<Double> area = createNumber("area", Double.class);

    public final StringPath category = createString("category");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath lotAddress = createString("lotAddress");

    public final StringPath name = createString("name");

    public QPark(String variable) {
        super(Park.class, forVariable(variable));
    }

    public QPark(Path<? extends Park> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPark(PathMetadata metadata) {
        super(Park.class, metadata);
    }

}

