package com.farmstory.service;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.ProductPageResponseDTO;
import com.farmstory.entity.Product;
import com.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 상품 insert
    public int insertProduct(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        log.info(product);

        // 상품 저장 후 저장한 product PNo 반환
        Product savedproduct = productRepository.save(product);
        log.info("saved pno " + savedproduct);

        return savedproduct.getPNo();
    }

    public ProductPageResponseDTO getAllProductsWithAllInfo(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("pNo" , 10);


        Page<Product> products = null;

        if(pageRequestDTO.getType() == null){
            products = productRepository.selectProductAllForList(pageRequestDTO, pageable);
        }
        else{
            products = productRepository.selectProductForSearch(pageRequestDTO, pageable);
        }

        List<ProductDTO> productList = products.stream().map(product -> {
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
                    .prodCateName(product.getProdCateNo() != null ? product.getProdCateNo().getProdCateName() : null) // 카테고리 이름 가져오기
                    // 이미지 파일 정보 추가
                    .p_sName1(product.getPDescImgFile() != null ? product.getPDescImgFile().getP_sName1() : "404이미지없음.png")
                    .p_sName2(product.getPDescImgFile() != null ? product.getPDescImgFile().getP_sName2() : null)
                    .p_sName3(product.getPDescImgFile() != null ? product.getPDescImgFile().getP_sName3() : null)
                    .build();
            return productDTO;
        }).collect(Collectors.toList());

        int total = (int) products.getTotalElements();

        return ProductPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .productList(productList)
                .total(total)
                .build();
    }
}
