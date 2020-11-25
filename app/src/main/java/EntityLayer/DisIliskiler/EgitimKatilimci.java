package EntityLayer.DisIliskiler;

public class EgitimKatilimci {
    private Long birimId;
    private  String birimAdi;
    private  String egitimTanim;
    private  String katilimciAdi;


    public Long getBirimId() {
        return birimId;
    }

    public void setBirimId(Long birimId) {
        this.birimId = birimId;
    }

    public String getBirimAdi() {
        return birimAdi;
    }

    public void setBirimAdi(String birimAdi) {
        this.birimAdi = birimAdi;
    }

    public String getEgitimTanim() {
        return egitimTanim;
    }

    public void setEgitimTanim(String egitimTanim) {
        this.egitimTanim = egitimTanim;
    }

    public String getKatilimciAdi() {
        return katilimciAdi;
    }

    public void setKatilimciAdi(String katilimciAdi) {
        this.katilimciAdi = katilimciAdi;
    }
}
