package Storage;

import Application.Model.*;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    // ----

    private static List<Destillering> destilleringer = new ArrayList<>();

    public static ArrayList<Destillering> getdestilleringer(){return new ArrayList<>(destilleringer);}

    public  static void addDestillering(Destillering destillering){destilleringer.add(destillering);}

    // ----

    private static List<Fad> fade = new ArrayList<>();

    public static ArrayList<Fad> getFade(){return new ArrayList<>(fade);}

    public  static void addFad(Fad fad){fade.add(fad);}

    // ----

    private static List<DestillatPaaFad> destillaterPaaFade = new ArrayList<>();

    public static ArrayList<DestillatPaaFad> getDestillaterPaaFade (){return new ArrayList<>(destillaterPaaFade );}

    public  static void addDestillatPaaFad (DestillatPaaFad destillatPaaFad){destillaterPaaFade.add(destillatPaaFad);}

    // ----

    private static List<Lager> lagere = new ArrayList<>();

    public static ArrayList<Lager> getLagere (){return new ArrayList<>(lagere);}

    public  static void addlager (Lager lager){lagere.add(lager);}

    // ----

    private static List<Whisky> whiskyer = new ArrayList<Whisky>();

    public static ArrayList<Whisky> getWhiskyer() {
        return new ArrayList<>(whiskyer);
    }

    public static void addWhisky(Whisky whisky) {
        whiskyer.add(whisky);
    }

}
