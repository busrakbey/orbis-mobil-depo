package EntityLayer.IsletmePazarlama;

import java.math.BigDecimal;

public class Uretim {
    private Long id;
    private String urunAdi;
    private BigDecimal gmiktar;
    private BigDecimal pmiktar;

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

    public BigDecimal getGmiktar() {
        return gmiktar;
    }

    public void setGmiktar(BigDecimal gmiktar) {
        this.gmiktar = gmiktar;
    }

    public BigDecimal getPmiktar() {
        return pmiktar;
    }

    public void setPmiktar(BigDecimal pmiktar) {
        this.pmiktar = pmiktar;
    }
}
