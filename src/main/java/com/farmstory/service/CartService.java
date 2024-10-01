package com.farmstory.service;

import com.farmstory.dto.CartDTO;
import com.farmstory.dto.CartResponceDTO;
import com.farmstory.entity.Cart;
import com.farmstory.entity.Product;
import com.farmstory.repository.CartRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public boolean insertCart(CartDTO cartDTO){
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        log.info("Insert cart " + cartDTO);
        Product product = Product.builder()
                .pNo(cartDTO.getProdNo().getPNo())
                .build();
        cart.setProdNo(product);
        Cart savedCart = cartRepository.save(cart);
        if(savedCart.getCartNo() != 0){
            return true;
        }else{
            return false;
        }
    }
    public List<CartResponceDTO> selectALLWithUid(String uid){
        List<Tuple> cartList = cartRepository.findByCartNo(uid);
        List<CartResponceDTO> cartResponceDTOList = cartList.stream().map(tuple -> {
            Cart cart = tuple.get(0, Cart.class);
            return CartResponceDTO.builder()
                    .cartNo(cart.getCartNo())
                    .cartProdQty(cart.getCartProdQty())
                    .cartProdDate(cart.getCartProdDate())
                    .uid(uid)
                    .prodNo(cart.getProdNo().getPNo())
                    .pName(cart.getProdNo().getPName())
                    .discount(cart.getProdNo().getDiscount())
                    .point(cart.getProdNo().getPoint())
                    .price(cart.getProdNo().getPrice())
                    //임시@@@@@@@@@@@@
                    .prodCateName("과일")
                    .build();
        }).toList();

        return cartResponceDTOList;
    }

}
