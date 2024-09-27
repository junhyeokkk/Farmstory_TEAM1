package com.farmstory.dto;

import com.farmstory.entity.Article;
import com.farmstory.entity.Cate;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {
    private int articleNo;   //board no  autoincrement
    private Cate cate; // croptalk={story,garden,re}, event = {event}, community={notice,today,cook,cs,faq}
    private String title;  //제목
    private String content;  //내용
    private String writer;  //작성자
    private String date;   //작성일 now()
    private String regIp;   //작성자ip

    //default 0 or null
    private String file;
    private String hit;
    private String comNo;

    public Article toEntity(){
        return Article.builder()
                .articleNo(articleNo)
                .cate(cate)
                .title(title)
                .content(content)
                .writer(writer)
                .regIp(regIp)
                .file(file)
                .hit(hit)
                .comNo(comNo)
                .build();
    }


}
