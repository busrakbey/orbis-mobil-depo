package EntityLayer.Orkoy;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Orkoy {

    private Long id;
    private Long bolgeMudurlukId;
    private String bolgeMudurlukAdi;
    private Long isletmeMudurlukId;
    private String isletmeMudurlukAdi;
    private Long isletmeSeflikId;
    private String isletmeSeflikAdi;
    private BigDecimal planlananKredi;
    private BigDecimal planlananHibe;
    private  BigDecimal planlananToplam;
    private  Integer planlananAdet;
    private  BigDecimal gerceklesenToplam;
    private  Integer gerceklesenAdet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBolgeMudurlukId() {
        return bolgeMudurlukId;
    }

    public void setBolgeMudurlukId(Long bolgeMudurlukId) {
        this.bolgeMudurlukId = bolgeMudurlukId;
    }

    public String getBolgeMudurlukAdi() {
        return bolgeMudurlukAdi;
    }

    public void setBolgeMudurlukAdi(String bolgeMudurlukAdi) {
        this.bolgeMudurlukAdi = bolgeMudurlukAdi;
    }

    public Long getIsletmeMudurlukId() {
        return isletmeMudurlukId;
    }

    public void setIsletmeMudurlukId(Long isletmeMudurlukId) {
        this.isletmeMudurlukId = isletmeMudurlukId;
    }

    public String getIsletmeMudurlukAdi() {
        return isletmeMudurlukAdi;
    }

    public void setIsletmeMudurlukAdi(String isletmeMudurlukAdi) {
        this.isletmeMudurlukAdi = isletmeMudurlukAdi;
    }

    public Long getIsletmeSeflikId() {
        return isletmeSeflikId;
    }

    public void setIsletmeSeflikId(Long isletmeSeflikId) {
        this.isletmeSeflikId = isletmeSeflikId;
    }

    public String getIsletmeSeflikAdi() {
        return isletmeSeflikAdi;
    }

    public void setIsletmeSeflikAdi(String isletmeSeflikAdi) {
        this.isletmeSeflikAdi = isletmeSeflikAdi;
    }

    public BigDecimal getPlanlananKredi() {
        return planlananKredi;
    }

    public void setPlanlananKredi(BigDecimal planlananKredi) {
        this.planlananKredi = planlananKredi;
    }

    public BigDecimal getPlanlananHibe() {
        return planlananHibe;
    }

    public void setPlanlananHibe(BigDecimal planlananHibe) {
        this.planlananHibe = planlananHibe;
    }

    public BigDecimal getPlanlananToplam() {
        return planlananToplam;
    }

    public void setPlanlananToplam(BigDecimal planlananToplam) {
        this.planlananToplam = planlananToplam;
    }

    public Integer getPlanlananAdet() {
        return planlananAdet;
    }

    public void setPlanlananAdet(Integer planlananAdet) {
        this.planlananAdet = planlananAdet;
    }

    public BigDecimal getGerceklesenToplam() {
        return gerceklesenToplam;
    }

    public void setGerceklesenToplam(BigDecimal gerceklesenToplam) {
        this.gerceklesenToplam = gerceklesenToplam;
    }

    public Integer getGerceklesenAdet() {
        return gerceklesenAdet;
    }

    public void setGerceklesenAdet(Integer gerceklesenAdet) {
        this.gerceklesenAdet = gerceklesenAdet;
    }
}
