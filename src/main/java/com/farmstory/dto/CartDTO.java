package com.farmstory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private int cartNo;                 // 장바구니 번호
    private int cartProdQty;            // 장바구니 상품 갯수
    private LocalDateTime cartProdDate; // 장바구니 날짜

    //외래키 목록
    private int uid;    // 유저 아이디
    private int prodNo; // 상품 번호
}
