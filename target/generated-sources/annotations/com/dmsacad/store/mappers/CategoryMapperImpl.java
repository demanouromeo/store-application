package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.response.CategoryDto;
import com.dmsacad.store.entities.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T19:57:38-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        if ( category.getId() != null ) {
            categoryDto.setId( category.getId() );
        }
        categoryDto.setName( category.getName() );

        return categoryDto;
    }
}
