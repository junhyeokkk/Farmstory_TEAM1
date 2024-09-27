package com.farmstory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class pDescImgFileDTO {
    private int pfNo;
    private String p_sName1;
    private String p_sName2;
    private String p_sName3;
    private String rdate;

    // 외래키 컬럼
    private int pNo;

}
