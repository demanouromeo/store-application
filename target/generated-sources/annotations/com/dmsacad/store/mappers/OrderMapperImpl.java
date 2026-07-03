package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.response.OrderDto;
import com.dmsacad.store.dtos.response.OrderItemDto;
import com.dmsacad.store.dtos.response.OrderProductDto;
import com.dmsacad.store.entities.Order;
import com.dmsacad.store.entities.OrderItem;
import com.dmsacad.store.entities.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-03T13:19:35-0400",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setCreatedAt( order.getCreatedAt() );
        orderDto.setId( order.getId() );
        orderDto.setItems( orderItemSetToOrderItemDtoList( order.getItems() ) );
        if ( order.getStatus() != null ) {
            orderDto.setStatus( order.getStatus().name() );
        }
        orderDto.setTotalPrice( order.getTotalPrice() );

        return orderDto;
    }

    protected OrderProductDto productToOrderProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        OrderProductDto orderProductDto = new OrderProductDto();

        if ( product.getId() != null ) {
            orderProductDto.setId( product.getId() );
        }
        orderProductDto.setName( product.getName() );
        orderProductDto.setPrice( product.getPrice() );

        return orderProductDto;
    }

    protected OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setProduct( productToOrderProductDto( orderItem.getProduct() ) );
        if ( orderItem.getQuantity() != null ) {
            orderItemDto.setQuantity( orderItem.getQuantity() );
        }
        orderItemDto.setTotalPrice( orderItem.getTotalPrice() );

        return orderItemDto;
    }

    protected List<OrderItemDto> orderItemSetToOrderItemDtoList(Set<OrderItem> set) {
        if ( set == null ) {
            return null;
        }

        List<OrderItemDto> list = new ArrayList<OrderItemDto>( set.size() );
        for ( OrderItem orderItem : set ) {
            list.add( orderItemToOrderItemDto( orderItem ) );
        }

        return list;
    }
}
