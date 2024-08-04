package Application.Model;

import java.util.ArrayList;
import java.util.List;

public class Lager {
    private String lagerNavn;
    private List<Fad> fade = new ArrayList<Fad>();

    public Lager(String lagerNavn) {
        this.lagerNavn = lagerNavn;
    }

    public String getLagerNavn() {
        return lagerNavn;
    }

    public void setLagerNavn(String lagerNavn) {
        this.lagerNavn = lagerNavn;
    }
    public void addFad(Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
            fad.setLager(this);
        }
    }
    public void removeFad(Fad fad) {
        if (fade.contains(fad)) {
            fade.remove(fad);
            fad.setLager(null);
        }
    }

    public List<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    @Override
    public String toString() {
        return lagerNavn;
    }
}
