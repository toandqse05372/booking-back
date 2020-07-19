package com.capstone.booking.repository;

import com.capstone.booking.entity.Category;
import com.capstone.booking.repository.customRepository.CategoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

//query to category table
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustom {
    //find category by key
    Category findOneByTypeKey(String key);

    //find by name
    Category findByTypeName(String typeName);
}
