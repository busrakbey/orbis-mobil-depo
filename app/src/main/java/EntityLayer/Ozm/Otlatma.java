package EntityLayer.Ozm;

import java.math.BigDecimal;

public class Otlatma {
    private Long id;
    private String bolgeAdi;
    private String isletmeAdi;
    private String seflikAdi;
    private Long yil;
    private BigDecimal serbestAlan;
    private BigDecimal oncelikliAlan;
    private BigDecimal yasakAlan;
    private BigDecimal suAlan;
    private BigDecimal planDisiAlan;


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

    public Long getYil() {
        return yil;
    }

    public void setYil(Long yil) {
        this.yil = yil;
    }

    public BigDecimal getSerbestAlan() {
        return serbestAlan;
    }

    public void setSerbestAlan(BigDecimal serbestAlan) {
        this.serbestAlan = serbestAlan;
    }

    public BigDecimal getOncelikliAlan() {
        return oncelikliAlan;
    }

    public void setOncelikliAlan(BigDecimal oncelikliAlan) {
        this.oncelikliAlan = oncelikliAlan;
    }

    public BigDecimal getYasakAlan() {
        return yasakAlan;
    }

    public void setYasakAlan(BigDecimal yasakAlan) {
        this.yasakAlan = yasakAlan;
    }

    public BigDecimal getSuAlan() {
        return suAlan;
    }

    public void setSuAlan(BigDecimal suAlan) {
        this.suAlan = suAlan;
    }

    public BigDecimal getPlanDisiAlan() {
        return planDisiAlan;
    }

    public void setPlanDisiAlan(BigDecimal planDisiAlan) {
        this.planDisiAlan = planDisiAlan;
    }
}
