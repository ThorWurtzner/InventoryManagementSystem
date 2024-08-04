package Gui.Helpers;

import javafx.scene.control.Alert;


/**
 * Klassen indeholder metoder til at oprette og vise forskellige typer af JavaFX Alert-dialoger
 * med standardiserede meddelelser og overskrifter for bekræftelse, manglende information og indtastningsfejl.
 * Disse metoder forenkler processen med at oprette og præsentere beskeder til brugeren i brugergrænsefladen.
 *
 */

public class AlertTemplates {

    /**
     * Viser en bekræftelsesdialog med angivet tekst.
     *
     * @param tekst Besked, der skal vises i bekræftelsesdialogen.
     */
    public static void bekræftelsesAlert(String tekst) {
        Alert bekræftelseAlert = new Alert(Alert.AlertType.INFORMATION);
        bekræftelseAlert.setHeaderText("Bekræftelse");
        bekræftelseAlert.setContentText(tekst);
        bekræftelseAlert.show();
    }

    /**
     * Viser en fejldialog for manglende information med angivet tekst.
     *
     * @param tekst Besked, der skal vises i fejldialogen.
     */
    public static void mangelAlert(String tekst) {
        Alert mangelAlert = new Alert(Alert.AlertType.ERROR);
        mangelAlert.setHeaderText("Der mangler noget..");
        mangelAlert.setContentText(tekst);
        mangelAlert.show();
    }

    /**
     * Viser en fejldialog for forkerte informationer med angivet tekst.
     *
     * @param tekst Besked, der skal vises i fejldialogen.
     */
    public static void forkertAlert(String tekst) {
        Alert fejlAlert = new Alert(Alert.AlertType.ERROR);
        fejlAlert.setHeaderText("Fejl i indtastede informationer");
        fejlAlert.setContentText(tekst);
        fejlAlert.show();
    }
}
