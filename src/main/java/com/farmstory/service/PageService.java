package com.farmstory.service;

import com.farmstory.repository.ArticleRepository;
import com.farmstory.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final ArticleRepository articleRepository;



    public Page<Article> getArticles(int cateNo, Pageable pageable) {
        return  articleRepository.findByCate_CateNo(cateNo,pageable);
    }
}
