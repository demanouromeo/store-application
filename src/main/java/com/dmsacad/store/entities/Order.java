package com.dmsacad.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //@NotNull//NO NEED SINCE WE HANDLED THIS AT DB LEVEL
    //@ManyToOne(fetch = FetchType.LAZY, optional = false)//optional = false, Tells hibernate that the foreign key cannot be null
    @ManyToOne//WE HANDLE optional=false at db level that is why we got rid of it
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    //@ColumnDefault("current_timestamp()")//No need, already handled at DB level. When we create order, dbmanger will in build function current_timestamp()
    @Column(name = "created_at", insertable = false, updatable = false)//To tell hibernate to exclude theses field from our query. It the DB manager that insert created_at the time we created order. Once order create hibernat should update this field
    private LocalDateTime createdAt;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    //WHY cascade = CascadeType.PERSIST
    // --> In a one to many, When the father is saved children are not saved.  CascadeType.PERSIST solves the problem
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})//PERSIST AND REMOVE IS TO TELL SPRING TO save or removes items of the order when it is removed or persisted
    @Builder.Default
    private Set<OrderItem> items = new HashSet<>();

    public static @NonNull Order fromCart(Cart cart, User customer) {
        var order = Order.builder()
                .totalPrice(cart.getCartTotalPrice())
                .status(PaymentStatus.PENDING)
                .customer(customer)
                .build();
        cart.getCartItems().forEach(item->{
            var orderItem = OrderItem.builder().order(order)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .unitPrice(item.getProduct().getPrice())
                    .build();
            order.items.add(orderItem);

        });
        return order;
    }

    public boolean isPlacedBy(User customer){
        //return this.customer.getId().equals(customer.getId());
        return this.customer.equals(customer);
    }

}
