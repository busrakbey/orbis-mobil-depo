package EntityLayer.OrmanIdaresi;

import java.math.BigDecimal;

public class OrmanIdaresi {
    private  Long id;
    private BigDecimal ormanlikAlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOrmanlikAlan() {
        return ormanlikAlan;
    }

    public void setOrmanlikAlan(BigDecimal ormanlikAlan) {
        this.ormanlikAlan = ormanlikAlan;
    }
}
