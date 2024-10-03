package com.farmstory.controller.order;

import com.farmstory.dto.*;
import com.farmstory.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final OrderService orderService;

    @PostMapping("/addOrder")
    public String addOrder(OrderDTO orderDTO, Model model) {

        orderService.insertOrder(orderDTO);

        log.info(orderDTO.toString());
        return null;
    }

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
        ProductDTO productDTO = productService.selectProductById(cartDTO.getProdNo());
        cartDTO.setProductDTO(productDTO);

        // 해당 user 들고오기
        UserDTO userDTO = userService.selectUser(cartDTO.getUid());

        CateDTO cate= categoryService.selectCategory("market","order");

        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(cartDTO);

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setUserDTO(userDTO);
        responseOrderDTO.setCartDTOList(cartDTOList);

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        model.addAttribute("responseOrderDTO", responseOrderDTO);

        log.info("responseOrderDTO"+ responseOrderDTO.toString());
        return "boardIndex";
    }

    @PostMapping("/CartToOrder")
    public String processSelectedCartItems(@RequestParam("selectedItems") String selectedItems, Model model) throws JsonProcessingException {
        // JSON 문자열을 List<Integer>로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> cartNos = objectMapper.readValue(selectedItems, new TypeReference<List<Integer>>() {});
        log.info("cartNos : "+cartNos);
        // cartNo별로 처리 로직 수행
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (Integer cartNo : cartNos) {
            log.info("cartNo : "+cartNo);
            CartDTO cartDTO = cartService.selectCartByCartNo(cartNo);
            ProductDTO productDTO = productService.selectProductById(cartDTO.getProdNo());
            cartDTO.setProductDTO(productDTO);
            cartDTOList.add(cartDTO);
        }
        // 해당 user 들고오기
        UserDTO userDTO = userService.selectUser(cartDTOList.get(0).getUid());

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setUserDTO(userDTO);
        responseOrderDTO.setCartDTOList(cartDTOList);

        log.info("!!!!!!!!!!!!!!!cartDTOList : "+cartDTOList);
        CateDTO cate= categoryService.selectCategory("market","order");

        model.addAttribute("cate", cate);
        model.addAttribute("responseOrderDTO", responseOrderDTO);
        log.info("responseOrderDTO"+ responseOrderDTO.toString());
        // 처리 후 페이지 리다이렉트
        return "boardIndex";
    }

}
