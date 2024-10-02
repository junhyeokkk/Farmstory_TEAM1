package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -186960001L;

    public static final QOrder order = new QOrder("order1");

    public final NumberPath<Integer> delivery = createNumber("delivery", Integer.class);

    public final NumberPath<Integer> oderNo = createNumber("oderNo", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final StringPath orderDesc = createString("orderDesc");

    public final StringPath payment = createString("payment");

    public final StringPath recAddr1 = createString("recAddr1");

    public final StringPath recAddr2 = createString("recAddr2");

    public final StringPath receipt = createString("receipt");

    public final StringPath recHp = createString("recHp");

    public final StringPath recZip = createString("recZip");

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final NumberPath<Integer> totalQty = createNumber("totalQty", Integer.class);

    public final StringPath uid = createString("uid");

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

