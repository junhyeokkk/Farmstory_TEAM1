package com.farmstory.service;

import com.farmstory.dto.*;
import com.farmstory.entity.Article;
import com.farmstory.entity.CsArticle;
import com.farmstory.repository.ArticleRepository;
import com.farmstory.repository.CateRepository;
import com.farmstory.repository.CsArticleRepository;
import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class  ArticleService {


    private final ArticleRepository articleRepository;
    private final CateRepository cateRepository;
    private final ModelMapper modelMapper;
    private final CsArticleRepository csArticleRepository;


    public int insertArticle(ArticleDTO articleDTO, int cateNo, HttpServletRequest req) {
        log.info("article controller :"+articleDTO.toString());

        //첨부파일 객체 가져오기
        List<MultipartFile>  files = articleDTO.getFiles();
        int filesize = 0;
        if(files != null && !files.isEmpty()) {
            filesize =files.size();
        }


        articleDTO.setRegIp(req.getRemoteAddr());
        Article article = modelMapper.map(articleDTO, Article.class);
        article.setFile(filesize);
        Article savedArticle = articleRepository.save(article);

        return savedArticle.getArticleNo();
    }
    public ArticleDTO selectArticle(int articleNo) {
        Optional<Article> opt = articleRepository.findById(articleNo);

        if(opt.isPresent()) {
            Article article = opt.get();
            article.setHit(article.getHit() + 1);
            Article hitupdateArticle = articleRepository.save(article);

            return modelMapper.map(hitupdateArticle, ArticleDTO.class);
        }
        return null;
    }

    public List<ArticleDTO> selectCateArticle(int cateNo){
        List<Article> entitys = articleRepository.findByCateNo(cateNo);

        List<ArticleDTO> articles = entitys.stream().map(article -> article.toDTO()).toList();

        return articles;


    }
    public List<ArticleDTO> selectArticles() { return null;}
    public void updateArticle(Article article,int cateNo) {}
    public int deleteArticle(int articleNo) {
        boolean exists =  articleRepository.existsById(articleNo);
        int result=0;
        if(exists) {
            articleRepository.deleteById(articleNo);
            result = 1;
        }
        return result;
    }


    public CSPageResponseDTO selectCsArticleForUser(CSPageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable("csNo");
        Page<Tuple> pageCsArticle = null;

            pageCsArticle =csArticleRepository.selectCsForUser(requestDTO,pageable,requestDTO.getUid());

            List<CsArticleDTO> csArticleList= pageCsArticle.stream().map(tuple -> {
                CsArticle csArticle = tuple.get(0,CsArticle.class);
                String nick = tuple.get(1,String.class);
                csArticle.setNick(nick);
                return modelMapper.map(csArticle,CsArticleDTO.class);
            }).toList();

        int total = (int)pageCsArticle.getTotalElements();

        return CSPageResponseDTO.builder()
                .csArticleDTOS(csArticleList)
                .total(total)
                .cspageRequestDTO(requestDTO)
                .build();

    }


    public PageResponseDTO getAllArticles(PageRequestDTO pageRequestDTO,int cateNo) {

        Pageable pageable = pageRequestDTO.getPageable("articleNo",10);
        Page<Tuple> pageArticle =null;
        if(pageRequestDTO.getKeyword()==null) {

                pageArticle = articleRepository.selectArticleAllForList(pageRequestDTO, pageable, cateNo);


        }else{
            pageArticle = articleRepository.selectArticleForSearch(pageRequestDTO, pageable,cateNo);
        }

        List<ArticleDTO> articleList = pageArticle.stream().map(tuple ->{
                    Article article = tuple.get(0,Article.class);
                    String nick=tuple.get(1,String.class);
                    article.setNick(nick);
                    return modelMapper.map(article, ArticleDTO.class);
                }
        ).toList();


        int total = (int)pageArticle.getTotalElements();

        log.info("total : "+total);
        return PageResponseDTO.builder()
                .dtoList(articleList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();


    }


    public CSPageResponseDTO selectCSByAdmin(CSPageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("articleNo",10);
        Page<Tuple> pageArticle =null;
        if(pageRequestDTO.getKeyword()==null) {

            pageArticle = csArticleRepository.selectCsForAdmin(pageRequestDTO, pageable, 504);

        }else{
            pageArticle = csArticleRepository.selectCsForAdmin(pageRequestDTO, pageable,504);
        }

        List<CsArticleDTO> articleList = pageArticle.stream().map(tuple ->{
                    CsArticle article = tuple.get(0,CsArticle.class);
                    String nick=tuple.get(1,String.class);
                    article.setNick(nick);
                    return modelMapper.map(article, CsArticleDTO.class);
                }
        ).toList();


        int total = (int)pageArticle.getTotalElements();

        log.info("total : "+total);
        return CSPageResponseDTO.builder()
                .csArticleDTOS(articleList)
                .total(total)
                .cspageRequestDTO(pageRequestDTO)
                .build();


    }






}