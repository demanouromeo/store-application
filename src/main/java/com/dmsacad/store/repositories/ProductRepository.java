package com.dmsacad.store.repositories;

import com.dmsacad.store.entities.Cart;
import com.dmsacad.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId (Byte categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p where p.id = :productId")
    Optional<Product> findProductWithCategory(@Param("productId") long productId);

    /*
    @Query("select p from Product p join fetch p.category")
    List<Product> findAllWithCategory();
    */
    //OR we use EntityGraph
    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p")
    List<Product> findAllWithCategory();

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p where p.category.id = :catId")
    List<Product> findAllWithCategoryId(@Param("catId")Byte categoryId);

}