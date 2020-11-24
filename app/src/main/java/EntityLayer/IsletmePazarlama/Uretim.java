package EntityLayer.IsletmePazarlama;

public class Uretim {
    private Long id;
    private String urunAdi;
    private Double gMiktar;
    private Double pMiktar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public Double getgMiktar() {
        return gMiktar;
    }

    public void setgMiktar(Double gMiktar) {
        this.gMiktar = gMiktar;
    }

    public Double getpMiktar() {
        return pMiktar;
    }

    public void setpMiktar(Double pMiktar) {
        this.pMiktar = pMiktar;
    }
}
