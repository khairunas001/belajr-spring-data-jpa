package com.bang_anas.belajar_spring_data_jpa.service;

import com.bang_anas.belajar_spring_data_jpa.category.Category;
import com.bang_anas.belajar_spring_data_jpa.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionOperations;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionOperations transactionOperations;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public void manual() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setTimeout(10);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transaction = transactionManager.getTransaction(definition);
        try {
            for (int i = 1; i <= 5; i++) {
                Category category = new Category();
                category.setName("Test manual " + i);
                categoryRepository.save(category);
            }
            error();
            transactionManager.rollback(transaction);

        } catch (Throwable throwable) {
            transactionManager.rollback(transaction);
            throw throwable;
        }
    }

    void error() {
        throw new RuntimeException("Ups gagal rek");
    }

    public void createCategories() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            for (int i = 1; i <= 5; i++) {
                Category category = new Category();
                category.setName("Test Category " + i);
                categoryRepository.save(category);
            }
            error();
        });
    }

    @Transactional
    // method pada annotasion @Transactional
    // harus di panggil dari luar class agar AOP dapat
    // berjalan dengan semestinya
    public void create() {
        for (int i = 1; i <= 5; i++) {
            Category category = new Category();
            category.setName("Test Category " + i);
            categoryRepository.save(category);
        }
        throw new RuntimeException("the transaction has been rollback");
    }

    public void startCreate() {
        create();
    }
}
