package EntityLayer.IsletmePazarlama;

import java.math.BigDecimal;

public class Damga {
    private Long id;
    private BigDecimal toplam;
    private BigDecimal uretimeVerilen;
    private BigDecimal dikili;
    private BigDecimal toplamProgram;
    private BigDecimal dikiliProgram;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getToplam() {
        return toplam;
    }

    public void setToplam(BigDecimal toplam) {
        this.toplam = toplam;
    }

    public BigDecimal getUretimeVerilen() {
        return uretimeVerilen;
    }

    public void setUretimeVerilen(BigDecimal uretimeVerilen) {
        this.uretimeVerilen = uretimeVerilen;
    }

    public BigDecimal getDikili() {
        return dikili;
    }

    public void setDikili(BigDecimal dikili) {
        this.dikili = dikili;
    }

    public BigDecimal getToplamProgram() {
        return toplamProgram;
    }

    public void setToplamProgram(BigDecimal toplamProgram) {
        this.toplamProgram = toplamProgram;
    }

    public BigDecimal getDikiliProgram() {
        return dikiliProgram;
    }

    public void setDikiliProgram(BigDecimal dikiliProgram) {
        this.dikiliProgram = dikiliProgram;
    }
}
