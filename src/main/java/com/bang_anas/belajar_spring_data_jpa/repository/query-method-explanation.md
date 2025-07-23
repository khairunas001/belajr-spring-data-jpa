# Tabel Penamaan Query Method

| **Contoh Method**                        | **Query SQL yang Dihasilkan**                                   | **Keterangan**                                    |
|------------------------------------------|-----------------------------------------------------------------|---------------------------------------------------|
| `findByName(String name)`                | `SELECT * FROM category WHERE name = ?`                        | Pencarian data berdasarkan nilai persis kolom `name`. |
| `findFirstByNameEquals(String name)`     | `SELECT * FROM category WHERE name = ? LIMIT 1`                | Mengambil 1 record pertama dengan `name` persis.     |
| `findAllByNameLike(String name)`         | `SELECT * FROM category WHERE name LIKE ?`                     | Pencarian dengan pola LIKE (wildcard: `%value%`).     |
| `findByNameContaining(String name)`      | `SELECT * FROM category WHERE name LIKE %?%`                   | Nama mengandung substring tertentu.                  |
| `findByNameStartingWith(String prefix)`  | `SELECT * FROM category WHERE name LIKE ?%`                    | Nama diawali dengan string tertentu.                 |
| `findByNameEndingWith(String suffix)`    | `SELECT * FROM category WHERE name LIKE %?`                    | Nama diakhiri dengan string tertentu.                |
| `findById(Long id)`                      | `SELECT * FROM category WHERE id = ?`                          | Pencarian berdasarkan primary key `id`.              |
| `countByName(String name)`               | `SELECT COUNT(*) FROM category WHERE name = ?`                 | Menghitung jumlah baris dengan `name` tertentu.      |
| `existsByName(String name)`              | `SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END ...`   | Mengecek apakah data dengan `name` ada.              |
| `deleteByName(String name)`              | `DELETE FROM category WHERE name = ?`                          | Menghapus semua data dengan `name` tertentu.         |
