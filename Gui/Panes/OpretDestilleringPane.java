package Gui.Panes;

import Application.Controller.Controller;
import Application.Model.Destillering;
import Gui.Helpers.AlertTemplates;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class OpretDestilleringPane extends BorderPane {

    // Globale variabler, så de er tilgængelige fra metoder i denne klasse
    private final TextField batchNr = new TextField();
    private final DatePicker startDato = new DatePicker();
    private final DatePicker slutDato = new DatePicker();
    private final TextField maltbatch = new TextField();
    private final TextField kornsort = new TextField();
    private final TextField væskemængde = new TextField();
    private final TextField alkoholProcent = new TextField();
    private final TextField rygeMateriale = new TextField();
    private final TextField medarbejderNavn = new TextField();
    private final TextField kommentar = new TextField();

    private final ListView destilleringer = new ListView<>();

    public OpretDestilleringPane() {
        // Skub alting ind fra siderne
        this.setPadding(new Insets(40));

        // Title
        Label title = new Label("Opret destillering");
        title.setId("paneTitle");

        // Inputs
        Label batchNrLabel = new Label("Batch Nr:");
        batchNrLabel.getStyleClass().add("inputLabel");
        batchNr.setPromptText("Indtast batch Nr...");
        HBox batchNrBox = new HBox(10);
        batchNrBox.setAlignment(Pos.CENTER_LEFT);
        batchNrBox.getChildren().addAll(batchNrLabel, batchNr);

        Label startDatoLabel = new Label("Start Dato:");
        startDatoLabel.getStyleClass().add("inputLabel");
        startDato.setPromptText("Vælg startdato...");
        startDato.setEditable(false);
        HBox startDatoBox = new HBox(10);
        startDatoBox.setAlignment(Pos.CENTER_LEFT);
        startDatoBox.getChildren().addAll(startDatoLabel, startDato);

        Label slutDatoLabel = new Label("Slut Dato:");
        slutDatoLabel.getStyleClass().add("inputLabel");
        slutDato.setPromptText("Vælg slutdato...");
        slutDato.setEditable(false);
        HBox slutDatoBox = new HBox(10);
        slutDatoBox.setAlignment(Pos.CENTER_LEFT);
        slutDatoBox.getChildren().addAll(slutDatoLabel, slutDato);

        Label maltbatchLabel = new Label("Maltbatch:");
        maltbatchLabel.getStyleClass().add("inputLabel");
        maltbatch.setPromptText("Indtast maltbatch...");
        HBox maltbatchBox = new HBox(10);
        maltbatchBox.setAlignment(Pos.CENTER_LEFT);
        maltbatchBox.getChildren().addAll(maltbatchLabel, maltbatch);

        Label kornsortLabel = new Label("Kornsort:");
        kornsortLabel.getStyleClass().add("inputLabel");
        kornsort.setPromptText("Indtast kornsort...");
        HBox kornsortBox = new HBox(10);
        kornsortBox.setAlignment(Pos.CENTER_LEFT);
        kornsortBox.getChildren().addAll(kornsortLabel, kornsort);

        Label væskemængdeLabel = new Label("Væskemængde:");
        væskemængdeLabel.getStyleClass().add("inputLabel");
        væskemængde.setPromptText("Indtast væskemængde...");
        HBox væskemængdeBox = new HBox(10);
        væskemængdeBox.setAlignment(Pos.CENTER_LEFT);
        væskemængdeBox.getChildren().addAll(væskemængdeLabel, væskemængde);

        Label alkoholProcentLabel = new Label("Alkohol Procent:");
        alkoholProcentLabel.getStyleClass().add("inputLabel");
        alkoholProcent.setPromptText("Indtast alkoholprocent...");
        HBox alkoholProcentBox = new HBox(10);
        alkoholProcentBox.setAlignment(Pos.CENTER_LEFT);
        alkoholProcentBox.getChildren().addAll(alkoholProcentLabel, alkoholProcent);

        Label rygeMaterialeLabel = new Label("Rygemateriale:");
        rygeMaterialeLabel.getStyleClass().add("inputLabel");
        rygeMateriale.setPromptText("Indtast rygemateriale...");
        HBox rygeMaterialeBox = new HBox(10);
        rygeMaterialeBox.setAlignment(Pos.CENTER_LEFT);
        rygeMaterialeBox.getChildren().addAll(rygeMaterialeLabel, rygeMateriale);

        Label medarbejderNavnLabel = new Label("Medarbejder:");
        medarbejderNavnLabel.getStyleClass().add("inputLabel");
        medarbejderNavn.setPromptText("Indtast navn på medarbejder...");
        HBox medarbejderNavnBox = new HBox(10);
        medarbejderNavnBox.setAlignment(Pos.CENTER_LEFT);
        medarbejderNavnBox.getChildren().addAll(medarbejderNavnLabel, medarbejderNavn);

        Label kommentarLabel = new Label("Kommentar:");
        kommentarLabel.getStyleClass().add("inputLabel");
        kommentar.setPromptText("Indtast kommentar...");
        HBox kommentarBox = new HBox(10);
        kommentarBox.setAlignment(Pos.CENTER_LEFT);
        kommentarBox.getChildren().addAll(kommentarLabel, kommentar);

        // Oprettelse
        Button btnOpretDestillering = new Button("Opret destillering");
        btnOpretDestillering.setOnAction(event -> opretDestillering());

        // Opdatering
        Button btnOpdaterDestillering = new Button("Gem opdatering");
        btnOpdaterDestillering.setOnAction(event -> opdaterDestillering());

        destilleringer.setOnMouseClicked(event -> visDestillering());


        // Indsættelse
        VBox inputBox = new VBox(20);
        inputBox.getChildren().addAll(
                batchNrBox, startDatoBox, slutDatoBox,
                maltbatchBox, kornsortBox,
                væskemængdeBox, alkoholProcentBox, rygeMaterialeBox,
                medarbejderNavnBox, kommentarBox,
                btnOpretDestillering
        );

        VBox listBox = new VBox(5);
        listBox.getChildren().addAll(destilleringer, btnOpdaterDestillering);
        destilleringer.getItems().addAll(Controller.getDestillinger());

        this.setTop(title);
        this.setLeft(inputBox);
        this.setRight(listBox);

        // Styling
        destilleringer.getStyleClass().add("list-view-template");
        btnOpdaterDestillering.getStyleClass().add("opretBtn");
        btnOpretDestillering.getStyleClass().add("opretBtn");
    }

    /**
     * Viser detaljerne for den valgte destillering i brugergrænsefladen. Hvis der er valgt en destillering fra listen,
     * opdateres de relevante tekstfelter med informationerne for den valgte destillering. Desuden deaktiveres batchNr og
     * startDato, så de ikke kan ændres.
     *
     * @implNote Hvis destillering er null, sker der ikke noget. Dette er gjort for at fikse et tilfældigt bug.
     *
     * @see Destillering Destillering klassen for information om de attributter, der vises.
     */
    private void visDestillering() {
        if (destilleringer.getSelectionModel().getSelectedItem() != null) {
            Destillering destillering = (Destillering) destilleringer.getSelectionModel().getSelectedItem();

            batchNr.setDisable(true);
            startDato.setDisable(true);

            batchNr.setText(destillering.getBatchNr());
            if (destillering.getStartDato() != null) {
                startDato.setPromptText(destillering.getStartDato().toString());
            }
            if (destillering.getSlutDato() != null) {
                slutDato.setPromptText(destillering.getSlutDato().toString());
            }
            maltbatch.setText(destillering.getMaltbatch());
            kornsort.setText(destillering.getKornsort());
            væskemængde.setText(Double.toString(destillering.getVæskemængde()));
            alkoholProcent.setText(Double.toString(destillering.getAlkoholProcent()));
            rygeMateriale.setText(destillering.getRygeMateriale());
            medarbejderNavn.setText(destillering.getMedarbejderNavn());
            kommentar.setText(destillering.getKommentar());
        }
    }

    /**
     * Opdaterer attributterne for den valgte destillering baseret på de indtastede værdier i brugergrænsefladen. Hvis ingen
     * destillering er valgt, vises en fejlmeddelelse. Efter opdatering af destilleringen, nulstilles tekstfelterne og
     * opdateres destilleringer-listen.
     *
     * @implNote Hvis en attribut ikke er indtastet, forbliver den uændret.
     *
     * @see AlertTemplates#mangelAlert(String) AlertTemplates.mangelAlert for visning af fejlmeddelelse ved manglende valg.
     */
    private void opdaterDestillering() {
        // Hvis der ikke er valgt en destillering, skal vi forlade metoden.
        if (destilleringer.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Destillering selectedDestillering = (Destillering) destilleringer.getSelectionModel().getSelectedItem();

        if (selectedDestillering == null) {
            AlertTemplates.mangelAlert("Vælg venligst en destillering fra listen");
            return;
        }

        if (slutDato.getValue() != null) {
            selectedDestillering.setSlutDato(slutDato.getValue());
        }

        if (maltbatch.getText() != "") {
            selectedDestillering.setMaltbatch(maltbatch.getText());
        }

        if (kornsort.getText() != "") {
            selectedDestillering.setKornsort(kornsort.getText());
        }

        if (væskemængde.getText() != "") {
            selectedDestillering.setVæskemængde(Double.parseDouble(væskemængde.getText()));
        }

        if (alkoholProcent.getText() != "") {
            selectedDestillering.setAlkoholProcent(Double.parseDouble(alkoholProcent.getText()));
        }

        if (rygeMateriale.getText() != "") {
            selectedDestillering.setRygeMateriale(rygeMateriale.getText());
        }

        if (medarbejderNavn.getText() != "") {
            selectedDestillering.setMedarbejderNavn(medarbejderNavn.getText());
        }

        if (kommentar.getText() != "") {
            selectedDestillering.setKommentar(kommentar.getText());
        }

        resetFelter();

        destilleringer.getItems().clear();
        destilleringer.getItems().addAll(Controller.getDestillinger());
    }


    /**
     * Opretter en ny destillering baseret på de indtastede oplysninger i brugergrænsefladen. Hvis påkrævede oplysninger
     * som batchNr og startDato ikke er angivet, vises en fejlmeddelelse. Hvis startDato ikke er en gyldig dato, vises også en
     * fejlmeddelelse. Efter oprettelse af destilleringen, nulstilles tekstfelterne og opdateres destilleringer-listen.
     *
     * @implNote Hvis en attribut ikke er indtastet, forbliver den uændret.
     *
     * @see Controller#opretDestilleringStart(String, LocalDate)  Controller.opretDestilleringStart for at oprette en destillering.
     * @see AlertTemplates#mangelAlert(String) AlertTemplates.mangelAlert for visning af fejlmeddelelse ved manglende oplysninger.
     * @see AlertTemplates#forkertAlert(String) AlertTemplates.forkertAlert for visning af fejlmeddelelse ved ugyldig dato eller tal.
     */
    private void opretDestillering() {

        if (batchNr.getText() == "" || startDato.getValue() == null) {
            AlertTemplates.mangelAlert("Batch nr og startdato skal angives!");
            return;
        }

        Destillering createdDestillering = null;

        try {
            createdDestillering = Controller.opretDestilleringStart(batchNr.getText(), startDato.getValue());
        } catch (Exception ex) {
            AlertTemplates.forkertAlert("Start dato er ikke en valid dato af: yyyy-MM-dd");
            return;
        }

        if (slutDato.getValue() != null) {
             try {
                createdDestillering.setSlutDato(slutDato.getValue());
            } catch (Exception ex) {
                AlertTemplates.forkertAlert("Start dato er ikke en valid dato af: yyyy-MM-dd");
                return;
            }
        }

        if (maltbatch.getText() != "") {
            createdDestillering.setMaltbatch(maltbatch.getText());
        }

        if (kornsort.getText() != "") {
            createdDestillering.setKornsort(kornsort.getText());
        }

        if (væskemængde.getText() != "") {
            try {
                createdDestillering.setVæskemængde(Double.parseDouble(væskemængde.getText()));
            } catch (Exception ex) {
                AlertTemplates.forkertAlert("Væskemængde skal være et tal!");
                return;
            }
        }

        if (alkoholProcent.getText() != "") {
            try {
                createdDestillering.setAlkoholProcent(Double.parseDouble(alkoholProcent.getText()));
            } catch (Exception ex) {
                AlertTemplates.forkertAlert("Alkoholprocent skal være et tal!");
                return;
            }
        }

        if (rygeMateriale.getText() != "") {
            createdDestillering.setRygeMateriale(rygeMateriale.getText());
        }

        if (medarbejderNavn.getText() != "") {
            createdDestillering.setMedarbejderNavn(medarbejderNavn.getText());
        }

        if (kommentar.getText() != "") {
            createdDestillering.setKommentar(kommentar.getText());
        }

        resetFelter();

        destilleringer.getItems().clear();
        destilleringer.getItems().addAll(Controller.getDestillinger());
    }

    private void resetFelter() {
        batchNr.setDisable(false);
        startDato.setDisable(false);

        batchNr.setText("");
        startDato.setValue(null);
        slutDato.setValue(null);
        maltbatch.setText("");
        kornsort.setText("");
        væskemængde.setText("");
        alkoholProcent.setText("");
        rygeMateriale.setText("");
        medarbejderNavn.setText("");
        kommentar.setText("");
    }
}
