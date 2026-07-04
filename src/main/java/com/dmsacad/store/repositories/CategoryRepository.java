package com.dmsacad.store.repositories;

import com.dmsacad.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {

    boolean existsByName(String name);
}