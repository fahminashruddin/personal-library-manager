# Personal Book Management App (Aplikasi Manajemen Buku Pribadi)

Sebuah aplikasi desktop yang dibangun menggunakan Java Swing untuk membantu pengguna mengelola dan melacak koleksi buku pribadi mereka. Pengguna dapat dengan mudah menambahkan, mengedit, menghapus, mencari, dan mengurutkan buku-buku mereka.

## Tampilan Aplikasi (Screenshots)

| Welcome Screen | Tampilan Utama | Form Tambah Buku |
| :---: | :---: | :---: |
| ![Welcome Screen](ManajemenBukuPribadiVscode/screenshots/welcomeScreen.png) | ![Tampilan Utama](ManajemenBukuPribadiVscode/screenshots/tampilanAwal.png) | ![Form Tambah Buku](ManajemenBukuPribadiVscode/screenshots/formTambahBuku.png) | ![Tampilan Buku Setelah Ditambahkan](ManajemenBukuPribadiVscode/screenshots/bukuSetelahDitambahkan.png)

## Fitur Utama (Features)
- **CRUD Penuh:** Tambah, Edit, dan Hapus data buku dari koleksi.
- **Pencarian Data:** Cari buku secara spesifik berdasarkan judul.
- **Pengurutan Data:** Urutkan daftar buku berdasarkan Judul atau Tanggal Terakhir Dibaca.
- **Pelacakan Status:** Lacak status baca setiap buku (misalnya, Selesai, Sedang Dibaca, Belum Dibaca).
- **Date Picker Interaktif:** Menggunakan komponen JDateChooser untuk pemilihan tanggal yang mudah.
- **Antarmuka Grafis (GUI):** Desain antarmuka yang bersih, intuitif, dan ramah pengguna.

## Teknologi yang Digunakan (Tech Stack)
* **Java**
* **Java Swing** (untuk komponen GUI)
* **JCalendar (JDateChooser)** (sebagai library eksternal untuk input tanggal)

## Cara Menjalankan Proyek (Local Setup & Run)

Berikut adalah panduan untuk mengkompilasi dan menjalankan proyek ini di komputer lokal.

**1. Prasyarat**
* JDK (Java Development Kit) versi 8 atau yang lebih baru.
* File library **jcalendar-1.4.jar**. Jika belum ada, unduh [di sini](https://repo1.maven.org/maven2/com/toedter/jcalendar/1.4/jcalendar-1.4.jar).

**2. Langkah-langkah Instalasi**
   1.  **Clone repository ini:**
       ```bash
       git clone [https://github.com/fahminashruddin/personal-library-manager.git](https://github.com/fahminashruddin/personal-library-manager.git)
       ```
   2.  **Masuk ke direktori proyek:**
       ```bash
       cd personal-library-manager
       ```
   3.  **Siapkan Library:** Buat folder baru bernama `lib` di dalam direktori proyek, lalu letakkan file `jcalendar-1.4.jar` di dalamnya.

**3. Kompilasi & Menjalankan**
   Buka terminal atau command prompt di direktori utama proyek, lalu jalankan perintah berikut.

   * **Untuk Kompilasi (Windows):**
       ```bash
       javac -cp ".;lib/jcalendar-1.4.jar" *.java
       ```
   * **Untuk Kompilasi (Linux/macOS):**
       ```bash
       javac -cp ".:lib/jcalendar-1.4.jar" *.java
       ```
   * **Untuk Menjalankan Program (Windows):**
       ```bash
       java -cp ".;lib/jcalendar-1.4.jar" ManajemenBukuGUI
       ```
   * **Untuk Menjalankan Program (Linux/macOS):**
       ```bash
       java -cp ".:lib/jcalendar-1.4.jar" ManajemenBukuGUI
       ```
       *(Catatan: Ganti `ManajemenBukuGUI` dengan nama class utama Anda yang berisi method `main` jika berbeda).*

## Lisensi
Proyek ini dilisensikan di bawah [MIT License](LICENSE).
