package Application.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DestillatMængde {
    private double literMængde;
    private Destillering destillering;

    private LocalDate tilførselsdato;

    public DestillatMængde(double literMængde, Destillering destillering, LocalDate tilførselsdatoen) {
        this.literMængde = literMængde;
        this.destillering = destillering;
        this.tilførselsdato = tilførselsdatoen;
    }

    public double getLiterMængde() {
        return literMængde;
    }

    public Destillering getDestillering() {
        return destillering;
    }

    public LocalDate getTilførselsdato() {
        return tilførselsdato;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return
                literMængde + " liter fra " + destillering.getBatchNr() + " tilføjet d. " + tilførselsdato.format(formatter);

    }
}
