package EntityLayer.Eizin;

import java.math.BigDecimal;

public class Izin {
    private Long id;
    private String kanunMaddesi;
    private Integer sayi;
    private BigDecimal alanHa;

    private Long yil;
    private String bolgeAdi;
    private String isletmeAdi;
    private String seflikAdi;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKanunMaddesi() {
        return kanunMaddesi;
    }

    public void setKanunMaddesi(String kanunMaddesi) {
        this.kanunMaddesi = kanunMaddesi;
    }

    public Integer getSayi() {
        return sayi;
    }

    public void setSayi(Integer sayi) {
        this.sayi = sayi;
    }

    public BigDecimal getAlanHa() {
        return alanHa;
    }

    public void setAlanHa(BigDecimal alanHa) {
        this.alanHa = alanHa;
    }

    public Long getYil() {
        return yil;
    }

    public void setYil(Long yil) {
        this.yil = yil;
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

}
