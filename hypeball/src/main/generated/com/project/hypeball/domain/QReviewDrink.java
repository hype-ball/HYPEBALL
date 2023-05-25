package com.project.hypeball.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewDrink is a Querydsl query type for ReviewDrink
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewDrink extends EntityPathBase<ReviewDrink> {

    private static final long serialVersionUID = -1301744291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewDrink reviewDrink = new QReviewDrink("reviewDrink");

    public final StringPath drink = createString("drink");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public QReviewDrink(String variable) {
        this(ReviewDrink.class, forVariable(variable), INITS);
    }

    public QReviewDrink(Path<? extends ReviewDrink> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewDrink(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewDrink(PathMetadata metadata, PathInits inits) {
        this(ReviewDrink.class, metadata, inits);
    }

    public QReviewDrink(Class<? extends ReviewDrink> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

