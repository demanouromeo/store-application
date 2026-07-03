package com.dmsacad.store.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dmsacad.store.entities.Order;
import com.dmsacad.store.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer(User user);

    @EntityGraph(attributePaths = "items.products")
    @Query("select o from Order o where o.customer.id = :userId")//FAILED TO SOLVE N+1 PROBLEM
    List<Order> findOrderWithItems(@Param("userId")Long userId);

    @EntityGraph(attributePaths = "items.product")//SOLVES N+1 problem
    @Query("select o from Order o where o.customer = :customer")
    List<Order> getOrdersByCustomer(@Param("customer")User customer);

    @EntityGraph(attributePaths = "items.product")//SOLVES N+1 problem
    @Query("select o from Order o where o.id = :orderId")
    Optional<Order> getOrderWithItems(@Param("orderId")Long orderId);
}
