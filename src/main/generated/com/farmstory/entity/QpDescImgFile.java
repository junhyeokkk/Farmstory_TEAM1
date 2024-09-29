package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QpDescImgFile is a Querydsl query type for pDescImgFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QpDescImgFile extends EntityPathBase<pDescImgFile> {

    private static final long serialVersionUID = -466206707L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QpDescImgFile pDescImgFile = new QpDescImgFile("pDescImgFile");

    public final StringPath p_sName1 = createString("p_sName1");

    public final StringPath p_sName2 = createString("p_sName2");

    public final StringPath p_sName3 = createString("p_sName3");

    public final NumberPath<Integer> pfNo = createNumber("pfNo", Integer.class);

    public final QProduct pNo;

    public final StringPath rdate = createString("rdate");

    public QpDescImgFile(String variable) {
        this(pDescImgFile.class, forVariable(variable), INITS);
    }

    public QpDescImgFile(Path<? extends pDescImgFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QpDescImgFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QpDescImgFile(PathMetadata metadata, PathInits inits) {
        this(pDescImgFile.class, metadata, inits);
    }

    public QpDescImgFile(Class<? extends pDescImgFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pNo = inits.isInitialized("pNo") ? new QProduct(forProperty("pNo"), inits.get("pNo")) : null;
    }

}

