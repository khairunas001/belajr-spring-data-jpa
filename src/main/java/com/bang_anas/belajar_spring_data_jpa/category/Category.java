package com.bang_anas.belajar_spring_data_jpa.category;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    // Menandakan bahwa id adalah Primary Key dari entitas (tabel).
    //Wajib ada pada setiap entitas JPA agar Hibernate/JPA tahu kolom mana yang menjadi identifier unik.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //  Mengatur cara pengisian otomatis nilai primary key oleh database.
    //  Strategy IDENTITY berarti:
    //  Nilai id akan di-generate oleh database (misalnya: kolom auto-increment di MySQL, PostgreSQL).
    //  Setiap insert data baru, database akan otomatis menambahkan nilai id berikutnya.
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}
