package com.farmstory.repository.Impl;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.entity.*;
import com.farmstory.repository.OrderRepository;
import com.farmstory.repository.custom.OrderRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QOrder qOrder = QOrder.order;
    private QProduct qProduct = QProduct.product;
    private QUser qUser = QUser.user;
    private QOrderItem qOrderItem = QOrderItem.orderItem;

    @Override
    public Page<Tuple> selectOrderAllForList(PageRequestDTO requestDTO, Pageable pageable) {
        List<Tuple> orders = queryFactory
                .select(qOrder, qOrderItem, qUser.name, qProduct)
                .from(qOrder)
                .leftJoin(qOrderItem)
                .on(qOrder.orderNo.eq(qOrderItem.orderNo))
                .leftJoin(qUser)
                .on(qOrder.uid.eq(qUser.uid))
                .leftJoin(qProduct)
                .on(qOrderItem.pNo.eq(qProduct.pNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qOrder.orderNo.desc())
                .fetch();

        long total = queryFactory.select(qOrder.count())
                .from(qOrder)
                .fetchOne();
        log.info("total 나오니?> "  + total);
        log.info("Order 나오니?> "  + orders.toString());

        return new PageImpl<>(orders, pageable, total);
    }
}
