package com.bang_anas.belajar_spring_data_jpa.service;

import com.bang_anas.belajar_spring_data_jpa.category.Category;
import com.bang_anas.belajar_spring_data_jpa.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void manual() {
        assertThrows(
                RuntimeException.class,
                () -> {
                    categoryService.manual();
                }
        );
    }

    @Test
    void createCategorires() {
        assertThrows(
                RuntimeException.class,
                () -> {
                    categoryService.createCategories();
                }
        );
    }

    @Test
        // jika memanggula categoryService.create(); dari luar class maka AOP dapat berjalan dengan baik, dan
        // transaksional bisa berjalan dengan baik
    void success() {
        assertThrows(
                RuntimeException.class,
                () -> {
                    categoryService.create();
                }
        );
    }

    @Test
        // dengan memangil fungsi categoryService.create();
        // dari dalam class maka tidak akan menjalankan AOP atau
        // transaksinya tidak dijalankan dengan benar karena data masuk ke database sebelum melakukan commit
    void failed() {
        assertThrows(
                RuntimeException.class,
                () -> {
                    categoryService.startCreate();
                }
        );
    }

    @Test
    void queryMethod() {
        Category category = categoryRepository.findFirstByNameEquals("BUDI SPEED NYUENI").orElse(null);
        assertNotNull(category);
        assertEquals(
                "BUDI SPEED NYUENI",
                category.getName()
        );

        List<Category> categories = categoryRepository.findAllByNameLike("%manual%");
        assertNotNull(categories);
        assertEquals(
                5,
                categories.size()
        );
        assertEquals("Test manual 1", categories.get(0).getName());

        System.out.println(categories);
    }
}
