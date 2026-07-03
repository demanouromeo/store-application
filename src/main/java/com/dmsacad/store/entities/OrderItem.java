package com.dmsacad.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    //@ManyToOne(fetch = FetchType.LAZY, optional = false)//optional = false, Tells hibernate that the foreign key cannot be null
    @ManyToOne//We get rid of optinal = false since this is handled at db level
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "unit_price")
    private BigDecimal unitPrice;


    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "total_price")
    private BigDecimal totalPrice;


}