package EntityLayer.DestekHizmetleri;

import java.math.BigDecimal;

public class PersonelGider {

     private Long id;
    private String harcamaTuru;
    private BigDecimal bakiye;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHarcamaTuru() {
        return harcamaTuru;
    }

    public void setHarcamaTuru(String harcamaTuru) {
        this.harcamaTuru = harcamaTuru;
    }

    public BigDecimal getBakiye() {
        return bakiye;
    }

    public void setBakiye(BigDecimal bakiye) {
        this.bakiye = bakiye;
    }
}
