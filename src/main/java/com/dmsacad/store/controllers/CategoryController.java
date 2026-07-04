package com.dmsacad.store.controllers;

import com.dmsacad.store.dtos.ErrorDto;
import com.dmsacad.store.dtos.request.CategoryRequest;
import com.dmsacad.store.dtos.request.RegisteredUserRequest;
import com.dmsacad.store.dtos.response.CategoryDto;
import com.dmsacad.store.entities.Category;
import com.dmsacad.store.mappers.CategoryMapper;
import com.dmsacad.store.repositories.CategoryRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<?> addCategory(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody CategoryRequest request
    ){
        if(categoryRepository.existsByName(request.getName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Category with name '"+request.getName()+"' already exsists"));
        }
        Category cat = Category.builder().name(request.getName()).build();
        categoryRepository.save(cat);
        CategoryDto catDto = categoryMapper.toDto(cat);
        var uri = uriBuilder.path("/category/{id}").buildAndExpand(catDto.getId()).toUri();
        return ResponseEntity.created(uri).body(catDto);
    }
}
