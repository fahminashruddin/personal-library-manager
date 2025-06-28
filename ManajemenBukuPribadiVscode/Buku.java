public class Buku {
    private String judul;
    private String penulis;
    private String genre;
    private String statusBaca;
    private String tanggalSelesai;
    private String catatanPribadi;

    // Constructor
    public Buku(String judul, String penulis, String genre, String statusBaca, String tanggalSelesai, String catatanPribadi) {
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.statusBaca = statusBaca;
        this.tanggalSelesai = tanggalSelesai;
        this.catatanPribadi = catatanPribadi;
    }

    // Getters
    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public String getGenre() {
        return genre;
    }

    public String getStatusBaca() {
        return statusBaca;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public String getCatatanPribadi() {
        return catatanPribadi;
    }

    // Setters
    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStatusBaca(String statusBaca) {
        this.statusBaca = statusBaca;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public void setCatatanPribadi(String catatanPribadi) {
        this.catatanPribadi = catatanPribadi;
    }
}
