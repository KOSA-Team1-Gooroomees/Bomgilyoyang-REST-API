package com.gooroomees.neulbomgil_backend.domain.auth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserMypage is a Querydsl query type for UserMypage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserMypage extends EntityPathBase<UserMypage> {

    private static final long serialVersionUID = -2018361724L;

    public static final QUserMypage userMypage = new QUserMypage("userMypage");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QUserMypage(String variable) {
        super(UserMypage.class, forVariable(variable));
    }

    public QUserMypage(Path<? extends UserMypage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMypage(PathMetadata metadata) {
        super(UserMypage.class, metadata);
    }

}

