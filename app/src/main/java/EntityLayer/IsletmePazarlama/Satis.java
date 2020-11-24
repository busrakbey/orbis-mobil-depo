package EntityLayer.IsletmePazarlama;

import java.math.BigDecimal;

public class Satis {
    private Long id;
    private String urunAdi;
    private Long sira;
    private BigDecimal aaMiktar;
    private BigDecimal aaTutar;
    private BigDecimal tmiktar;
    private BigDecimal ttutar;
    private BigDecimal kmiktar;
    private BigDecimal ktutar;
    private BigDecimal programMiktar;
    private BigDecimal programTutar;

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

    public Long getSira() {
        return sira;
    }

    public void setSira(Long sira) {
        this.sira = sira;
    }

    public BigDecimal getAaMiktar() {
        return aaMiktar;
    }

    public void setAaMiktar(BigDecimal aaMiktar) {
        this.aaMiktar = aaMiktar;
    }

    public BigDecimal getAaTutar() {
        return aaTutar;
    }

    public void setAaTutar(BigDecimal aaTutar) {
        this.aaTutar = aaTutar;
    }

    public BigDecimal getTmiktar() {
        return tmiktar;
    }

    public void setTmiktar(BigDecimal tmiktar) {
        this.tmiktar = tmiktar;
    }

    public BigDecimal getTtutar() {
        return ttutar;
    }

    public void setTtutar(BigDecimal ttutar) {
        this.ttutar = ttutar;
    }

    public BigDecimal getKmiktar() {
        return kmiktar;
    }

    public void setKmiktar(BigDecimal kmiktar) {
        this.kmiktar = kmiktar;
    }

    public BigDecimal getKtutar() {
        return ktutar;
    }

    public void setKtutar(BigDecimal ktutar) {
        this.ktutar = ktutar;
    }

    public BigDecimal getProgramMiktar() {
        return programMiktar;
    }

    public void setProgramMiktar(BigDecimal programMiktar) {
        this.programMiktar = programMiktar;
    }

    public BigDecimal getProgramTutar() {
        return programTutar;
    }

    public void setProgramTutar(BigDecimal programTutar) {
        this.programTutar = programTutar;
    }
}
