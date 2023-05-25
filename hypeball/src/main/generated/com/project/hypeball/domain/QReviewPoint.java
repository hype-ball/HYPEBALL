package com.project.hypeball.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewPoint is a Querydsl query type for ReviewPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewPoint extends EntityPathBase<ReviewPoint> {

    private static final long serialVersionUID = -1290751403L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewPoint reviewPoint = new QReviewPoint("reviewPoint");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPoint point;

    public final QReview review;

    public QReviewPoint(String variable) {
        this(ReviewPoint.class, forVariable(variable), INITS);
    }

    public QReviewPoint(Path<? extends ReviewPoint> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewPoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewPoint(PathMetadata metadata, PathInits inits) {
        this(ReviewPoint.class, metadata, inits);
    }

    public QReviewPoint(Class<? extends ReviewPoint> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.point = inits.isInitialized("point") ? new QPoint(forProperty("point")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

