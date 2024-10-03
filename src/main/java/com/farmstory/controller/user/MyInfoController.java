package com.farmstory.controller.user;

import com.farmstory.dto.CartDTO;
import com.farmstory.dto.CateDTO;
import com.farmstory.service.CartService;
import com.farmstory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MyInfoController {
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping("/category/myInfo/cart")
    public String cart(@RequestParam(value = "content", required = false) String content
            , Model model){

        CateDTO cate = categoryService.selectCategory("myInfo","cart");
        log.info("cate : "+cate);

        List<CartDTO> cartList = cartService.findCartWithUid();

        model.addAttribute("cate", cate);
        model.addAttribute("content", content);

        model.addAttribute("cartList", cartList);

        return "boardIndex";
    }
}
