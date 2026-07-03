package com.dmsacad.store.services;

import java.util.List;

import com.dmsacad.store.exceptions.OrderNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.dmsacad.store.dtos.response.OrderDto;
import com.dmsacad.store.mappers.OrderMapper;
import com.dmsacad.store.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrdersForCurrentUser() {
        var user = authService.getCurrentUser();
        //var orders = orderRepository.findOrderWithItems(user.getId());
        var orders = orderRepository.getOrdersByCustomer(user);
        //System.out.println("******************* Orders ******************===> FOUND: " + orders.size());
        return orders.stream().map(orderMapper::toDto).toList(); // Placeholder return statement
    }

    public OrderDto getOrder(Long orderId) throws OrderNotFoundException {
        var o = orderRepository.getOrderWithItems(orderId).orElseThrow(
                OrderNotFoundException::new
        );
        var user = authService.getCurrentUser();
        //!o.getCustomer().getId().equals(user.getId())
        if(!o.isPlacedBy(user)){//A user should not have access to another user orders
            throw new AccessDeniedException("You don't have access to this order");//403 Forbiden.
        }
        return orderMapper.toDto(o);
    }
}
