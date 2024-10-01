package com.farmstory.repository.custom;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.entity.Article;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {

    public Page<Tuple> selectArticleAllForList(PageRequestDTO pagerequestDTO, Pageable pageable, int cateNo);
    public Page<Tuple> selectArticleAllForListOnlyUid(PageRequestDTO pagerequestDTO, Pageable pageable, int cateNo);
    public Page<Tuple> selectArticleForSearch(PageRequestDTO pagerequestDTO, Pageable pageable,int cateNo);

    public List<Tuple> selectArticleById(int no);

    public List<Article> selectNotice(int cateNo);
}
