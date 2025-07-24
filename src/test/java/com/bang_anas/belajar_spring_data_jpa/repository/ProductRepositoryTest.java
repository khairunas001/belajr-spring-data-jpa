package com.bang_anas.belajar_spring_data_jpa.repository;

import com.bang_anas.belajar_spring_data_jpa.category.Category;
import com.bang_anas.belajar_spring_data_jpa.category.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    @Test
    void createProducts() {
        Category category = categoryRepository.findById(2L).orElse(null);
        assertNotNull(category);

        {
            Product product = new Product();
            product.setName("MacBook Pro Air M5");
            product.setPrice(25_000_000L);
            product.setCategory(category);
            productRepository.save(product);
        }

        {
            Product product = new Product();
            product.setName("PS5");
            product.setPrice(7_000_000L);
            product.setCategory(category);
            productRepository.save(product);
        }
    }

    @Test
    void findProducts() {
        List<Product> products = productRepository.findAllByCategory_Name("BUDI SPEED NYUENI");
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("MacBook Pro Air M5", products.get(0).getName());
        assertEquals("PS5", products.get(1).getName());
    }

    @Test
    void findProductSort() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Product> products = productRepository.findAllByCategory_Name(
                "BUDI SPEED NYUENI",
                sort
        );
        assertNotNull(products);assertEquals(2, products.size());
        assertEquals("PS5", products.get(0).getName());
        assertEquals("MacBook Pro Air M5", products.get(1).getName());

        for (Product product : products) {
            System.out.println("ID: " + product.getId() +
                                       ", Name: " + product.getName() +
                                       ", Price: " + product.getPrice() +
                                       ", Category: " + product.getCategory().getName());
        }


    }

    @Test
    void findProductPageble() {
        // page number 0
        Pageable pageable = PageRequest.of(
                0,
                1,
                Sort.by(Sort.Order.desc("id"))
        );
        Page<Product> products = productRepository.findAllByCategory_Name("BUDI SPEED NYUENI", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals(0, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("PS5", products.getContent().get(0).getName());


        // page number 1
        pageable = PageRequest.of(
                1,
                1,
                Sort.by(Sort.Order.desc("id"))
        );
        products = productRepository.findAllByCategory_Name("BUDI SPEED NYUENI", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals(1, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("MacBook Pro Air M5", products.getContent().get(0).getName());

    }

}