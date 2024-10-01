package com.farmstory.entity;

import com.farmstory.dto.pDescImgFileDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pNo;
    private String pName;
    private int price;
    private int stock;
    private int point;
    private int discount;
    private int delivery;

    @CreationTimestamp
    private LocalDate rdate;
    private String pDesc;

    // 외래키 컬럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="prodCateNo")
    private prodCate prodCateNo;

    @OneToOne(mappedBy = "pNo", fetch = FetchType.LAZY)
    @ToString.Exclude
    private pDescImgFile pDescImgFile;

}
