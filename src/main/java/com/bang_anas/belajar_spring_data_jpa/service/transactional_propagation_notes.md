
# 🌱 Spring @Transactional - Propagation

## 📌 Apa itu Propagation?

`Propagation` adalah cara menentukan **bagaimana perilaku transaksi** ketika sebuah method dipanggil, terutama saat method transactional memanggil method transactional lain.

---

## 🔄 Daftar Jenis Propagation

| Propagation           | Deskripsi                                                                                  |
|-----------------------|---------------------------------------------------------------------------------------------|
| `REQUIRED` (default)  | Gunakan transaksi yang ada. Jika belum ada, buat transaksi baru.                            |
| `REQUIRES_NEW`        | Selalu buat transaksi baru. Jika ada transaksi aktif, suspend terlebih dahulu.              |
| `MANDATORY`           | Harus ada transaksi aktif. Jika tidak ada, lempar `IllegalTransactionStateException`.      |
| `NEVER`               | Tidak boleh ada transaksi aktif. Jika ada, lempar exception.                                |
| `SUPPORTS`            | Jika ada transaksi, ikut. Jika tidak ada, jalankan tanpa transaksi.                         |
| `NOT_SUPPORTED`       | Jalankan tanpa transaksi. Jika ada transaksi aktif, akan disuspend.                         |
| `NESTED`              | Buat transaksi bersarang di dalam transaksi utama. Bisa rollback sendiri tanpa rollback parent. |

---

## 📘 Contoh Penggunaan

### 1. `REQUIRED` (default)
```java
@Transactional(propagation = Propagation.REQUIRED)
public void saveData() {
    // Gunakan transaksi yang ada atau buat baru jika tidak ada
}
```

### 2. `REQUIRES_NEW`
```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void insertLog() {
    // Suspend transaksi saat ini dan buat transaksi baru
}
```

### 3. `MANDATORY`
```java
@Transactional(propagation = Propagation.MANDATORY)
public void updateRecord() {
    // Wajib dipanggil dari transaksi aktif, jika tidak, error
}
```

### 4. `NEVER`
```java
@Transactional(propagation = Propagation.NEVER)
public void auditLog() {
    // Tidak boleh dijalankan dalam transaksi
}
```

### 5. `SUPPORTS`
```java
@Transactional(propagation = Propagation.SUPPORTS)
public void readOnlyMethod() {
    // Ikut transaksi jika ada, kalau tidak, jalan biasa
}
```

### 6. `NOT_SUPPORTED`
```java
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public void noTxOperation() {
    // Suspend transaksi dan jalankan tanpa transaksi
}
```

### 7. `NESTED`
```java
@Transactional(propagation = Propagation.NESTED)
public void savePartialData() {
    // Bisa rollback mandiri tanpa rollback seluruh transaksi
}
```

---

## 🎯 Kapan Menggunakan?

| Use Case                        | Pilihan Propagation |
|----------------------------------|----------------------|
| Operasi normal sehari-hari       | `REQUIRED`           |
| Logging, audit terpisah          | `REQUIRES_NEW`       |
| Validasi yang wajib transactional| `MANDATORY`          |
| Operasi yang harus bebas transaksi | `NEVER`, `NOT_SUPPORTED` |
| Operasi opsional transaksi       | `SUPPORTS`           |
| Transaksi nested/partial rollback| `NESTED`             |

---

## ⚠️ Catatan Penting
- `@Transactional` menggunakan **Spring AOP proxy**, jadi method transactional **harus dipanggil dari luar**, bukan `this.method()`.
- Tidak semua `Propagation.NESTED` didukung di semua database atau JPA provider.

---
