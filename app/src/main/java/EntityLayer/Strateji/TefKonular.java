package EntityLayer.Strateji;

import com.google.gson.annotations.SerializedName;

public class TefKonular {

    private Long id;
    private Long bolgeMudurluguId;
    private String bolgeAdi;
    private Long isletmeMudurluguId;
    private String isletmeAdi;
    private String belgeKod;
    private Long yil;
    private Long gorevEmirNo;
    private String tebligTarihi;
    private  String durum;
    private  String teftisBaslamaTarihi;
    private  String teftisBitisTarihi;
    private  String personelAdiSoyadi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBolgeMudurluguId() {
        return bolgeMudurluguId;
    }

    public void setBolgeMudurluguId(Long bolgeMudurluguId) {
        this.bolgeMudurluguId = bolgeMudurluguId;
    }

    public String getBolgeAdi() {
        return bolgeAdi;
    }

    public void setBolgeAdi(String bolgeAdi) {
        this.bolgeAdi = bolgeAdi;
    }

    public Long getIsletmeMudurluguId() {
        return isletmeMudurluguId;
    }

    public void setIsletmeMudurluguId(Long isletmeMudurluguId) {
        this.isletmeMudurluguId = isletmeMudurluguId;
    }

    public String getIsletmeAdi() {
        return isletmeAdi;
    }

    public void setIsletmeAdi(String isletmeAdi) {
        this.isletmeAdi = isletmeAdi;
    }

    public String getBelgeKod() {
        return belgeKod;
    }

    public void setBelgeKod(String belgeKod) {
        this.belgeKod = belgeKod;
    }

    public Long getYil() {
        return yil;
    }

    public void setYil(Long yil) {
        this.yil = yil;
    }

    public Long getGorevEmirNo() {
        return gorevEmirNo;
    }

    public void setGorevEmirNo(Long gorevEmirNo) {
        this.gorevEmirNo = gorevEmirNo;
    }

    public String getTebligTarihi() {
        return tebligTarihi;
    }

    public void setTebligTarihi(String tebligTarihi) {
        this.tebligTarihi = tebligTarihi;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getTeftisBaslamaTarihi() {
        return teftisBaslamaTarihi;
    }

    public void setTeftisBaslamaTarihi(String teftisBaslamaTarihi) {
        this.teftisBaslamaTarihi = teftisBaslamaTarihi;
    }

    public String getTeftisBitisTarihi() {
        return teftisBitisTarihi;
    }

    public void setTeftisBitisTarihi(String teftisBitisTarihi) {
        this.teftisBitisTarihi = teftisBitisTarihi;
    }

    public String getPersonelAdiSoyadi() {
        return personelAdiSoyadi;
    }

    public void setPersonelAdiSoyadi(String personelAdiSoyadi) {
        this.personelAdiSoyadi = personelAdiSoyadi;
    }
}
