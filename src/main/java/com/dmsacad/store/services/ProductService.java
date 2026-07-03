package com.dmsacad.store.services;

import com.dmsacad.store.dtos.request.ProductRequest;
import com.dmsacad.store.dtos.response.ProductDto;
import com.dmsacad.store.entities.Product;
import com.dmsacad.store.exceptions.CategoryNotFoundException;
import com.dmsacad.store.exceptions.ProductNotFoundException;
import com.dmsacad.store.exceptions.ProductOnlyNotFoundException;
import com.dmsacad.store.mappers.ProductMapper;
import com.dmsacad.store.repositories.CategoryRepository;
import com.dmsacad.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public List<ProductDto> getAllProducts(Byte categoryId) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findAllWithCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }
        if (products.isEmpty()) {
            //return ResponseEntity.notFound().build();
            throw new ProductNotFoundException();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    public ProductDto getAProduct(long id) {
        Product prod = productRepository.findById(id).orElse(null);
        if (prod == null) {
            //return ResponseEntity.notFound().build();
            throw new ProductOnlyNotFoundException();
        }
        return productMapper.toDto(prod);
    }

    public void deleteAProduct(Long id) {
        var prod = productRepository.findById(id).orElse(null);
        if (prod == null) {
            //return ResponseEntity.notFound().build();
            throw new ProductOnlyNotFoundException();
        }
        productRepository.delete(prod);
    }

    public ProductDto updateAProduct(Long id, ProductRequest request) {
        var prod = productRepository.findById(id).orElse(null);
        if (prod == null) {
            //return ResponseEntity.notFound().build();
            throw new ProductOnlyNotFoundException();
        }
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            //return ResponseEntity.badRequest().build(); //Category not found
            throw new CategoryNotFoundException();
        }

        productMapper.update(request, prod);
        prod.setCategory(category);
        System.out.println("Updating product: " + prod);
        productRepository.save(prod);
        return productMapper.toDto(prod);
    }

    public ProductDto createAProduct(@Valid ProductRequest request) {
        var product = productMapper.toEntity(request);
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            //return ResponseEntity.notFound().build(); //CATEGORY NOT FOUND
            //return ResponseEntity.badRequest().build();//THE USER PROVIDE A WRONG CATEGORY
            throw new CategoryNotFoundException();
        }
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toDto(product);
    }
}
