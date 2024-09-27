package com.farmstory.controller.admin;

import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.pDescImgFileDTO;
import com.farmstory.service.ProductService;
import com.farmstory.service.pDescImgFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@RestController
public class ProductController {

    private final pDescImgFileService descImgFileService;
    private final ProductService productService;

    @PostMapping("/admin/register")
    public String register(ProductDTO productDTO) {

        log.info("register product : "  + productDTO.toString());
        log.info("register product : "  + productDTO.getProdCateNo());

        // 파일 업로드
        pDescImgFileDTO uploadFile = descImgFileService.uploadpDescImgFile(productDTO);
        log.info("uploadeFIle : " + uploadFile.toString());

        // 상품 저장
        int PNo = productService.insertProduct(productDTO);

        log.info("nono" + PNo);

        // 이미지 저장 (이미지 파일 1개기 때문에 그냥 바로 저장 처리)
        uploadFile.setPNo(PNo);
        descImgFileService.insertpDescImgFile(uploadFile);


        log.info("uploadFile" + uploadFile);

        return "/admin/main";
    }

}
