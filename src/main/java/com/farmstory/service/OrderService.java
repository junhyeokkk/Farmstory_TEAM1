package com.farmstory.service;

import com.farmstory.dto.*;
import com.farmstory.entity.Order;
import com.farmstory.entity.OrderItem;
import com.farmstory.entity.Product;
import com.farmstory.entity.prodCate;
import com.farmstory.repository.OrderItemRepository;
import com.farmstory.repository.OrderRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final OrderItemRepository orderItemRepository;

    public OrderDTO insertOrder(OrderDTO orderDTO) {

        Order order = modelMapper.map(orderDTO, Order.class);
        Order savedOrder = orderRepository.save(order);

        OrderDTO savedorderDTO = modelMapper.map(savedOrder, OrderDTO.class);

        return savedorderDTO;
    }

    public OrderItemDTO insertOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        OrderItemDTO savedorderItemDTO = modelMapper.map(savedOrderItem, OrderItemDTO.class);

        return savedorderItemDTO;
    }

    public OrderPageResponseDTO getAllOrder(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("orderNo" , 10);

        Page<Tuple> orders = null;
        log.info("너는 되니? " + orders);


        orders = orderRepository.selectOrderAllForList(pageRequestDTO, pageable);
        log.info("너까지 되니? " + orders);


        List<OrderDTO> orderList = orders.getContent().stream().map(tuple -> {
            Order order = tuple.get(0, Order.class);
            OrderItem orderItem = (tuple.get(1, OrderItem.class));
            String name = (tuple.get(2, String.class));
            Product product = tuple.get(3, Product.class);

            order.setPName(product.getPName());
            order.setPrice(product.getPrice());
            order.setItemQty(orderItem.getItemQty());
            order.setName(name);

            return modelMapper.map(order, OrderDTO.class);
        }).toList();

        log.info("매핑 되니? " + orderList);

        int total = (int) orders.getTotalElements();

        return OrderPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .orderDTOList(orderList)
                .total(total)
                .build();
    }

}
