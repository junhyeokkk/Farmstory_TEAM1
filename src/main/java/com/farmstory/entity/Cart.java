package com.farmstory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue
    private int cartNo;                 // 장바구니 번호
    private int cartProdQty;            // 장바구니 상품 갯수
    private LocalDateTime cartProdDate; // 장바구니 날짜

    //외래키 목록
    private int uid;    // 유저 아이디
    private int prodNo; // 상품 번호
}