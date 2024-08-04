package Application.Model;

public class Fad {
    private String fadNr;
    private String leverandør;
    private String fadtype;
    private int antalFyldninger;
    private int literStørrelse;
    private DestillatPaaFad destillatPaaFad;
    private Lager lager;

    public Fad(String fadNr, String leverandør, String fadtype, int antalFyldninger, int literStørrelse) {
        this.fadNr = fadNr;
        this.leverandør = leverandør;
        this.fadtype = fadtype;
        this.antalFyldninger = antalFyldninger;
        this.literStørrelse = literStørrelse;
    }

    public String getFadNr() {
        return fadNr;
    }

    public String getLeverandør() {
        return leverandør;
    }

    public String getFadtype() {
        return fadtype;
    }

    public int getAntalFyldninger() {
        return antalFyldninger;
    }

    public int getLiterStørrelse() {
        return literStørrelse;
    }

    public Lager getLager() {
        return lager;
    }

    public void setDestillatPaaFad(DestillatPaaFad destillatPaaFad) {
        this.destillatPaaFad = destillatPaaFad;
    }

    public void setLager(Lager lager) {
        if (this.lager != lager) {
            if (this.lager != null) {
                this.lager.removeFad(this);
            }
            this.lager = lager;
            if (lager != null) {
                lager.addFad(this);
            }
        }

        this.lager = lager;
    }

    public void fjernFraLager() {
        this.lager = null;
    }

    public DestillatPaaFad getDestillatPaaFad() {
        return destillatPaaFad;
    }

    public void removeDestillatPaaFad() {
        this.destillatPaaFad = null;
    }

    @Override
    public String toString() {
        if (destillatPaaFad == null) {
            return
            "FadNr: '" + fadNr + '\'' +
            "\nLeverandør: " + leverandør +
            "\nFadtype: " + fadtype +
            "\nAntal fyldninger: " + antalFyldninger +
            "\nLiter-størrelse: " + literStørrelse;
        } else {
            return
            "FadNr: '" + fadNr + '\'' +
            "\nLeverandør: " + leverandør +
            "\nFadtype: " + fadtype +
            "\nAntal fyldninger: " + antalFyldninger +
            "\nLiter-størrelse: " + literStørrelse +
            "\n" + destillatPaaFad.getDestillatMængder() +
            "\n Lager: " + lager.getLagerNavn();
        }
    }
}
