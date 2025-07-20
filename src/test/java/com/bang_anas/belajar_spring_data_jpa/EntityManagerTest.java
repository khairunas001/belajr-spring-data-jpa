package com.bang_anas.belajar_spring_data_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityManagerTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory; // Menginject bean EntityManagerFactory

    @Test
    void testEntitiyManagerFactory(){
        // Memastikan bahwa entityManagerFactory tidak null (berhasil di-inject)
        Assertions.assertNotNull(entityManagerFactory);

        // Membuat instance EntityManager dari factory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Memastikan bahwa entityManager berhasil dibuat
        Assertions.assertNotNull(entityManager);

        // Menutup EntityManager setelah selesai digunakan
        entityManager.close();
    }
}
