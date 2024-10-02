package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oderNo; // 주문 번호

    private LocalDateTime orderDate;    //주문날짜
    private int totalPrice; // 주문 전체 금액
    private int totalQty;   //
    private String receipt;
    private int delivery;
    private String recHp;       // 주문 받는사람 전화번호
    private String recZip;      // 주문 받는 우편번호
    private String recAddr1;    // 주문 받는 주소1
    private String recAddr2;    // 주문 받는주소2
    private String payment;    // 결제 수단
    private String orderDesc; // 주문 부가 설명

    // 외래키 컬럼
    private String uid;
}
