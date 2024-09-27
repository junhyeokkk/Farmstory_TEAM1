package com.farmstory.repository;

import com.farmstory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p JOIN p.prodCateNo c") // Product와 prodCate를 조인
    List<Product> findAllOnList();
}
