public class Pesanan {
    private String namaPemesan;
    private String noTelp;
    private String email;
    private int jumlahTiket;
    private int idTiket;

    public Pesanan(String namaPemesan, String noTelp, String email, int jumlahTiket, int idTiket) {
        this.namaPemesan = namaPemesan;
        this.noTelp = noTelp;
        this.email = email;
        this.jumlahTiket = jumlahTiket;
        this.idTiket = idTiket;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getEmail() {
        return email;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public int getIdTiket() {
        return idTiket;
    }
}