package EntityLayer.Kadastro;

import com.google.gson.annotations.SerializedName;

public class KdmDosya {

    private Long id;
    private Long bolgeId;
    private String bolgeAdi;
    private Long isletmeId;
    private String isletmeAdi;
    private Long basMuhId;
    private String basMuhAdi;
    private Long yil;
    private Double ormanAlan;
    private  Double ormanDisiAlan;
    private  Double cikarilanAlan;

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

    public Long getBasMuhId() {
        return basMuhId;
    }

    public void setBasMuhId(Long basMuhId) {
        this.basMuhId = basMuhId;
    }

    public String getBasMuhAdi() {
        return basMuhAdi;
    }

    public void setBasMuhAdi(String basMuhAdi) {
        this.basMuhAdi = basMuhAdi;
    }

    public Long getYil() {
        return yil;
    }

    public void setYil(Long yil) {
        this.yil = yil;
    }

    public Double getOrmanAlan() {
        return ormanAlan;
    }

    public void setOrmanAlan(Double ormanAlan) {
        this.ormanAlan = ormanAlan;
    }

    public Double getOrmanDisiAlan() {
        return ormanDisiAlan;
    }

    public void setOrmanDisiAlan(Double ormanDisiAlan) {
        this.ormanDisiAlan = ormanDisiAlan;
    }

    public Double getCikarilanAlan() {
        return cikarilanAlan;
    }

    public void setCikarilanAlan(Double cikarilanAlan) {
        this.cikarilanAlan = cikarilanAlan;
    }
}
