package EntityLayer.DestekHizmetleri;

import java.math.BigDecimal;

public class MaliHesap {

    private String hesapAdi;
    private Long tutar;
    private String yili;
    private String hesapAdiEski;
    private BigDecimal tutarEski;
    private BigDecimal yiliEski;

    public String getHesapAdi() {
        return hesapAdi;
    }

    public void setHesapAdi(String hesapAdi) {
        this.hesapAdi = hesapAdi;
    }

    public Long getTutar() {
        return tutar;
    }

    public void setTutar(Long tutar) {
        this.tutar = tutar;
    }

    public String getYili() {
        return yili;
    }

    public void setYili(String yili) {
        this.yili = yili;
    }

    public String getHesapAdiEski() {
        return hesapAdiEski;
    }

    public void setHesapAdiEski(String hesapAdiEski) {
        this.hesapAdiEski = hesapAdiEski;
    }

    public BigDecimal getTutarEski() {
        return tutarEski;
    }

    public void setTutarEski(BigDecimal tutarEski) {
        this.tutarEski = tutarEski;
    }

    public BigDecimal getYiliEski() {
        return yiliEski;
    }

    public void setYiliEski(BigDecimal yiliEski) {
        this.yiliEski = yiliEski;
    }
}
