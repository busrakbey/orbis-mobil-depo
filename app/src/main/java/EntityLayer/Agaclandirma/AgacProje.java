package EntityLayer.Agaclandirma;

import java.math.BigDecimal;

public class AgacProje {

    private Long id;
    private Long bolgeId;
    private String bolgeAdi;
    private Long isletmeId;
    private String isletmeAdi;
    private Long seflikId;
    private String seflikAdi;
    private Long yil;
    private BigDecimal programMiktar;
    private BigDecimal programTutar;
    private BigDecimal gerceklesmeMiktar;
    private BigDecimal gerceklesmeTutar;

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

    public BigDecimal getProgramMiktar() {
        return programMiktar;
    }

    public void setProgramMiktar(BigDecimal programMiktar) {
        this.programMiktar = programMiktar;
    }

    public BigDecimal getProgramTutar() {
        return programTutar;
    }

    public void setProgramTutar(BigDecimal programTutar) {
        this.programTutar = programTutar;
    }

    public BigDecimal getGerceklesmeMiktar() {
        return gerceklesmeMiktar;
    }

    public void setGerceklesmeMiktar(BigDecimal gerceklesmeMiktar) {
        this.gerceklesmeMiktar = gerceklesmeMiktar;
    }

    public BigDecimal getGerceklesmeTutar() {
        return gerceklesmeTutar;
    }

    public void setGerceklesmeTutar(BigDecimal gerceklesmeTutar) {
        this.gerceklesmeTutar = gerceklesmeTutar;
    }
}
