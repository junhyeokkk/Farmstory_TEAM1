package com.farmstory.repository.Impl;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.entity.*;
import com.farmstory.repository.ProductRepository;
import com.farmstory.repository.custom.ProductRepositoryCustom;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
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
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QProduct qproduct = QProduct.product;
    private QpDescImgFile qpDescImgFile = QpDescImgFile.pDescImgFile;
    private QprodCate qprodCate = QprodCate.prodCate;



    @Override
    public Page<Product> selectProductAllForList(PageRequestDTO requestDTO, Pageable pageable) {
        List<Product> products = queryFactory.select(qproduct)
                .from(qproduct)
                .leftJoin(qproduct.prodCateNo ,qprodCate)
                .leftJoin(qproduct.pDescImgFile, qpDescImgFile)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qproduct.pNo.desc())
                .fetch();

        long total = queryFactory.select(qproduct.count())
                .from(qproduct)
                .fetchOne();

        return new PageImpl<Product>(products, pageable, total);
    }

    @Override
    public Page<Product> selectProductForSearch(PageRequestDTO requestDTO, Pageable pageable) {
        String cateNo = requestDTO.getType();

        BooleanExpression expression = null;

        if(cateNo.equals("1")){
            expression = qproduct.prodCateNo.prodCateNo.eq(1);
        }
        else if(cateNo.equals("2")){
            expression = qproduct.prodCateNo.prodCateNo.eq(2);
        }
        else if(cateNo.equals("3")){
            expression = qproduct.prodCateNo.prodCateNo.eq(3);
        }

        List<Product> products = queryFactory.select(qproduct)
                .from(qproduct)
                .leftJoin(qproduct.prodCateNo ,qprodCate)
                .leftJoin(qproduct.pDescImgFile, qpDescImgFile)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qproduct.pNo.desc())
                .fetch();

        long total = queryFactory.select(qproduct.count())
                .from(qproduct)
                .where(expression)
                .fetchOne();

        return new PageImpl<Product>(products, pageable, total);
    }

    @Override
    public Product selectProductByPId(Integer id) {

        Product product = queryFactory.select(qproduct)
                .from(qproduct)
                .leftJoin(qproduct.prodCateNo ,qprodCate)
                .leftJoin(qproduct.pDescImgFile, qpDescImgFile)
                .where(qproduct.pNo.eq(id))
                .fetchOne();
        ;
        return product;
    }
}
