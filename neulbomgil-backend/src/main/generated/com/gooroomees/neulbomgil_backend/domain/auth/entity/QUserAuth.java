package com.gooroomees.neulbomgil_backend.domain.auth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserAuth is a Querydsl query type for UserAuth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAuth extends EntityPathBase<UserAuth> {

    private static final long serialVersionUID = -38215631L;

    public static final QUserAuth userAuth = new QUserAuth("userAuth");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserAuth(String variable) {
        super(UserAuth.class, forVariable(variable));
    }

    public QUserAuth(Path<? extends UserAuth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAuth(PathMetadata metadata) {
        super(UserAuth.class, metadata);
    }

}

