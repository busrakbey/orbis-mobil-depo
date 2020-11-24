package EntityLayer.IsletmePazarlama;

public class Damga {
    private Long id;
    private Double toplam;
    private Double uretimeVerilen;
    private Double dikili;
    private Double toplamProgram;
    private Double dikiliProgram;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getToplam() {
        return toplam;
    }

    public void setToplam(Double toplam) {
        this.toplam = toplam;
    }

    public Double getUretimeVerilen() {
        return uretimeVerilen;
    }

    public void setUretimeVerilen(Double uretimeVerilen) {
        this.uretimeVerilen = uretimeVerilen;
    }

    public Double getDikili() {
        return dikili;
    }

    public void setDikili(Double dikili) {
        this.dikili = dikili;
    }

    public Double getToplamProgram() {
        return toplamProgram;
    }

    public void setToplamProgram(Double toplamProgram) {
        this.toplamProgram = toplamProgram;
    }

    public Double getDikiliProgram() {
        return dikiliProgram;
    }

    public void setDikiliProgram(Double dikiliProgram) {
        this.dikiliProgram = dikiliProgram;
    }
}
