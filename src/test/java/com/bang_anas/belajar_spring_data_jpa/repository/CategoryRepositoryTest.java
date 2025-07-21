package com.bang_anas.belajar_spring_data_jpa.repository;

import com.bang_anas.belajar_spring_data_jpa.category.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreate() {
        Category category = new Category();
        category.setName("BUDI SPEED NYUENI");

        categoryRepository.save(category);

        assertNotNull(category.getId());
    }

    @Test
    void testUpdate() {
        Category category = categoryRepository.findById(1L).orElse(null);
        assertNotNull(category);

        category.setName("BUDI SPEED NYUENI POLL KUADRAT");
        categoryRepository.save(category);

        category = categoryRepository.findById(1L).orElse(null);
        assertNotNull(category);
        assertEquals(
                "BUDI SPEED NYUENI POLL KUADRAT",
                category.getName()
        );

    }

    @Test
    void testDelete() {
        Category category = categoryRepository.findById(1L).orElse(null);
        assertNotNull(category);

        categoryRepository.delete(category);

        category = categoryRepository.findById(1L).orElse(null);
        assertNull(category);
    }

}