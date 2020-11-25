package EntityLayer.Ozm;

import java.math.BigDecimal;

public class Yirtici {

    private Long id;
    private String bolgeAdi;
    private String isletmeAdi;
    private String seflikAdi;
    private Integer yil;
    private String laboratuvarAdi;
    private Integer gerceklesmeAdet;
    private BigDecimal gerceklesmeHarcama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBolgeAdi() {
        return bolgeAdi;
    }

    public void setBolgeAdi(String bolgeAdi) {
        this.bolgeAdi = bolgeAdi;
    }

    public String getIsletmeAdi() {
        return isletmeAdi;
    }

    public void setIsletmeAdi(String isletmeAdi) {
        this.isletmeAdi = isletmeAdi;
    }

    public String getSeflikAdi() {
        return seflikAdi;
    }

    public void setSeflikAdi(String seflikAdi) {
        this.seflikAdi = seflikAdi;
    }

    public Integer getYil() {
        return yil;
    }

    public void setYil(Integer yil) {
        this.yil = yil;
    }

    public String getLaboratuvarAdi() {
        return laboratuvarAdi;
    }

    public void setLaboratuvarAdi(String laboratuvarAdi) {
        this.laboratuvarAdi = laboratuvarAdi;
    }

    public Integer getGerceklesmeAdet() {
        return gerceklesmeAdet;
    }

    public void setGerceklesmeAdet(Integer gerceklesmeAdet) {
        this.gerceklesmeAdet = gerceklesmeAdet;
    }

    public BigDecimal getGerceklesmeHarcama() {
        return gerceklesmeHarcama;
    }

    public void setGerceklesmeHarcama(BigDecimal gerceklesmeHarcama) {
        this.gerceklesmeHarcama = gerceklesmeHarcama;
    }
}
