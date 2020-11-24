package EntityLayer.BilgiSistemleri;

public class HaritaArsiv {

    private Long id;
    private String bolgeAdi;
    private String isletmeAdi;
    private String hesapAdi;
    private Long demirbasSayi;


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

    public String getHesapAdi() {
        return hesapAdi;
    }

    public void setHesapAdi(String hesapAdi) {
        this.hesapAdi = hesapAdi;
    }

    public Long getDemirbasSayi() {
        return demirbasSayi;
    }

    public void setDemirbasSayi(Long demirbasSayi) {
        this.demirbasSayi = demirbasSayi;
    }
}
