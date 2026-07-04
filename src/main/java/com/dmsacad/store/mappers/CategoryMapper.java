package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.response.CartDto;
import com.dmsacad.store.dtos.response.CartItemDto;
import com.dmsacad.store.dtos.response.CategoryDto;
import com.dmsacad.store.entities.Cart;
import com.dmsacad.store.entities.CartItem;
import com.dmsacad.store.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}