package com.project.hypeball.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttachedFile is a Querydsl query type for AttachedFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttachedFile extends EntityPathBase<AttachedFile> {

    private static final long serialVersionUID = -1798000733L;

    public static final QAttachedFile attachedFile = new QAttachedFile("attachedFile");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath storeFileName = createString("storeFileName");

    public final StringPath uploadFileName = createString("uploadFileName");

    public QAttachedFile(String variable) {
        super(AttachedFile.class, forVariable(variable));
    }

    public QAttachedFile(Path<? extends AttachedFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttachedFile(PathMetadata metadata) {
        super(AttachedFile.class, metadata);
    }

}

