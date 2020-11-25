package EntityLayer.DisIliskiler;

import java.math.BigDecimal;

public class YurtdisiProtokol {

    private Long id;
    private Long kisiId;
    private String kisiAdi;
    private Long birimId;
    private String birimAdi;
    private String kurum;
    private Long ulkeId;
    private String ulkeAdi;
    private Integer yil;
    private String gorevTuru;
    private String konu;
    private String gidisTarihi;
    private String gelisTarihi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKisiId() {
        return kisiId;
    }

    public void setKisiId(Long kisiId) {
        this.kisiId = kisiId;
    }

    public String getKisiAdi() {
        return kisiAdi;
    }

    public void setKisiAdi(String kisiAdi) {
        this.kisiAdi = kisiAdi;
    }

    public Long getBirimId() {
        return birimId;
    }

    public void setBirimId(Long birimId) {
        this.birimId = birimId;
    }

    public String getBirimAdi() {
        return birimAdi;
    }

    public void setBirimAdi(String birimAdi) {
        this.birimAdi = birimAdi;
    }

    public String getKurum() {
        return kurum;
    }

    public void setKurum(String kurum) {
        this.kurum = kurum;
    }

    public Long getUlkeId() {
        return ulkeId;
    }

    public void setUlkeId(Long ulkeId) {
        this.ulkeId = ulkeId;
    }

    public String getUlkeAdi() {
        return ulkeAdi;
    }

    public void setUlkeAdi(String ulkeAdi) {
        this.ulkeAdi = ulkeAdi;
    }

    public Integer getYil() {
        return yil;
    }

    public void setYil(Integer yil) {
        this.yil = yil;
    }

    public String getGorevTuru() {
        return gorevTuru;
    }

    public void setGorevTuru(String gorevTuru) {
        this.gorevTuru = gorevTuru;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public String getGidisTarihi() {
        return gidisTarihi;
    }

    public void setGidisTarihi(String gidisTarihi) {
        this.gidisTarihi = gidisTarihi;
    }

    public String getGelisTarihi() {
        return gelisTarihi;
    }

    public void setGelisTarihi(String gelisTarihi) {
        this.gelisTarihi = gelisTarihi;
    }
}
