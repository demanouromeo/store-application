package com.dmsacad.store.services;

import com.dmsacad.store.dtos.response.CartDto;
import com.dmsacad.store.dtos.response.CartItemDto;
import com.dmsacad.store.entities.Cart;
import com.dmsacad.store.entities.CartItem;
import com.dmsacad.store.exceptions.CartNotFoundException;
import com.dmsacad.store.exceptions.ProductNotFoundException;
import com.dmsacad.store.mappers.CartMapper;
import com.dmsacad.store.repositories.CartRepository;
import com.dmsacad.store.repositories.ProductRepository;
import com.dmsacad.store.tools.Utility;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto creaACart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(String cartId, long productId) {
        UUID cardIdAsUUID = Utility.hexStringToUUID(cartId);
        var cart = cartRepository.findCartWithItems(cardIdAsUUID).orElse(null);
        if (cart == null) {
            //return ResponseEntity.notFound().build();
            throw new CartNotFoundException();
        }

        var product = productRepository.findProductWithCategory(productId).orElse(null);
        if (product == null) {
            //return ResponseEntity.badRequest().build();
            throw new ProductNotFoundException();
        }
        CartItem cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return  cartMapper.toDto(cartItem);
    }

    public CartItemDto updateCart(String cardId, long productId, int quantity) {
        UUID cardIdAsUUID = Utility.hexStringToUUID(cardId);
        //var cart = cartRepository.findById(cardIdAsUUID).orElse(null); ==> N+1 PB
        var cart = cartRepository.findCartWithItems(cardIdAsUUID).orElse(null); //==> SOLVE N+1 PB
        if (cart == null) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Cart with id '" + cardIdAsUUID + "' not found"));
            throw new CartNotFoundException();
        }
        CartItem cartItem = cart.getItem(productId);
        if (cartItem == null) {
            System.out.println("Cart id [" + cardIdAsUUID + "] not related to Product id [" + productId + "]");
            //return ResponseEntity.notFound().build();
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "cart_item with cart id '" + cardIdAsUUID + "' and product_id '" + request.getProductId() + "' not found"));
            throw new ProductNotFoundException();

        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void deleteItem(String cartId, Long productId) {
        UUID cartUUID = Utility.hexStringToUUID(cartId);
        var cart = cartRepository.findCartWithItems(cartUUID).orElse(null);
        if (cart == null) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Cart with id '" + cartId + "' not found"));
            throw new CartNotFoundException();
        }

        CartItem cartItem = cart.getItem(productId);
        if (cartItem == null) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Cart item not found for product '" + productId + "' and cart '" + cartId + "' "));
            throw new ProductNotFoundException();
        }
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    public void clearCart(@NotNull String cartId) {
        UUID cartUUID = Utility.hexStringToUUID(cartId);
        var cart = cartRepository.findCartWithItems(cartUUID).orElse(null);
        if (cart == null) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Cart with id '" + cartId + "' not found"));
            throw new CartNotFoundException();
        }
        cart.clear();
        cartRepository.save(cart);
    }

    public void clearCart2(@NotNull UUID cartUUID) {
        var cart = cartRepository.findCartWithItems(cartUUID).orElse(null);
        if (cart == null) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Cart with id '" + cartId + "' not found"));
            throw new CartNotFoundException();
        }
        cart.clear();
        cartRepository.save(cart);
    }

    public CartDto getCart(String cartId){
        var cart = cartRepository.findCartWithItems(Utility.hexStringToUUID(cartId)).orElse(null);
        if (cart == null) {
            //return ResponseEntity.notFound().build();
            throw new CartNotFoundException();
        }
        return cartMapper.toDto(cart);
    }

    public Iterable<CartDto> findAllCarts() {
        return cartRepository.findAllCartWithItems()
                .stream()
                .map(cartMapper::toDto)
                .toList();
    }
}
