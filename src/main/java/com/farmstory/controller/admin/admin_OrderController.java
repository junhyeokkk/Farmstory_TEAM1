package com.farmstory.controller.admin;

import com.farmstory.dto.CateDTO;
import com.farmstory.dto.OrderPageResponseDTO;
import com.farmstory.dto.PageRequestDTO;
import com.farmstory.dto.ProductPageResponseDTO;
import com.farmstory.service.CategoryService;
import com.farmstory.service.OrderService;
import com.farmstory.service.ProductService;
import com.farmstory.service.pDescImgFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Log4j2
@Controller
public class admin_OrderController {

    private final CategoryService categoryService;
    private final OrderService orderService;

    @GetMapping("/admin/a_order")
    public String product (Model model, PageRequestDTO pageRequestDTO){

        OrderPageResponseDTO orderPageResponseDTO = orderService.getAllOrder(pageRequestDTO);

        log.info("adfasfasfa"  + orderPageResponseDTO);

        CateDTO cate= categoryService.selectCategory("admin","a_order");

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        model.addAttribute("orderPageResponseDTO", orderPageResponseDTO);

        return "admin_index";
    }
}
