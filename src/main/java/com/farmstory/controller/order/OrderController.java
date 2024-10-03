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
    private final OrderService orderService;

    @PostMapping("/addOrder")
    public String addOrder(OrderDTO orderDTO, Model model) {
/*
        OrderDTO savedOrder = orderService.insertOrder(orderDTO);

        if(savedOrder != null) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setOrderNo(savedOrder.getOrderNo());
        }
  */
      log.info("afdssssssssssssssssssss" + orderDTO.toString());

        return null;
    }

    @PostMapping("/orderlist")
    public String orderlist(CartDTO cartDTO ,Model model){

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

        // 계산 먼저 처리하고 order에서 그대로 쓰기
        OrderSummaryDTO orderSummary = new OrderSummaryDTO();

        int totalQty = 0;
        int totalPrice = 0;
        int totalDiscount = 0;
        int totalDelivery = 0;
        int totalPoint = 0;

        for (CartDTO cart : cartDTOList) {
            totalQty += cart.getCartProdQty();
            totalPrice += cart.getTotalprice();
            totalDelivery += cart.getCart_delivery();
            totalPoint += cart.getProductDTO().getPoint() * cart.getCartProdQty();

            // 할인 금액 계산
            int discount = (cart.getProductDTO().getPrice() * cart.getCartProdQty()) - cartDTO.getTotalprice();
            totalDiscount += discount;
        }

        // 결과 DTO에 값 설정
        orderSummary.setTotalQty(totalQty);
        orderSummary.setTotalPrice(totalPrice);
        orderSummary.setTotalDiscount(totalDiscount);
        orderSummary.setTotalDelivery(totalDelivery);
        orderSummary.setTotalPoint(totalPoint);

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setUserDTO(userDTO);
        responseOrderDTO.setCartDTOList(cartDTOList);

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        model.addAttribute("responseOrderDTO", responseOrderDTO);
        model.addAttribute("orderSummary", orderSummary);

        log.info("responseOrderDTO"+ responseOrderDTO.toString());
        return "boardIndex";
    }
}
