package EntityLayer.DestekHizmetleri;

import java.math.BigDecimal;

public class MaliGelir {

    private String daireKodu;
    private BigDecimal simdikigelir;
    private BigDecimal simdikiGerceklesme;
    private BigDecimal simdikiOran;
    private String eskiDaireKodu;
    private BigDecimal eskigelir;
    private BigDecimal eskiGerceklesme;
    private BigDecimal eskiOran;

    public String getDaireKodu() {
        return daireKodu;
    }

    public void setDaireKodu(String daireKodu) {
        this.daireKodu = daireKodu;
    }

    public BigDecimal getSimdikigelir() {
        return simdikigelir;
    }

    public void setSimdikigelir(BigDecimal simdikigelir) {
        this.simdikigelir = simdikigelir;
    }

    public BigDecimal getSimdikiGerceklesme() {
        return simdikiGerceklesme;
    }

    public void setSimdikiGerceklesme(BigDecimal simdikiGerceklesme) {
        this.simdikiGerceklesme = simdikiGerceklesme;
    }

    public BigDecimal getSimdikiOran() {
        return simdikiOran;
    }

    public void setSimdikiOran(BigDecimal simdikiOran) {
        this.simdikiOran = simdikiOran;
    }

    public String getEskiDaireKodu() {
        return eskiDaireKodu;
    }

    public void setEskiDaireKodu(String eskiDaireKodu) {
        this.eskiDaireKodu = eskiDaireKodu;
    }

    public BigDecimal getEskigelir() {
        return eskigelir;
    }

    public void setEskigelir(BigDecimal eskigelir) {
        this.eskigelir = eskigelir;
    }

    public BigDecimal getEskiGerceklesme() {
        return eskiGerceklesme;
    }

    public void setEskiGerceklesme(BigDecimal eskiGerceklesme) {
        this.eskiGerceklesme = eskiGerceklesme;
    }

    public BigDecimal getEskiOran() {
        return eskiOran;
    }

    public void setEskiOran(BigDecimal eskiOran) {
        this.eskiOran = eskiOran;
    }
}
