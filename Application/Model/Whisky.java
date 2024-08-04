package Application.Model;

import java.util.ArrayList;
import java.util.List;

public class Whisky {
    private int batchNr;
    private String navn;
    private boolean caskStrength;
    private List<DestillatPaaFad> destillatPaaFadList = new ArrayList<>();
    private List<WhiskyMængde> whiskyMængder = new ArrayList<>();


    public int getBatchNr() {
        return batchNr;
    }

    public String getNavn() {
        return navn;
    }

    public boolean isCaskStrength() {
        return caskStrength;
    }

    public List<DestillatPaaFad> getDestillatPaaFadList() {
        return new ArrayList<>(destillatPaaFadList);
    }

    public List<WhiskyMængde> getWhiskyMængder() {
        return new ArrayList<>(whiskyMængder);
    }

    public Whisky(int batchNr, String navn, boolean caskStrength, List<WhiskyMængde> whiskyMængder) {
        this.batchNr = batchNr;
        this.navn = navn;
        this.caskStrength = caskStrength;
        this.whiskyMængder = whiskyMængder;
        for (int i = 0; i < whiskyMængder.size(); i++) {
            DestillatPaaFad dpf = whiskyMængder.get(i).getFad().getDestillatPaaFad();
            this.destillatPaaFadList.add(dpf);
        }
    }

    @Override
    public String toString() {
        return "Batch " + batchNr + ": " + navn;
    }
}
