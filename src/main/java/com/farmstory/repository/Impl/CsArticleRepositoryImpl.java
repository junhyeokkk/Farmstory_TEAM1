package com.farmstory.repository.Impl;

import com.farmstory.dto.CSPageRequestDTO;
import com.farmstory.entity.QCsArticle;
import com.farmstory.entity.QUser;
import com.farmstory.repository.custom.CsArticleRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CsArticleRepositoryImpl implements CsArticleRepositoryCustom  {

     private final JPAQueryFactory queryFactory;
     private final QCsArticle qcsArticle = QCsArticle.csArticle;
     private final QUser quser = QUser.user;




        @Override
        public Page<Tuple> selectCsForAdmin(CSPageRequestDTO cspagerequestDTO, Pageable pageable, int cateNo) {

            List<Tuple> content = queryFactory
                    .select(qcsArticle,quser.nick)
                    .from(qcsArticle)
                    .join(quser)
                    .on(qcsArticle.writer.eq(quser.uid))
                    .where(qcsArticle.isCompleted.eq(false))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(qcsArticle.createdate.asc(), qcsArticle.updateDate.asc())
                    .fetch();

            Long total = queryFactory
                    .select(qcsArticle.count())
                    .from(qcsArticle)
                    .where(qcsArticle.isCompleted.eq(false))
                    .fetchOne();

            return  new PageImpl<Tuple>(content,pageable,total);
    }

    @Override
    public Page<Tuple> selectCsForUser(CSPageRequestDTO cspagerequestDTO, Pageable pageable, String uid) {
        List<Tuple> content = queryFactory
                .select(qcsArticle,quser.nick)
                .from(qcsArticle)
                .join(quser)
                .on(qcsArticle.writer.eq(quser.uid))
                .where(qcsArticle.writer.eq(uid))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qcsArticle.createdate.asc(), qcsArticle.updateDate.asc())
                .fetch();

        Long total = queryFactory
                .select(qcsArticle.count())
                .from(qcsArticle)
                .join(quser)
                .on(qcsArticle.writer.eq(quser.uid))
                .where(qcsArticle.writer.eq(uid))
                .fetchOne();


        return new PageImpl<Tuple>(content,pageable,total);
    }
}
