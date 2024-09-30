package com.farmstory.service;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.ProductPageResponseDTO;
import com.farmstory.entity.Product;
import com.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService2 {

    @Value("${spring.servlet.multipart.location}")
    private String uploadedPath;

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
                    .prodCateNo(product.getProdCateNo() != null ? product.getProdCateNo().getProdCateName() : null) // 카테고리 이름 가져오기
                    // 이미지 파일 정보 추가
                    .p_sName1(product.getPDescImgFile() != null ? product.getPDescImgFile().getP_sName1() : "/images/404이미지없음.png")
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

    public ProductDTO selectProductById(int pNo) {
            Product product = productRepository.selectProductByPId(pNo);

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
                    .prodCateNo(product.getProdCateNo() != null ? product.getProdCateNo().getProdCateName() : null) // 카테고리 이름 가져오기
                    // 이미지 파일 정보 추가
                    .p_sName1(product.getPDescImgFile() != null ? product.getPDescImgFile().getP_sName1() : "/images/404이미지없음.png")
                    .p_sName2(product.getPDescImgFile() != null ? product.getPDescImgFile().getP_sName2() : null)
                    .p_sName3(product.getPDescImgFile() != null ? product.getPDescImgFile().getP_sName3() : null)
                    .build();
            return productDTO;
    }
}
