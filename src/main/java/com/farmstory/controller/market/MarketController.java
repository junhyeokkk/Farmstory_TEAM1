package com.farmstory.controller.market;

import com.farmstory.dto.*;
import com.farmstory.service.CategoryService;
import com.farmstory.service.ProductService2;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
public class MarketController {

    private final CategoryService categoryService;
    private final ProductService2 productService;
    @Value("${spring.servlet.multipart.location}")
    private String uploadedPath;

    @GetMapping("/plist")
    public String plist(@RequestParam(value = "content", required = false) String content,
                        @RequestParam(value ="page", defaultValue = "0") int page,
                        Model model, PageRequestDTO pageRequestDTO){
        CateDTO cate = categoryService.selectCategory("market","plist");
        int cateNo = cate.getCateNo();
        log.info("cate : "+cate);
        List<ArticleDTO> articles = null;

        ProductPageResponseDTO productPageResponseDTO = productService.getAllProductsWithAllInfo(pageRequestDTO);

        model.addAttribute("cate", cate);
        model.addAttribute("content", content);
        model.addAttribute("ProductPageResponseDTO", productPageResponseDTO);
        return "boardIndex";
    }

    @GetMapping("/pview/{pNo}")
    public String plist(@RequestParam(value = "content", required = false) String content
                        ,@PathVariable int pNo
                        ,Model model){
        CateDTO cate = categoryService.selectCategory("market","pview");
        log.info("cate : "+cate);

        ProductDTO productDTO = productService.selectProductById(pNo);
        log.info("productDTO : "+productDTO);
        model.addAttribute("cate", cate);
        model.addAttribute("content", content);
        model.addAttribute("productDTO", productDTO);
        return "boardIndex";
    }


}
