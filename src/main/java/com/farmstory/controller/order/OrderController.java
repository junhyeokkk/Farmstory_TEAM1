package com.farmstory.controller.order;

import com.farmstory.dto.*;
import com.farmstory.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
public class OrderController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;


    @PostMapping("/orderlist")
    public String orderlist(CartDTO cartDTO, Model model){

        log.info("컨트롤러 들어옹ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ니/.?");

        log.info("카트dtod " + cartDTO);

        //uid set (시간 남으면 좋으코드로 develop)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (authentication != null && authentication.getPrincipal() instanceof UserDetails)
                ? ((UserDetails) authentication.getPrincipal()).getUsername()
                : null;

        log.info("현재 로그인한 사용자 uid: " + uid);

        cartDTO.setUid(uid);

        // 해당 product 들고오기
        ProductDTO productDTO = productService.selectProduct(cartDTO.getProdNo());

        // 해당 user 들고오기
        UserDTO userDTO = userService.selectUser(cartDTO.getUid());

        CateDTO cate= categoryService.selectCategory("market","order");

        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(cartDTO);

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setProductDTO(productDTO);
        responseOrderDTO.setUserDTO(userDTO);
        responseOrderDTO.setCartDTOList(cartDTOList);

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        model.addAttribute("responseOrderDTO", responseOrderDTO);

        log.info("responseOrderDTO"+ responseOrderDTO.toString());
        return "boardIndex";
    }
}
