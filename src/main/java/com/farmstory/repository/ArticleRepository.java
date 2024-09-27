package com.farmstory.repository;


import com.farmstory.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository  extends JpaRepository<Article, Integer> {


    public List<Article> findByCate_CateNo(Integer cateNo);
    // 특정 cateNo에 맞는 Article을 페이징으로 가져오는 쿼리 메서드
    public Page<Article> findByCate_CateNo(int cateNo, Pageable pageable);

}
