package com.bang_anas.belajar_spring_data_jpa.repository;

import com.bang_anas.belajar_spring_data_jpa.category.Category;
import com.bang_anas.belajar_spring_data_jpa.category.Product;
import com.bang_anas.belajar_spring_data_jpa.model.ProductPrice;
import com.bang_anas.belajar_spring_data_jpa.model.SimpleProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionOperations;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionOperations transactionOperations;


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

    @Test
    void testCount(){

        long count = productRepository.count();
        assertEquals(3L,count);

        count = productRepository.countByCategory_Name("BUDI SPEED NYUENI");
        assertEquals(2L,count);

        count = productRepository.countByCategory_Name("Test manual 1");
        assertEquals(1L,count);

        count = productRepository.countByCategory_Name("Test manual 5");
        assertEquals(0L,count);
    }


    @Test
    void testExists(){
        boolean exists = productRepository.existsByName("MacBook Pro Air M5");
        assertTrue(exists);

        exists = productRepository.existsByName("Playstation 5");
        assertFalse(exists);
    }

    @Test
    void testDeleteWrapInTransactionOperations(){
        // delete query secara default di gunakan di dalam wrap transactionOperations
        transactionOperations.executeWithoutResult(transactionStatus -> { // Transaction 1
            Category category = categoryRepository.findById(2L).orElse(null);
            assertNotNull(category);

            Product product =new Product();
            product.setName("Mobil Toyota Mirai");
            product.setPrice(500_000_000L);
            product.setCategory(category);
            productRepository.save(product); // Transaction 1

            int delete = productRepository.deleteByName("Mobil Toyota Mirai"); // Transaction 1
            assertEquals(1,delete);

            delete = productRepository.deleteByName("Mobil Toyota Mirai"); // Transaction 1
            assertEquals(0,delete);
        });
    }

    @Test
    void testDeleteUsingTransactionalAnnotationOnDeletePrefixQuery() {

        // jika menggunakan ini akan terdapat 3 transaction sehingga
        // jika salah satu gagal maka tidak akan di rollback, gunakan sesuai kebutuhan
        Category category = categoryRepository.findById(2L).orElse(null);
        assertNotNull(category);

        Product product = new Product();
        product.setName("Mobil Toyota Mirai");
        product.setPrice(500_000_000L);
        product.setCategory(category);
        productRepository.save(product);  // Transaction 1

        int delete = productRepository.deleteByName("Mobil Toyota Mirai"); // Transaction 2
        assertEquals(1, delete);

        delete = productRepository.deleteByName("Mobil Toyota Mirai"); // Transaction 3
        assertEquals(0, delete);

    }

    @Test
    void searchProduct(){
        // querry dibawah tidak bisa karena sort harus dimasukkan di @NamedQuerry
        // jika menggunakan Sort.Order.desc("id")) tidak akan dikirim query nya
        // Pageable pageable = PageRequest.of(0,1, Sort.by(Sort.Order.desc("id")));
        Pageable pageable = PageRequest.of(0,1);
        List<Product> products = productRepository.searchProductUsingName("MacBook Pro Air M5",pageable);
        assertEquals(1,products.size());
        assertEquals("MacBook Pro Air M5", products.get(0).getName());
    }

    @Test
    void searchProductLike(){
        Pageable pageable = PageRequest.of(0,1);
        Page<Product> products = productRepository.searchProduct("%5%",pageable);
        assertEquals(1, products.getContent().size());

        assertEquals(0, products.getNumber());
        assertEquals(2, products.getTotalPages());
        assertEquals(2, products.getTotalElements());

        products = productRepository.searchProduct("%NYUENI%",pageable);
        assertEquals(1,products.getContent().size());

        assertEquals(0, products.getNumber());
        assertEquals(2, products.getTotalPages());
        assertEquals(2, products.getTotalElements());
    }

    @Test
    void modifying(){
        transactionOperations.executeWithoutResult(transactionStatus -> {
            int total = productRepository.deleteProductUsingName("Wrong");
            assertEquals(0,total);

            total = productRepository.updateProductsPriceToZero(1L);
            assertEquals(1,total);

            Product product = productRepository.findById(1L).orElse(null);
            assertNotNull(product);
            assertEquals(0,product.getPrice());

        });
    }

    @Test
    void modifying2(){
        transactionOperations.executeWithoutResult(transactionStatus -> {
            int updatedProductsPrice = productRepository.updateProductsPrice(
                    1L,
                    10_000_000L
            );
            assertEquals(1,updatedProductsPrice);

            Product product = productRepository.findById(1L).orElse(null);
            assertEquals(10_000_000L,product.getPrice());
        });
    }

    @Test
        //    @Transactional
    void stream() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            Category category = categoryRepository.findById(2L).orElse(null);
            assertNotNull(category);

            Stream<Product> stream = productRepository.streamAllByCategory(category);
            stream.forEach(product -> System.out.println(product.getId() + " : " + product.getName()));

        });
    }


    @Test
    void slice(){
       Pageable firstPage = PageRequest.of(0,1);

        Category category = categoryRepository.findById(2L).orElse(null);
        assertNotNull(category);

        Slice<Product> slice = productRepository.findAllByCategory(
                category,
                firstPage
        );
            //tampilkan konten
        while(slice.hasNext()){
            slice = productRepository.findAllByCategory(
                    category,
                    slice.nextPageable()
            );
            //tampilkan konten
        }

    }

    @Test
    void lock1(){
        transactionOperations.executeWithoutResult(transactionStatus -> {
            try{
                Product product = productRepository.findFirstByIdEquals(1L).orElse(null);
                assertNotNull(product);
                product.setPrice(30_000_000L);

                Thread.sleep(20_000L);
                productRepository.save(product);
            }catch (InterruptedException exception){
                throw new RuntimeException(exception);
            }
        });
    }

    @Test
    void lock2(){
        transactionOperations.executeWithoutResult(transactionStatus -> {
            Product product = productRepository.findFirstByIdEquals(1L).orElse(null);
            assertNotNull(product);
            product.setPrice(10_000_000L);
            productRepository.save(product);
        });
    }

    @Test
    void spesification() {
        Specification<Product> specification = (root, criteria, builder) -> {
            return criteria.where(
                    builder.or(
                            builder.equal(root.get("name"),("MacBook Pro Air M5")),
                            builder.equal(root.get("name"),("PS5"))
                    )
            ).getRestriction();
        };

        List<Product> products = productRepository.findAll(specification);
        assertEquals(2,products.size());
    }


    @Test
    void projection() {
        List<SimpleProduct> simpleProducts = productRepository.findAllByNameLike("%5%", SimpleProduct.class);
        assertEquals(2,simpleProducts.size());

        List<ProductPrice> productPrices = productRepository.findAllByNameLike("%5%", ProductPrice.class);
        assertEquals(2,productPrices.size());
    }
}