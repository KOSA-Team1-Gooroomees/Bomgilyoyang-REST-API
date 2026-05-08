package com.gooroomees.neulbomgil_backend.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 2092040156L;

    public static final QBoard board = new QBoard("board");

    public final NumberPath<Integer> boardid = createNumber("boardid", Integer.class);

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final StringPath userid = createString("userid");

    public final DateTimePath<java.time.LocalDateTime> writedate = createDateTime("writedate", java.time.LocalDateTime.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

