package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.response.ProductDto;
import com.dmsacad.store.dtos.request.ProductRequest;
import com.dmsacad.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    //THIS MEANS map id of category "category.id" on Product to the field value of "categoryId" on ProductDto
    //Product entity has a field "category" of type "Category"
    ProductDto toDto(Product product);
    Product toEntity(ProductRequest request);
    void update(ProductRequest request, @MappingTarget Product product);
}
