package com.bang_anas.belajar_spring_data_jpa.repository;

import com.bang_anas.belajar_spring_data_jpa.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
