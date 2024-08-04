package Application.Model;

import java.time.LocalDate;

public class Destillering {
    private String batchNr;
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltbatch;
    private String kornsort;
    private String medarbejderNavn;
    private double væskemængde;
    private double alkoholProcent;
    private String rygeMateriale;
    private String kommentar;


    public Destillering(String batchNr, LocalDate startDato) {
        this.batchNr = batchNr;
        this.startDato = startDato;
    }

    public String getBatchNr() {
        return batchNr;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public String getMaltbatch() {
        return maltbatch;
    }

    public String getKornsort() {
        return kornsort;
    }

    public String getMedarbejderNavn() {
        return medarbejderNavn;
    }

    public double getVæskemængde() {
        return væskemængde;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public String getRygeMateriale() {
        return rygeMateriale;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public void setMaltbatch(String maltbatch) {
        this.maltbatch = maltbatch;
    }

    public void setKornsort(String kornsort) {
        this.kornsort = kornsort;
    }

    public void setMedarbejderNavn(String medarbejderNavn) {
        this.medarbejderNavn = medarbejderNavn;
    }

    public void setVæskemængde(double væskemængde) {
        this.væskemængde = væskemængde;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public void setRygeMateriale(String rygeMateriale) {
        this.rygeMateriale = rygeMateriale;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    @Override
    public String toString() {
        return
        "Batch nummer: '" + batchNr + '\'' +
        "\nStart dato: " + startDato +
        "\nSlut dato: " + slutDato +
        "\nMaltbatch: '" + maltbatch + '\'' +
        "\nKornsort: '" + kornsort + '\'' +
        "\nVæskemængde: " + væskemængde +
        "\nAlkoholprocent: " + alkoholProcent +
        "\nRygemateriale: '" + rygeMateriale + '\'' +
        "\nMedarbejder navn: '" + medarbejderNavn + '\'' +
        "\nKommentar: '" + kommentar + '\'';
    }
}
