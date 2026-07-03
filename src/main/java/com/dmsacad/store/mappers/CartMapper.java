package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.response.CartDto;
import com.dmsacad.store.dtos.response.CartItemDto;
import com.dmsacad.store.entities.Cart;
import com.dmsacad.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source = "cartItems")//items here is for CartDto. cartItems is a field in Cart (Entity - Given that parameter is Cart cart in to_Do method). If we missSpell 'items' or 'cartItems' mapping won't work
    @Mapping(target = "cartTotalPrice", expression = "java(cart.getCartTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}