package com.dmsacad.store.controllers;

import com.dmsacad.store.dtos.request.CartItemRequest;
import com.dmsacad.store.dtos.request.CartItemUpdateRequest;
import com.dmsacad.store.dtos.response.CartDto;
import com.dmsacad.store.dtos.response.CartItemDto;
import com.dmsacad.store.entities.CartItem;
import com.dmsacad.store.entities.Cart;
import com.dmsacad.store.exceptions.CartNotFoundException;
import com.dmsacad.store.exceptions.ProductNotFoundException;
import com.dmsacad.store.mappers.CartMapper;
import com.dmsacad.store.repositories.CartRepository;
import com.dmsacad.store.repositories.ProductRepository;
import com.dmsacad.store.services.CartService;
import com.dmsacad.store.tools.Utility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
@Tag(name = "Carts")//The default controller on swagger UI is cart-controller. Using Here we changed it into "Carts"
public class CartController {
    //private final CartMapper cartMapper;
    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Find all the carts each with list of its items")//@Operation Enable us to provide short description of end point that can be seen on swagger UI
    Iterable<CartDto> getAllCarts() {
        /* N+1 Problem
        return cartRepository.findAll(Sort.by("dateCreated").descending())
                .stream().map(cartMapper::toDto).toList();
        */
        //Solving N+1 problem
        return cartService.findAllCarts();

        //return userRepository.findAll()
        //        .stream().map(user -> userMapper.toDto(user)).toList();
    }

    @GetMapping("/{cartId}")
    ResponseEntity<CartDto> getCart(
            @Parameter(description = "ID of the cart")//To provide description of the parameter
            @PathVariable String cartId) {
        CartDto cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        CartDto cartDto = cartService.creaACart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @Parameter(description = "ID of the cart")
            @PathVariable String cartId,
            @Valid @RequestBody CartItemRequest cartItemRequest) {

        CartItemDto res = cartService.addToCart(cartId, cartItemRequest.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/update-cart-item")
    public ResponseEntity<?> updateCartItem(@Valid @RequestBody CartItemUpdateRequest request) {
        CartItemDto cartItemDto = cartService.updateCart(request.getCartId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().body(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable(required = true) @NotNull String cartId,
            @PathVariable(required = true) @Min(1) Long productId) {
        cartService.deleteItem(cartId, productId);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(//Delete all cartItems related to a cart
                                       @PathVariable(required = true) @NotNull String cartId
    ) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

}
