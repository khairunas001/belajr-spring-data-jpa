package com.bang_anas.belajar_spring_data_jpa.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "Product.searchProductUsingName",
                query = "SELECT p FROM Product p WHERE p.name = :name") // jika ingin mengggunakan sort masukkan di sini
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
}
