package com.farmstory.service;

import com.farmstory.dto.OrderDTO;
import com.farmstory.entity.Order;
import com.farmstory.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
/*
    public OrderDTO insertOrder(OrderDTO orderDTO) {

        Order order = modelMapper.map(orderDTO, Order.class);
        Order savedOrder = orderRepository.save(order);

        OrderDTO savedorderDTO = modelMapper.map(savedOrder, OrderDTO.class);

        return savedorderDTO;
    }

 */
}
