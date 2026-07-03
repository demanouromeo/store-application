package com.dmsacad.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    //@OnDelete(action = OnDeleteAction.CASCADE)//WE DON'T NEED THIS SINCE WE IMPLEMENTED CASCADE AT DB LEVEL
    @JoinColumn(name = "cart_id")
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    //@ColumnDefault("1")//WE DON'T NEED DEFAULT VALUE SINCE IT IS DEFINE AT DB LEVEL
    @Column(name = "quantity")
    private Integer quantity;


    public BigDecimal getTotalPrice(){
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}