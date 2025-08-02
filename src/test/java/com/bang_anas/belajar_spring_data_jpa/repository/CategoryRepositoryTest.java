package com.bang_anas.belajar_spring_data_jpa.repository;

import com.bang_anas.belajar_spring_data_jpa.category.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;

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

    @Test
    void audit(){
        Category category = new Category();
        category.setName("Sample Audit");
        categoryRepository.save(category);

        assertNotNull(category.getId());
        assertNotNull(category.getCreatedDate());
        assertNotNull(category.getLastModifiedDate());
    }

    @Test
    void example() {
        Category category = new Category();
        category.setName("BUDI SPEED NYUENI");

        Example<Category> example = Example.of(category);
        List<Category> categories = categoryRepository.findAll(example);
        assertEquals(1,categories.size());
    }

    @Test
    void example2() {
        Category category = new Category();
        category.setName("BUDI SPEED NYUENI");
        category.setId(2L);

        Example<Category> example = Example.of(category);
        List<Category> categories = categoryRepository.findAll(example);
        assertEquals(1,categories.size());
    }

    @Test
    void exampleMatcher(){
        Category category = new Category();
        category.setName("BUDI speed NYUENI");
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();

        Example<Category> example = Example.of(category,matcher);
        List<Category> categories = categoryRepository.findAll(example);
        assertEquals(1,categories.size());
    }

}