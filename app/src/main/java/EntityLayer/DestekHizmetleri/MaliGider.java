package EntityLayer.DestekHizmetleri;

import java.math.BigDecimal;

public class MaliGider {
    private String daireKodu;
    private BigDecimal butce;
    private BigDecimal sarfiyat;
    private BigDecimal oran;
    private String eskiDaireKodu;
    private BigDecimal eskiButce;
    private BigDecimal eskiSarfiyat;
    private BigDecimal eskiOran;

    public String getDaireKodu() {
        return daireKodu;
    }

    public void setDaireKodu(String daireKodu) {
        this.daireKodu = daireKodu;
    }

    public BigDecimal getButce() {
        return butce;
    }

    public void setButce(BigDecimal butce) {
        this.butce = butce;
    }

    public BigDecimal getSarfiyat() {
        return sarfiyat;
    }

    public void setSarfiyat(BigDecimal sarfiyat) {
        this.sarfiyat = sarfiyat;
    }

    public BigDecimal getOran() {
        return oran;
    }

    public void setOran(BigDecimal oran) {
        this.oran = oran;
    }

    public String getEskiDaireKodu() {
        return eskiDaireKodu;
    }

    public void setEskiDaireKodu(String eskiDaireKodu) {
        this.eskiDaireKodu = eskiDaireKodu;
    }

    public BigDecimal getEskiButce() {
        return eskiButce;
    }

    public void setEskiButce(BigDecimal eskiButce) {
        this.eskiButce = eskiButce;
    }

    public BigDecimal getEskiSarfiyat() {
        return eskiSarfiyat;
    }

    public void setEskiSarfiyat(BigDecimal eskiSarfiyat) {
        this.eskiSarfiyat = eskiSarfiyat;
    }

    public BigDecimal getEskiOran() {
        return eskiOran;
    }

    public void setEskiOran(BigDecimal eskiOran) {
        this.eskiOran = eskiOran;
    }
}
