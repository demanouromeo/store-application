package com.dmsacad.store.mappers;

import org.mapstruct.Mapper;

import com.dmsacad.store.dtos.response.OrderDto;
import com.dmsacad.store.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    //OrderItemDto toDto(OrderItem orderItem);//getAllOrders in OrderController.java works even if this line is commented out. Since mapstruct can handle the mapping of nested objects automatically
}
