package EntityLayer.Ortak;

import AnnotationLayer.Column;
import AnnotationLayer.Table;

/**
 * Created by isahin on 23.3.2017.
 */

@Table(name = "S_IL")
public class SCity extends BaseEntity{

    @Override
    public String toString()
    {
        return  adi;
    }

    @Column(name = "id", nullable = false)
    public Long id = null;

    @Column(name = "adi", nullable = false)
    public String adi = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }
}
