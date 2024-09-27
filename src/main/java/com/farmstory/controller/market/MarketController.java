package com.farmstory.controller.market;

import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.CateDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.service.CategoryService;
import com.farmstory.service.ProductService2;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
public class MarketController {

    private final CategoryService categoryService;
    private final ProductService2 productService;

    @GetMapping("/plist")
    public String plist(@RequestParam(value = "content", required = false) String content,
                        @RequestParam(value ="page", defaultValue = "0") int page,
                        Model model){
        CateDTO cate = categoryService.selectCategory("market","plist");
        int cateNo = cate.getCateNo();
        log.info("cate : "+cate);
        List<ArticleDTO> articles = null;

        List<ProductDTO> productList = productService.getAllProductsWithCategories();

        model.addAttribute("cate", cate);
        model.addAttribute("content", content);
        model.addAttribute("productList", productList);
        return "boardIndex";
    }


}
