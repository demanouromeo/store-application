package com.dmsacad.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    //@Size(max = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id"/*, nullable = false, length = 36//nullable and length already defined at DB level*/)
    private UUID id;


    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.MERGE /*, CascadeType.PERSIST*/}, orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<CartItem> cartItems = new HashSet<>();

    public BigDecimal getCartTotalPrice(){
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        /*WE ALSO USE LOOP AS FOLLOW:
        BigDecimal total = BigDecimal.ZERO;
        for(CartItem item: items){
            total = total.add(item.getTotalPrice);
        }
        */
    }

    public CartItem getItem(Long productId){
        return getCartItems().stream()
                .filter(u->u.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product){
        CartItem cartItem = getItem(product.getId());
        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else{
            cartItem = CartItem.builder()
                    .product(product)
                    .cart(this)
                    .quantity(1)
                    .build();
            cartItems.add(cartItem);
        }
        return cartItem;
    }

    public boolean removeItem(CartItem cartItem){
        try {
            cartItems.remove(cartItem);
            cartItem.setCart(null);//THIS INSTRUCTION IS IMPORTANT. By adding orphanRemoval = true, to @OneToMany the cartItem will be deleted when removed from cart
            return true;
        } catch (Exception e) {
            System.err.println("Cart.removeItem(): Error occurs ==> "+e.getMessage());
        }
        return false;
    }

    public void clear() {
        cartItems.clear();
    }
}