package com.bang_anas.belajar_spring_data_jpa.repository;

import com.bang_anas.belajar_spring_data_jpa.category.Category;
import com.bang_anas.belajar_spring_data_jpa.category.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Stream<Product> streamAllByCategory(Category category);

    @Modifying
    @Query("update Product p set p.price = :price where p.id = :id")
    int updateProductsPrice(@Param("id") Long id, Long price);

    @Modifying
    @Query("delete from Product p where p.name = :name")
    int deleteProductUsingName(@Param("name") String name);

    @Modifying
    @Query("update Product p set p.price = 0 where p.id = :id")
    int updateProductsPriceToZero(@Param("id") Long id);

    @Query(value = "select p from Product p where p.name like :name or p.category.name like :name",
            countQuery = "select count(p) from Product p where p.name like :name or p.category.name like :name"
    )
    Page<Product> searchProduct(@Param("name") String name, Pageable pageable);

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
