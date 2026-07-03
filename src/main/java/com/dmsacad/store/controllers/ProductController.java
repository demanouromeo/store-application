package com.dmsacad.store.controllers;

import com.dmsacad.store.dtos.request.ProductRequest;
import com.dmsacad.store.dtos.response.ProductDto;
import com.dmsacad.store.entities.Product;
import com.dmsacad.store.mappers.ProductMapper;
import com.dmsacad.store.mappers.UserMapper;
import com.dmsacad.store.repositories.CategoryRepository;
import com.dmsacad.store.repositories.ProductRepository;
import com.dmsacad.store.repositories.UserRepository;
import com.dmsacad.store.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@Tag(name = "Products")//The default controller on swagger UI is product-controller. Using Here we changed it into "Products"
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Find all products in DB")//@Operation Enable us to provide short description of end point that can be seen on swagger UI
    ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestHeader(name = "x-auth-token", required = false) String authToken,
            @Parameter(description = "ID of the Category")
            @RequestParam(required = false, name = "category_id") Byte categoryId
    ) {
        System.out.println("AUTH TOKEN = " + authToken);
        List<ProductDto> products = productService.getAllProducts(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(
            @Parameter(description = "ID of the product")
            @PathVariable long id) {
        ProductDto productDto = productService.getAProduct(id);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteAProduct(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        ProductDto productDto = productService.updateAProduct(id, request);
        return ResponseEntity.ok(productDto);

    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody ProductRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        ProductDto productDto = productService.createAProduct(request);
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto); //===> Code = 201

    }

}
