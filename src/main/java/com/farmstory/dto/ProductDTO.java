package com.farmstory.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int pNo;
    private String pName;
    private int price;
    private int stock;
    private int point;
    private int discount;
    private int delivery;
    private String rdate;
    private String pDesc;

    // 업로드 전용
    private MultipartFile files1;
    private MultipartFile files2;
    private MultipartFile files3;

    private pDescImgFileDTO pDescImgFileDTO;

    // 이거 지우고 위에스면댐
    private String p_sName1;
    private String p_sName2;
    private String p_sName3;

    // 외래키 컬럼
    private int prodCateNo;
    private String prodCateName;

    public List<MultipartFile> getMultipartFiles(){
        return List.of(files1,files2,files3);
    }



}
