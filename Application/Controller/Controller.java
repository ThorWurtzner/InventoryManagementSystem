package Application.Controller;

import Application.Model.*;
import Storage.Storage;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    //------------------------------------------------------------------------------------------
    //opret kald

    public static Destillering opretDestilleringStart(String batchNr, LocalDate startDato) {
        Destillering destillering = new Destillering(batchNr, startDato);
        Storage.addDestillering(destillering);
        return destillering;
    }

    public static Fad opretFad(String fadNr, String leverandør, String fadtype, int antalFyldninger, int literStørrelse) {
        Fad fad = new Fad(fadNr, leverandør, fadtype, antalFyldninger, literStørrelse);
        Storage.addFad(fad);
        return fad;
    }

    public static DestillatPaaFad opretDestillatPaaFad(LocalDate ophældningsDato, Fad fad) {
        DestillatPaaFad dpf = new DestillatPaaFad(ophældningsDato, fad);
        fad.setDestillatPaaFad(dpf);
        Storage.addDestillatPaaFad(dpf);
        return dpf;
    }

    public static Lager opretLager(String lagernavn) {
        Lager lager = new Lager(lagernavn);
        Storage.addlager(lager);
        return lager;
    }

    public static Whisky opretWhisky(int batchNr, String navn, boolean caskStrength, List<WhiskyMængde> whiskyMængder) {
        Whisky whisky = new Whisky(batchNr, navn, caskStrength, whiskyMængder);
        Storage.addWhisky(whisky);
        return whisky;
    }

    // Hjælpe metoder -------------------------------------------------

    /**
     * Tjekker om et fad er klar til at blive whisky baseret på tidspunktet for tilførsel af destillat.
     * Et fad anses for at være klar, hvis den seneste tilførsel af destillatMængde til destillatet er sket for mere end tre år siden.
     *
     * @param fad          Fadet, der skal undersøges.
     * @param treAarSiden  Datoen, der repræsenterer tre år tilbage fra nu.
     * @return True, hvis fadet er klar til whisky; ellers false.
     *
     * @see Fad#getDestillatPaaFad()
     * @see DestillatMængde#getTilførselsdato()
     */
    public static boolean isWhiskyFad(Fad fad, LocalDate treAarSiden) {
        DestillatPaaFad dpf = fad.getDestillatPaaFad();
        for (DestillatMængde dm : dpf.getDestillatMængder()) {
            if (treAarSiden.isBefore(dm.getTilførselsdato())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Udfører en omhældning af destillat fra valgte fyldte fade til et bestemt tomt fad, registrerer omhældningen i systemet
     * og opdaterer grænsefladen med de nye oplysninger.
     *
     * @param tomtFad          Det tomme fad, hvor destillatet skal hældes til.
     * @param selectedFyldtFade Listen af valgte fyldte fade, hvor destillatet skal hentes fra.
     * @param lager            Det lager, hvor det tomme fad skal placeres.
     * @param omhældningsDato  Datoen for omhældningen.
     * @return DestillatPaaFad, der repræsenterer det nye destillat på det tomme fad.
     *
     * @see #opretDestillatPaaFad(LocalDate, Fad)
     * @see DestillatPaaFad#addTidligereDestillering(DestillatPaaFad)
     * @see Fad#removeDestillatPaaFad()
     */
    public static DestillatPaaFad lavOmhældning(Fad tomtFad, ArrayList<Fad> selectedFyldtFade, Lager lager, LocalDate omhældningsDato) {

        // Sæt nyt destillat på fadet
        tomtFad.setLager(lager);
        DestillatPaaFad nyeDestillatPaaFad = opretDestillatPaaFad(omhældningsDato, tomtFad);

        //gemmer de tidliger destillat på fade på nyt destillat på fad og flytter tidligere destillatMænger over på den nyeDestillatPåFad
        for (int i = 0; i < selectedFyldtFade.size(); i++) {
            nyeDestillatPaaFad.addTidligereDestillering(selectedFyldtFade.get(i).getDestillatPaaFad());
            nyeDestillatPaaFad.getDestillatMængder().addAll(selectedFyldtFade.get(i).getDestillatPaaFad().getDestillatMængder());
            selectedFyldtFade.get(i).fjernFraLager();
            selectedFyldtFade.get(i).removeDestillatPaaFad();
        }
        return nyeDestillatPaaFad;
    }

    //------------------------------------------------------------------------------------------
    //storage get kald

    /**
     * Returnerer en liste af fade, der er klar til whisky produktion baseret på tilførselstidspunkt af dens destillater. Fade, hvor den
     * seneste tilførsel af destillatMængde er sket for mere end tre år siden, anses for at indeholde whisky.
     *
     * @return En liste af fade, der er klar til at blive whisky.
     *
     * @see Storage#getFade()
     * @see #isWhiskyFad(Fad, LocalDate)
     */
    public static ArrayList<Fad> getWhiskyFade() {
        ArrayList<Fad> fade = Storage.getFade();
        ArrayList<Fad> whiskyFade = new ArrayList<>();

        LocalDate treAarSiden = LocalDate.now().minusYears(3);

        for (Fad fad : fade) {
            if (fad.getDestillatPaaFad() != null) {
                if (isWhiskyFad(fad, treAarSiden)) {
                    whiskyFade.add(fad);
                }
            }
        }

        return whiskyFade;
    }

    public static ArrayList<Destillering> getDestillinger() {

        return Storage.getdestilleringer();
    }

    public static ArrayList<Fad> getFade() {
        return Storage.getFade();
    }

    public static ArrayList<Lager> getLagere() {
        return Storage.getLagere();
    }

    public static ArrayList<Whisky> getWhiskyer() {
        return Storage.getWhiskyer();
    }

    //------------------------------------------------------------------------------------------
    //init
    public static void initContent() {
        Destillering destillering1 = opretDestilleringStart("nw001", LocalDate.of(2019, 11, 18));
        destillering1.setKornsort("Byg fra moesvang");
        destillering1.setVæskemængde(100);
        destillering1.setAlkoholProcent(40);
        destillering1.setMaltbatch("mthy78");
        destillering1.setRygeMateriale("Tørv fra Holmstrup");
        destillering1.setMedarbejderNavn("MKR");
        destillering1.setSlutDato(LocalDate.of(2019, 11, 23));
        destillering1.setKommentar("Nyt rygematerial prøvet");

        Destillering destillering2 = opretDestilleringStart("nw002", LocalDate.of(2021, 11, 18));
        destillering2.setKornsort("Byg fra Kongsvang");
        destillering2.setVæskemængde(100);
        destillering2.setAlkoholProcent(40);
        destillering2.setMaltbatch("mthy79");
        destillering2.setRygeMateriale("Tang fra Islay");
        destillering2.setMedarbejderNavn("THW");
        destillering2.setSlutDato(LocalDate.of(2021, 11, 23));
        destillering2.setKommentar("Temperatur for høj");

        Fad fad1 = opretFad("001", "el Bødker", "sherry", 1, 200);
        Fad fad2 = opretFad("002", "el Bødker", "sherry", 1, 200);
        Fad fad3 = opretFad("003", "Bødker huset", "bourbon", 2, 300);
        Fad fad4 = opretFad("004", "Bødker huset", "bourbon", 1, 50);

        DestillatPaaFad destillatPaaFad = opretDestillatPaaFad(LocalDate.of(2019, 11, 19), fad1);
        destillatPaaFad.createDestillatMængde(30, destillering1, LocalDate.of(2019, 11, 19));

        DestillatPaaFad destillatPaaFad2 = opretDestillatPaaFad(LocalDate.of(2019, 11, 19), fad2);
        destillatPaaFad2.createDestillatMængde(30, destillering2, LocalDate.of(2019, 11, 19));

        DestillatPaaFad destillatPaaFad3 = opretDestillatPaaFad(LocalDate.of(2021, 11, 19), fad3);
        destillatPaaFad3.createDestillatMængde(20, destillering2, LocalDate.of(2021, 11, 19));

        DestillatPaaFad destillatPaaFad4 = opretDestillatPaaFad(LocalDate.of(2019, 11, 19), fad3);
        destillatPaaFad4.createDestillatMængde(40, destillering1, LocalDate.of(2019, 11, 19));
        destillatPaaFad4.createDestillatMængde(20, destillering2, LocalDate.of(2021, 11, 19));

        Lager lager1 = opretLager("Containeren");
        Lager lager2 = opretLager("Lars' lade");

        lager1.addFad(fad1);
        lager2.addFad(fad2);
        lager2.addFad(fad3);

        // Dummy dataens whisky batch er 0 - dette er med vilje.
        List<WhiskyMængde> tempMængder = new ArrayList<>();
        tempMængder.add(new WhiskyMængde(10, fad1));
        Whisky whisky = opretWhisky(0, "Thor's Special Reserve", true, tempMængder);

    }

}
