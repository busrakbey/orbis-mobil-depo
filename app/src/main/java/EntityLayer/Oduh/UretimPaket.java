package EntityLayer.Oduh;

public class UretimPaket {

    private Long id;
    private Long bolgeId;
    private String bolgeAdi;
    private Long isletmeId;
    private String isletmeAdi;
    private Long seflikId;
    private String seflikAdi;
    private Long uretimYili;
    private Double alinanUrunAdedi;
    private Double alinanUrunMiktari;
    private Double uretilecekToplamMiktar;
    private String turAdi;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBolgeId() {
        return bolgeId;
    }

    public void setBolgeId(Long bolgeId) {
        this.bolgeId = bolgeId;
    }

    public String getBolgeAdi() {
        return bolgeAdi;
    }

    public void setBolgeAdi(String bolgeAdi) {
        this.bolgeAdi = bolgeAdi;
    }

    public Long getIsletmeId() {
        return isletmeId;
    }

    public void setIsletmeId(Long isletmeId) {
        this.isletmeId = isletmeId;
    }

    public String getIsletmeAdi() {
        return isletmeAdi;
    }

    public void setIsletmeAdi(String isletmeAdi) {
        this.isletmeAdi = isletmeAdi;
    }

    public Long getSeflikId() {
        return seflikId;
    }

    public void setSeflikId(Long seflikId) {
        this.seflikId = seflikId;
    }

    public String getSeflikAdi() {
        return seflikAdi;
    }

    public void setSeflikAdi(String seflikAdi) {
        this.seflikAdi = seflikAdi;
    }



    public String getTurAdi() {
        return turAdi;
    }

    public void setTurAdi(String turAdi) {
        this.turAdi = turAdi;
    }


    public Long getUretimYili() {
        return uretimYili;
    }

    public void setUretimYili(Long uretimYili) {
        this.uretimYili = uretimYili;
    }

    public Double getAlinanUrunAdedi() {
        return alinanUrunAdedi;
    }

    public void setAlinanUrunAdedi(Double alinanUrunAdedi) {
        this.alinanUrunAdedi = alinanUrunAdedi;
    }

    public Double getAlinanUrunMiktari() {
        return alinanUrunMiktari;
    }

    public void setAlinanUrunMiktari(Double alinanUrunMiktari) {
        this.alinanUrunMiktari = alinanUrunMiktari;
    }

    public Double getUretilecekToplamMiktar() {
        return uretilecekToplamMiktar;
    }

    public void setUretilecekToplamMiktar(Double uretilecekToplamMiktar) {
        this.uretilecekToplamMiktar = uretilecekToplamMiktar;
    }
}
