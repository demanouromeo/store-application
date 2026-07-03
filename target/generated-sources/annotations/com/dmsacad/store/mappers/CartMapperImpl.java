package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.response.CartDto;
import com.dmsacad.store.dtos.response.CartItemDto;
import com.dmsacad.store.dtos.response.CartProductDto;
import com.dmsacad.store.entities.Cart;
import com.dmsacad.store.entities.CartItem;
import com.dmsacad.store.entities.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-03T15:39:54-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDto toDto(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDto cartDto = new CartDto();

        cartDto.setItems( cartItemSetToCartItemDtoList( cart.getCartItems() ) );
        cartDto.setId( cart.getId() );

        cartDto.setCartTotalPrice( cart.getCartTotalPrice() );

        return cartDto;
    }

    @Override
    public CartItemDto toDto(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemDto.CartItemDtoBuilder cartItemDto = CartItemDto.builder();

        cartItemDto.product( productToCartProductDto( cartItem.getProduct() ) );
        cartItemDto.quantity( cartItem.getQuantity() );

        cartItemDto.totalPrice( cartItem.getTotalPrice() );

        return cartItemDto.build();
    }

    protected List<CartItemDto> cartItemSetToCartItemDtoList(Set<CartItem> set) {
        if ( set == null ) {
            return null;
        }

        List<CartItemDto> list = new ArrayList<CartItemDto>( set.size() );
        for ( CartItem cartItem : set ) {
            list.add( toDto( cartItem ) );
        }

        return list;
    }

    protected CartProductDto productToCartProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        BigDecimal price = null;

        if ( product.getId() != null ) {
            id = product.getId();
        }
        name = product.getName();
        price = product.getPrice();

        CartProductDto cartProductDto = new CartProductDto( id, name, price );

        return cartProductDto;
    }
}
