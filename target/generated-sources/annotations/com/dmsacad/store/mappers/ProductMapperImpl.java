package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.request.ProductRequest;
import com.dmsacad.store.dtos.response.ProductDto;
import com.dmsacad.store.entities.Category;
import com.dmsacad.store.entities.Product;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T11:48:39-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Byte categoryId = null;
        long id = 0L;
        String name = null;
        BigDecimal price = null;
        String description = null;

        categoryId = productCategoryId( product );
        if ( product.getId() != null ) {
            id = product.getId();
        }
        name = product.getName();
        price = product.getPrice();
        description = product.getDescription();

        ProductDto productDto = new ProductDto( id, name, price, description, categoryId );

        return productDto;
    }

    @Override
    public Product toEntity(ProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.description( request.getDescription() );
        product.price( request.getPrice() );

        return product.build();
    }

    @Override
    public void update(ProductRequest request, Product product) {
        if ( request == null ) {
            return;
        }

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
    }

    private Byte productCategoryId(Product product) {
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getId();
    }
}
