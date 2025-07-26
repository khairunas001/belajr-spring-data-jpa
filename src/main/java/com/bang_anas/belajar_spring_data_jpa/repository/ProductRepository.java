package com.bang_anas.belajar_spring_data_jpa.repository;

import com.bang_anas.belajar_spring_data_jpa.category.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p where p.name like :name or p.category.name like :name")
    List<Product> searchProduct(@Param("name") String name);

    List<Product> searchProductUsingName(@Param("name") String name, Pageable pageable);

    @Transactional
        // Diperlukan karena default transaksi Spring Data JPA adalah readOnly = true.
        // Untuk operasi delete (write), harus override menjadi transaksi tulis.
    int deleteByName(String name);

    boolean existsByName(String name);

    Long countByCategory_Name(String name);

    List<Product> findAllByCategory_Name(String name);

    List<Product> findAllByCategory_Name(String name, Sort sort);

    Page<Product> findAllByCategory_Name(String name, Pageable pageable);
}
