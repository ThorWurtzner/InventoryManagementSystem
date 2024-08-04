package Gui.Panes;

import Application.Controller.Controller;
import Application.Model.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TidligereProducePane extends BorderPane {

    private final ListView<Whisky> whiskyer = new ListView<>();

    private final TextArea whiskyHistorieTextArea = new TextArea();

    public TidligereProducePane() {
        // Skub alting ind fra siderne
        this.setPadding(new Insets(40));

        // Title
        Label title = new Label("Tidligere produce");
        title.setId("paneTitle");

        whiskyer.getItems().addAll(Controller.getWhiskyer());
        whiskyer.setOnMouseClicked(event -> visWhiskyHistorie());

        whiskyHistorieTextArea.setEditable(false);

        setTop(title);
        setLeft(whiskyer);
        setCenter(whiskyHistorieTextArea);
    }

    /**
     * Viser historien for den valgte whisky ved at opbygge en tekststreng. Inkluderer oplysninger om whiskynavn, batchnummer,
     * cask strength-status og detaljer om destilleringer og fade, som whiskyen har været på. Hvis ingen whisky er valgt,
     * forbliver tekstområdet tomt.
     *
     * @implNote Den rekursive del af visningen af destillat på fade håndteres af metoden {@link #visDestillatPaaFad(List, StringBuilder, int)}.
     */
    private void visWhiskyHistorie() {
        if (whiskyer.getSelectionModel().getSelectedItem() != null) {
            Whisky selectedWhisky = whiskyer.getSelectionModel().getSelectedItem();

            List<WhiskyMængde> whiskyMængder = selectedWhisky.getWhiskyMængder();
            List<DestillatPaaFad> destillatPaaFade = selectedWhisky.getDestillatPaaFadList();

            Set<String> uniqueBatchNumbers = new HashSet<>();

            StringBuilder sb = new StringBuilder();
            sb.append("Navn: ").append(selectedWhisky.getNavn()).append("\n");
            sb.append("Batch nr: ").append(selectedWhisky.getBatchNr()).append("\n");
            sb.append(selectedWhisky.isCaskStrength() ? "Cask strength" : "").append("\n");

            // Den rekursive del sker her
            sb.append("Whiskyen har lagt på følgende fade:\n");
            visDestillatPaaFad(selectedWhisky.getDestillatPaaFadList(), sb, 0);
            // -------

            sb.append("\n\n");
            sb.append("Whiskyen består af følgende destilleringer:\n");

            for (int i = 0; i < destillatPaaFade.size(); i++) {
                List<DestillatMængde> destillatMængder = destillatPaaFade.get(i).getDestillatMængder();

                for (int j = 0; j < destillatMængder.size(); j++) {
                    Destillering destillering = destillatMængder.get(j).getDestillering();

                    String currentBatchNr = destillering.getBatchNr();

                    // Check if the batch number is unique
                    if (!uniqueBatchNumbers.contains(currentBatchNr)) {
                        sb.append("Batch nummer: '").append(currentBatchNr).append("\n");
                        sb.append("Start dato: ").append(destillering.getStartDato()).append("\n");
                        sb.append("Slut dato: ").append(destillering.getSlutDato()).append("\n");
                        sb.append("Maltbatch: '").append(destillering.getMaltbatch()).append("\n");
                        sb.append("Kornsort: ").append(destillering.getKornsort()).append("\n");
                        sb.append("Væskemængde: ").append(destillering.getVæskemængde()).append("\n");
                        sb.append("Alkoholprocent: ").append(destillering.getAlkoholProcent()).append("\n");
                        sb.append("Rygemateriale: '").append(destillering.getRygeMateriale()).append("\n");
                        sb.append("Medarbejder navn: '").append(destillering.getMedarbejderNavn()).append("\n");
                        sb.append("Kommentar: '").append(destillering.getKommentar()).append("\n");

                        sb.append("\n");
                        // Add the batch number to the set to mark it as processed
                        uniqueBatchNumbers.add(currentBatchNr);
                    }
                }
            }
            String whiskyHistorie = sb.toString();
            whiskyHistorieTextArea.setText(whiskyHistorie);
        }
    }

    /**
     * Viser information om destillat på et fad ved at opbygge en tekststreng. Gennemgår rekursivt alle tidligere destillater
     * for at inkludere deres informationer i teksten. Hver linje i teksten indeholder oplysninger om fadtype, fadnummer,
     * ophældningsdato/omhældningsdato afhængigt af alder, og niveauer for at angive hierarki af destillater.
     *
     * @param destillatPaaFade Listen af destillat på fad, som skal vises.
     * @param sb StringBuilder til opbygning af teksten.
     * @param niveau Niveau af hvor langt vi er inde i den rekursive gennemgang for at angive hierarki af destillater.
     *
     * @see DestillatPaaFad#getFad()
     * @see DestillatPaaFad#getOphældningsDato()
     */
    private void visDestillatPaaFad(List<DestillatPaaFad> destillatPaaFade, StringBuilder sb, int niveau) {
        for (DestillatPaaFad destillatPaaFad : destillatPaaFade) {
            sb.append("- Fadtype: ").append(destillatPaaFad.getFad().getFadtype())
                    .append(", Fad nr.: ").append(destillatPaaFad.getFad().getFadNr())
                    .append(destillatPaaFad.getOphældningsDato().isBefore(LocalDate.now().minusYears(3)) ? ", Ophældningsdato: " : ", Omhældningsdato: ").append(destillatPaaFad.getOphældningsDato())
                    .append("\n");

            // Rekursivt kald for at vise tidligere destillater
            visDestillatPaaFad(destillatPaaFad.getTidligereDestillater(), sb, niveau + 1);
        }
    }
}
