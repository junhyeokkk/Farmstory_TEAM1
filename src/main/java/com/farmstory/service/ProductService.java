package com.farmstory.service;

import com.farmstory.dto.ProductDTO;
import com.farmstory.entity.Product;
import com.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public int insertProduct(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        log.info(product);

        // 상품 저장 후 저장한 product PNo 반환
        Product savedproduct = productRepository.save(product);

        return savedproduct.getPNo();
    }
}
