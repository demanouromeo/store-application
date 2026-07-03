package com.dmsacad.store.repositories;

import com.dmsacad.store.dtos.response.CartDto;
import com.dmsacad.store.entities.Cart;
import com.dmsacad.store.tools.Utility;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @EntityGraph(attributePaths = "cartItems.product")
    @Query("select c from Cart c where c.id = :cardId")
    Optional<Cart> findCartWithItems(@Param("cardId") UUID uuid);

    @EntityGraph(attributePaths = "cartItems.product")
    @Query("select c from Cart c order by c.dateCreated desc")
    List<Cart> findAllCartWithItems();


}