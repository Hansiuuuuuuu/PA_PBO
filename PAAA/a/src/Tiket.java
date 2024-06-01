public class Tiket {
    private int idTiket;
    private int hargaTiket;
    private String asal;
    private String tujuan;
    private String jam;
    private int jumlahTiket;

    public Tiket(int idTiket, int hargaTiket, String asal, String tujuan, String jam, int jumlahTiket) {
        this.idTiket = idTiket;
        this.hargaTiket = hargaTiket;
        this.asal = asal;
        this.tujuan = tujuan;
        this.jam = jam;
        this.jumlahTiket = jumlahTiket;
    }

    public int getIdTiket() {
        return idTiket;
    }

    public int getHargaTiket() {
        return hargaTiket;
    }

    public String getAsal() {
        return asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public String getJam() {
        return jam;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }
}
