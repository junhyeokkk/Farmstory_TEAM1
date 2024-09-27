package com.farmstory.service;

import com.farmstory.dto.ProductDTO;
import com.farmstory.entity.Product;
import com.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService2 {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductDTO selectProduct(int pNo) {
        Optional<Product> product = productRepository.findById(pNo);

        if(product.isPresent()){
            ProductDTO productDTO = null;
            productDTO = modelMapper.map(product, ProductDTO.class);
            return productDTO;
        }
        return null;
    }

    public List<ProductDTO> getAllProductsWithCategories() {
        List<Product> products = productRepository.findAllOnList();

        return products.stream().map(product -> {
            ProductDTO productDTO = ProductDTO.builder()
                    .pNo(product.getPNo())
                    .pName(product.getPName())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .point(product.getPoint())
                    .discount(product.getDiscount())
                    .delivery(product.getDelivery())
                    .rdate(product.getRdate().toString()) // 날짜 포맷 조정 필요시 변환
                    .pDesc(product.getPDesc())
                    //.pList_fNo(null) // 이미지 파일 리스트는 null 또는 적절히 설정
                    .prodCateNo(product.getProdCateNo() != null ? product.getProdCateNo().getProdCateName() : null) // 카테고리 이름 가져오기
                    .build();
            return productDTO;
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> selectProductAll() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productList = products
                .stream()
                .map(Product -> modelMapper.map(Product, ProductDTO.class))
                .collect(Collectors.toList());
        return productList;
    }
}
