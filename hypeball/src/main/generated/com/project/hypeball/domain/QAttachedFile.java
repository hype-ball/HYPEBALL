package com.project.hypeball.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachedFile is a Querydsl query type for AttachedFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttachedFile extends EntityPathBase<AttachedFile> {

    private static final long serialVersionUID = -1798000733L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachedFile attachedFile = new QAttachedFile("attachedFile");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public final StringPath storeFileName = createString("storeFileName");

    public final StringPath uploadFileName = createString("uploadFileName");

    public QAttachedFile(String variable) {
        this(AttachedFile.class, forVariable(variable), INITS);
    }

    public QAttachedFile(Path<? extends AttachedFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachedFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachedFile(PathMetadata metadata, PathInits inits) {
        this(AttachedFile.class, metadata, inits);
    }

    public QAttachedFile(Class<? extends AttachedFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

