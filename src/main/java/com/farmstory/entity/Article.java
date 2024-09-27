package com.farmstory.entity;

import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.CateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleNo;   //board no  autoincrement

    @ManyToOne
    @JoinColumn(name ="cateNo")
    private Cate cate;
    private String title;  //제목
    private String content;  //내용
    private String writer;  //작성자

    @CreationTimestamp
    private LocalDateTime date;   //작성일 now()
    private String regIp;   //작성자i

    //default 0 or null
    private String file;
    private String hit;
    private String comNo;


    public ArticleDTO toDTO(){
        return ArticleDTO.builder()
                .articleNo(articleNo)
                .cate(cate)
                .title(title)
                .content(content)
                .writer(writer)
                .date(String.valueOf(date))
                .regIp(regIp)
                .file(file)
                .hit(hit)
                .comNo(comNo)
                .build();
    }

}
