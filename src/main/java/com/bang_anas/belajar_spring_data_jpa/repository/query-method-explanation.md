# 📚 Tabel Penamaan Query Method di Spring Data JPA

Spring Data JPA menyediakan cara deklaratif untuk membuat query SQL langsung dari nama method. Berikut adalah berbagai contoh dan hasil query-nya:

---

## 🔍 Tabel 1 – Query SELECT Sederhana

| **Method**                               | **Query SQL yang Dihasilkan**                                     | **Keterangan**                                                  |
|------------------------------------------|-------------------------------------------------------------------|------------------------------------------------------------------|
| `findByName(String name)`                | `SELECT * FROM category WHERE name = ?`                           | Cari data berdasarkan nilai kolom `name`.                       |
| `findFirstByNameEquals(String name)`     | `SELECT * FROM category WHERE name = ? LIMIT 1`                   | Ambil 1 data pertama yang sesuai dengan `name`.                 |
| `findAllByNameLike(String name)`         | `SELECT * FROM category WHERE name LIKE ?`                        | Cari data dengan wildcard LIKE, misalnya `%manual%`.            |
| `findByNameContaining(String name)`      | `SELECT * FROM category WHERE name LIKE %?%`                      | Nama mengandung substring tertentu.                             |
| `findByNameStartingWith(String prefix)`  | `SELECT * FROM category WHERE name LIKE ?%`                       | Nama diawali string tertentu.                                   |
| `findByNameEndingWith(String suffix)`    | `SELECT * FROM category WHERE name LIKE %?`                       | Nama diakhiri string tertentu.                                  |
| `findById(Long id)`                      | `SELECT * FROM category WHERE id = ?`                             | Cari berdasarkan primary key.                                   |
| `findTop3ByOrderByIdDesc()`              | `SELECT * FROM category ORDER BY id DESC LIMIT 3`                 | Ambil 3 data terbaru berdasarkan ID.                            |
| `findByNameIn(List<String> names)`       | `SELECT * FROM category WHERE name IN (?, ?, ... )`              | Cari data dengan nama yang termasuk dalam list.                 |

---

## 🔢 Tabel 2 – Query COUNT dan EXISTS

| **Method**                  | **Query SQL yang Dihasilkan**                                     | **Keterangan**                                                  |
|-----------------------------|-------------------------------------------------------------------|------------------------------------------------------------------|
| `countByName(String name)`  | `SELECT COUNT(*) FROM category WHERE name = ?`                   | Hitung jumlah data dengan `name` tertentu.                      |
| `existsByName(String name)` | `SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END ...`     | Mengecek keberadaan data dengan `name` tertentu.                |

---

## ❌ Tabel 3 – Query DELETE

| **Method**                  | **Query SQL yang Dihasilkan**               | **Keterangan**                                      |
|-----------------------------|---------------------------------------------|-----------------------------------------------------|
| `deleteByName(String name)` | `DELETE FROM category WHERE name = ?`       | Hapus semua data yang memiliki `name` tertentu.     |

---

## 🔗 Tabel 4 – Query JOIN (Relasi Antar Entity)

| **Method**                                      | **Query SQL yang Dihasilkan**                                                           | **Keterangan**                                                                 |
|--------------------------------------------------|-------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `findAllByCategory_Name(String name)`            | `SELECT * FROM product p JOIN category c ON p.category_id = c.id WHERE c.name = ?`       | Cari semua `Product` yang punya `Category` dengan `name` tertentu.             |
| `findByCategory_Id(Long id)`                     | `SELECT * FROM product WHERE category_id = ?`                                            | Cari produk berdasarkan `id` kategori-nya.                                      |
| `findAllByCategory_NameContaining(String name)`  | `SELECT * FROM product p JOIN category c ON ... WHERE c.name LIKE %?%`                   | Cari produk yang kategorinya mengandung string tertentu.                        |
| `findAllByCategory_NameStartingWith(String s)`   | `SELECT * FROM product p JOIN category c ON ... WHERE c.name LIKE ?%`                    | Nama kategori diawali string tertentu.                                          |

> ⚠ Pastikan bahwa `Product` memiliki relasi seperti:
> ```java
> @ManyToOne
> private Category category;
> ```

---

## 📁 Tabel 5 – Contoh Query Kombinasi (Filter + Sort + Limit)

| **Method**                                       | **Query SQL yang Dihasilkan**                                               | **Keterangan**                                              |
|--------------------------------------------------|-----------------------------------------------------------------------------|-------------------------------------------------------------|
| `findTop5ByNameContainingOrderByIdDesc(String name)` | `SELECT * FROM category WHERE name LIKE %?% ORDER BY id DESC LIMIT 5`       | Ambil 5 data terakhir yang mengandung kata tertentu.        |
| `findFirstByNameOrderByIdDesc(String name)`         | `SELECT * FROM category WHERE name = ? ORDER BY id DESC LIMIT 1`           | Ambil data terakhir (terbaru) yang sesuai `name`.           |

---

## 📌 Tips Tambahan

- Spring Data JPA akan otomatis melakukan JOIN jika field adalah relasi (`@ManyToOne`, `@OneToMany`, dll.).
- Kamu bisa menggunakan `@Query(...)` jika ingin menulis JPQL/custom native query.
- Gunakan pagination dengan `Pageable`, misalnya:

```java
Page<Product> findByCategory_Name(String name, Pageable pageable);
