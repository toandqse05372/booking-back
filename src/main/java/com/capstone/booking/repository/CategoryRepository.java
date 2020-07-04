package com.capstone.booking.repository;

import com.capstone.booking.entity.Category;
import com.capstone.booking.repository.customRepository.CategoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustom {
    Category findOneByTypeKey(String key);

    Category findByTypeName(String typeName);
}
