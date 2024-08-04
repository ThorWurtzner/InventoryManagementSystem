package Application.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DestillatPaaFad {
    private LocalDate ophældningsDato;
    private Fad fad;
    private List<DestillatMængde> destillatMængder = new ArrayList<>();

    private List<DestillatPaaFad> tidligereDestillater = new ArrayList<>();

    public DestillatPaaFad(LocalDate ophældningsDato, Fad fad) {
        this.ophældningsDato = ophældningsDato;
        this.fad = fad;
    }

    public DestillatMængde createDestillatMængde(double litermængde,Destillering destillering, LocalDate tilførselsdato){
        DestillatMængde dm = new DestillatMængde(litermængde,destillering, tilførselsdato);
        destillatMængder.add(dm);
        return dm;
    }

    public List<DestillatPaaFad> getTidligereDestillater() {
        return new ArrayList<>(tidligereDestillater);
    }

    public void addTidligereDestillering(DestillatPaaFad destillatPaaFad) {
        tidligereDestillater.add(destillatPaaFad);
    }

    public LocalDate getOphældningsDato() {
        return ophældningsDato;
    }

    public Fad getFad() {
        return fad;
    }

    // Returnerer den faktiske liste, så den kan ændres under omhældning
    public List<DestillatMængde> getDestillatMængder() {
        return this.destillatMængder;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = ophældningsDato.format(formatter);

        return "Ophældning: " + formattedDate + "\n\n" + fad + "\n\nDestillatmængder:" + destillatMængder + "\n";
    }
}
