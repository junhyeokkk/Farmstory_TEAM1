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
@Table(name="orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemNo;            // 주문번호
    private LocalDateTime orderDate;    // 주문날짜
    private int itemPrice;
    private int itemQty;

    // 할인 되는 금액
    private int discount;

    // 외래키 목록
    private int orderNo;    //주문 번호
    private int pNo;        //상품 번호
}
