package EntityLayer.Ozm;

import java.math.BigDecimal;

public class KusKarinca {

    private Long id;
    private String bolgeAdi;
    private String isletmeAdi;
    private String seflikAdi;
    private Integer yil;
    private String laboratuvarAdi;
    private Integer kusYuvasiAdet;
    private Integer karincaNakliAdet;

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

    public Integer getKusYuvasiAdet() {
        return kusYuvasiAdet;
    }

    public void setKusYuvasiAdet(Integer kusYuvasiAdet) {
        this.kusYuvasiAdet = kusYuvasiAdet;
    }

    public Integer getKarincaNakliAdet() {
        return karincaNakliAdet;
    }

    public void setKarincaNakliAdet(Integer karincaNakliAdet) {
        this.karincaNakliAdet = karincaNakliAdet;
    }
}
