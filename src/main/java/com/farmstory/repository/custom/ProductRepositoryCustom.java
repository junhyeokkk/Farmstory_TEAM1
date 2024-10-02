package com.farmstory.repository.custom;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.entity.Product;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    public Page<Tuple> selectProductAllForList(PageRequestDTO requestDTO, Pageable pageable);
    public Page<Product> selectProductForSearch(PageRequestDTO requestDTO, Pageable pageable);
    public Product selectProductByPId(Integer id);
}
