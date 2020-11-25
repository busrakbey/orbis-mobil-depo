package EntityLayer.InsaatIkmal;

import java.math.BigDecimal;

public class InikYol {


    private Long id;
    private Long bolgeId;
    private String bolgeAdi;
    private Long isletmeId;
    private String isletmeAdi;
    private Long seflikId;
    private String seflikAdi;
    private Long yil;
    private BigDecimal toplamTul;
    private BigDecimal mevcutTul;
    private BigDecimal yapilacakTul;
    private BigDecimal ormanYoluTulu;
    private BigDecimal yapilacakBuyukOnarimTulu;
    private  Integer sanatYapisiAdet;
    private BigDecimal gerceklesenTul;


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

    public Long getYil() {
        return yil;
    }

    public void setYil(Long yil) {
        this.yil = yil;
    }

    public BigDecimal getToplamTul() {
        return toplamTul;
    }

    public void setToplamTul(BigDecimal toplamTul) {
        this.toplamTul = toplamTul;
    }

    public BigDecimal getMevcutTul() {
        return mevcutTul;
    }

    public void setMevcutTul(BigDecimal mevcutTul) {
        this.mevcutTul = mevcutTul;
    }

    public BigDecimal getYapilacakTul() {
        return yapilacakTul;
    }

    public void setYapilacakTul(BigDecimal yapilacakTul) {
        this.yapilacakTul = yapilacakTul;
    }

    public BigDecimal getOrmanYoluTulu() {
        return ormanYoluTulu;
    }

    public void setOrmanYoluTulu(BigDecimal ormanYoluTulu) {
        this.ormanYoluTulu = ormanYoluTulu;
    }

    public BigDecimal getYapilacakBuyukOnarimTulu() {
        return yapilacakBuyukOnarimTulu;
    }

    public void setYapilacakBuyukOnarimTulu(BigDecimal yapilacakBuyukOnarimTulu) {
        this.yapilacakBuyukOnarimTulu = yapilacakBuyukOnarimTulu;
    }

    public Integer getSanatYapisiAdet() {
        return sanatYapisiAdet;
    }

    public void setSanatYapisiAdet(Integer sanatYapisiAdet) {
        this.sanatYapisiAdet = sanatYapisiAdet;
    }

    public BigDecimal getGerceklesenTul() {
        return gerceklesenTul;
    }

    public void setGerceklesenTul(BigDecimal gerceklesenTul) {
        this.gerceklesenTul = gerceklesenTul;
    }
}
