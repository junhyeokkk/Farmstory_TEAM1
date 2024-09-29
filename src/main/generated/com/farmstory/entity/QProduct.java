package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1617704160L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> delivery = createNumber("delivery", Integer.class);

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final StringPath pDesc = createString("pDesc");

    public final QpDescImgFile pDescImgFile;

    public final StringPath pName = createString("pName");

    public final NumberPath<Integer> pNo = createNumber("pNo", Integer.class);

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QprodCate prodCateNo;

    public final DatePath<java.time.LocalDate> rdate = createDate("rdate", java.time.LocalDate.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pDescImgFile = inits.isInitialized("pDescImgFile") ? new QpDescImgFile(forProperty("pDescImgFile"), inits.get("pDescImgFile")) : null;
        this.prodCateNo = inits.isInitialized("prodCateNo") ? new QprodCate(forProperty("prodCateNo")) : null;
    }

}

