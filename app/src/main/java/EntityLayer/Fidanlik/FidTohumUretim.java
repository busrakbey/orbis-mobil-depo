package EntityLayer.Fidanlik;

import java.math.BigDecimal;

public class FidTohumUretim {

     Long id;
     Long bolgeId;
     String bolgeAdi;
     Long isletmeId;
     String isletmeAdi;
     Long seflikId;
     String seflikAdi;
     Short uretimYili;
     String cetvelTuru;
     String agacTuru;
     String orijin;
     BigDecimal miktar;

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

    public Short getUretimYili() {
        return uretimYili;
    }

    public void setUretimYili(Short uretimYili) {
        this.uretimYili = uretimYili;
    }

    public String getCetvelTuru() {
        return cetvelTuru;
    }

    public void setCetvelTuru(String cetvelTuru) {
        this.cetvelTuru = cetvelTuru;
    }

    public String getAgacTuru() {
        return agacTuru;
    }

    public void setAgacTuru(String agacTuru) {
        this.agacTuru = agacTuru;
    }

    public String getOrijin() {
        return orijin;
    }

    public void setOrijin(String orijin) {
        this.orijin = orijin;
    }

    public BigDecimal getMiktar() {
        return miktar;
    }

    public void setMiktar(BigDecimal miktar) {
        this.miktar = miktar;
    }
}
