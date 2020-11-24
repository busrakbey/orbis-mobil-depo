package EntityLayer.IsletmePazarlama;

import java.math.BigDecimal;

public class Odenek {

    private Long id;
    private String urunAdi;
    private Long urunId;
    private BigDecimal kmiktar;
    private BigDecimal ktutar;
    private BigDecimal smiktar;
    private BigDecimal stutar;
    private BigDecimal ymiktar;
    private BigDecimal ytutar;
    private BigDecimal tmiktar;
    private BigDecimal ttutar;
    private BigDecimal imiktar;
    private BigDecimal itutar;
    private BigDecimal program;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public Long getUrunId() {
        return urunId;
    }

    public void setUrunId(Long urunId) {
        this.urunId = urunId;
    }

    public BigDecimal getKmiktar() {
        return kmiktar;
    }

    public void setKmiktar(BigDecimal kmiktar) {
        this.kmiktar = kmiktar;
    }

    public BigDecimal getKtutar() {
        return ktutar;
    }

    public void setKtutar(BigDecimal ktutar) {
        this.ktutar = ktutar;
    }

    public BigDecimal getSmiktar() {
        return smiktar;
    }

    public void setSmiktar(BigDecimal smiktar) {
        this.smiktar = smiktar;
    }

    public BigDecimal getStutar() {
        return stutar;
    }

    public void setStutar(BigDecimal stutar) {
        this.stutar = stutar;
    }

    public BigDecimal getYmiktar() {
        return ymiktar;
    }

    public void setYmiktar(BigDecimal ymiktar) {
        this.ymiktar = ymiktar;
    }

    public BigDecimal getYtutar() {
        return ytutar;
    }

    public void setYtutar(BigDecimal ytutar) {
        this.ytutar = ytutar;
    }

    public BigDecimal getTmiktar() {
        return tmiktar;
    }

    public void setTmiktar(BigDecimal tmiktar) {
        this.tmiktar = tmiktar;
    }

    public BigDecimal getTtutar() {
        return ttutar;
    }

    public void setTtutar(BigDecimal ttutar) {
        this.ttutar = ttutar;
    }

    public BigDecimal getImiktar() {
        return imiktar;
    }

    public void setImiktar(BigDecimal imiktar) {
        this.imiktar = imiktar;
    }

    public BigDecimal getItutar() {
        return itutar;
    }

    public void setItutar(BigDecimal itutar) {
        this.itutar = itutar;
    }

    public BigDecimal getProgram() {
        return program;
    }

    public void setProgram(BigDecimal program) {
        this.program = program;
    }
}
