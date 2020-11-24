package EntityLayer.IsletmePazarlama;

public class Uretim {
    private Long id;
    private String urunAdi;
    private Double gmiktar;
    private Double pmiktar;

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

    public Double getGmiktar() {
        return gmiktar;
    }

    public void setGmiktar(Double gmiktar) {
        this.gmiktar = gmiktar;
    }

    public Double getPmiktar() {
        return pmiktar;
    }

    public void setPmiktar(Double pmiktar) {
        this.pmiktar = pmiktar;
    }
}
