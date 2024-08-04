package Gui.Panes;

import Application.Controller.Controller;
import Application.Model.Destillering;
import Application.Model.Fad;
import Application.Model.Lager;
import Gui.Helpers.AlertTemplates;
import Gui.StartWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class HaeldPaaFadPane extends BorderPane {

    private final DatePicker ophældningsdato = new DatePicker();
    private final TextField literMængde = new TextField();

    private final ListView fadeTom = new ListView<>();
    private final ListView fadeFyldt = new ListView<>();
    private final ListView destilleringer = new ListView<>();
    private final ComboBox<Lager> lagerComboBox = new ComboBox<>();

    public HaeldPaaFadPane() {
        // Skub alting ind fra siderne
        this.setPadding(new Insets(40));

        // Title
        Label title = new Label("Hæld på fad");
        title.setId("paneTitle");

        // Inputs
        Label ophældningsdatoLabel = new Label("Ophældningsdato:");
        ophældningsdatoLabel.getStyleClass().add("inputLabel");
        ophældningsdato.setPromptText("Vælg ophældningsdato...");
        ophældningsdato.setEditable(false);
        HBox ophældningsdatoBox = new HBox(10);
        ophældningsdatoBox.setAlignment(Pos.CENTER_LEFT);
        ophældningsdatoBox.getChildren().addAll(ophældningsdatoLabel, ophældningsdato);

        Label literMængdeLabel = new Label("Antal liter:");
        literMængdeLabel.getStyleClass().add("inputLabel");
        literMængde.setPromptText("Indtast antal liter...");
        HBox literMængdeBox = new HBox(10);
        literMængdeBox.setAlignment(Pos.CENTER_LEFT);
        literMængdeBox.getChildren().addAll(literMængdeLabel, literMængde);

        Label lagerValgLabel = new Label("Vælg lager:");
        lagerValgLabel.getStyleClass().add("inputLabel");
        lagerValgLabel.setStyle("-fx-min-width: 65");
        lagerComboBox.getItems().addAll(Controller.getLagere());
        HBox lagerValgBox = new HBox(10);
        lagerValgBox.setAlignment(Pos.CENTER_LEFT);
        lagerValgBox.setTranslateX(-62);
        lagerComboBox.setMinWidth(200);
        lagerComboBox.setMinHeight(35);

        lagerValgBox.getChildren().addAll(lagerValgLabel, lagerComboBox);


        // Oprettelse
        Button btnHældPåFad = new Button("Hæld på fad");
        btnHældPåFad.getStyleClass().add("opretBtn");
        btnHældPåFad.setOnAction(event -> hældPåFad());

        // Hjælpknap
        Button btnGuide = new Button("?");
        btnGuide.setOnAction(event -> visGuide());

        fadeTom.setOnMouseClicked(event -> {
            fadeFyldt.setDisable(true);
            fadeFyldt.getSelectionModel().clearSelection();
        });

        fadeFyldt.setOnMouseClicked(event -> {
            fadeTom.setDisable(true);
            fadeTom.getSelectionModel().clearSelection();
            lagerComboBox.setDisable(true);
            lagerComboBox.getSelectionModel().clearSelection();
        });

        HBox inputBoxInner = new HBox(170);
        inputBoxInner.getChildren().addAll(ophældningsdatoBox, lagerValgBox);

        // Indsættelse
        VBox inputBox = new VBox(20);
        inputBox.getChildren().addAll(
                inputBoxInner, literMængdeBox
        );

        Label fadeTomLabel = new Label("Tomme fade:");
        Label fadeFyldtLabel = new Label("Fyldte fade:");
        VBox fadeTomBox = new VBox(3);
        VBox fadeFyldtBox = new VBox(3);

        VBox fadeBox = new VBox(15);
        fadeTomBox.getChildren().addAll(fadeTomLabel, fadeTom);
        fadeFyldtBox.getChildren().addAll(fadeFyldtLabel, fadeFyldt);
        fadeBox.getChildren().addAll(fadeTomBox, fadeFyldtBox);

        Label destillingerLabel = new Label("Destilleringer:");
        VBox destilleringerBox = new VBox(3);
        destilleringerBox.getChildren().addAll(destillingerLabel, destilleringer);

        HBox listBox = new HBox(30);
        listBox.getChildren().addAll(fadeBox, destilleringerBox);

        HBox btnBox = new HBox(525);
        btnBox.getChildren().addAll(btnHældPåFad, btnGuide);

        VBox listBoxOuter = new VBox();
        listBoxOuter.getChildren().addAll(listBox, btnBox);
        listBoxOuter.setPadding(new Insets(30, 0, 30,0));

        destilleringer.getItems().addAll(Controller.getDestillinger());

        opdaterFade();

        this.setTop(title);
        this.setLeft(inputBox);
        this.setBottom(listBoxOuter);

        // Styling
        destilleringer.getStyleClass().add("list-view-template");
        fadeTom.getStyleClass().add("list-view-short");
        fadeFyldt.getStyleClass().add("list-view-short");

        btnGuide.setTranslateY(40);
    }

    /**
     * Viser en guide til brug af funktionaliteten 'Hældning på fad'.
     *
     * @see Controller#lavOmhældning(Fad, ArrayList, Lager, LocalDate) Controller.lavOmhældning for den underliggende omhældningslogik.
     */
    private void visGuide() {
        Alert guideAlert = new Alert(Alert.AlertType.INFORMATION);
        guideAlert.setHeaderText("Guide til 'Hældning på fad'");
        guideAlert.setContentText("For at bruge funktionaliteten på denne side -" +
                "skal man vælge fra højre liste hvilken destillering man ønsker at påfylde, " +
                "samt herefter vælge fra enten listen over fyldte fad (hvis man ønsker at blande) eller et tomt fad." +
                "\n\nHvis et tomt fad er valgt, skal der vælges en dato på ophældningen, samt en liter mængde, som tages fra destilleringen - der skal desuden vælges hvilket lager fadet skal placeres på." +
                "\n\nHvis et allerede fyldt fad er valgt, skal der IKKE vælges lager, kun dato og liter mængde, som tages fra destilleringen" +
                "\n\nKlik herefter på knappen 'Hæld på fad', som vil registrere ophældningen, samt give en bekræftelse på dette.");
        guideAlert.show();
    }

    /**
     * Udfører ophældning af destillat fra en valgt destillering til et bestemt fad og registrerer
     * ophældningen i systemet. Opdaterer grænsefladen med nye oplysninger og giver bekræftelse.
     *
     * Hvis et tomt fad er valgt, opretter metoden først destillatet på fadet
     * og registrerer det på det valgte lager. Hvis et fyldt fad er valgt, opretter metoden destillatmængden i det
     * valgte fad. Efter en vellykket ophældning vises en bekræftelsesmeddelelse, og grænsefladen opdateres.
     */
    private void hældPåFad() {
        if (fadeTom.getSelectionModel().getSelectedItem() == null && fadeFyldt.getSelectionModel().getSelectedItem() == null) {
            AlertTemplates.mangelAlert("Vælg venligst et fad");
            return;
        }

        if (destilleringer.getSelectionModel().getSelectedItem() == null) {
            AlertTemplates.mangelAlert("Vælg venligst en destillering");
            return;
        }

        if (ophældningsdato.getValue() == null || literMængde.getText() == "") {
            AlertTemplates.mangelAlert("Indtast venligst antal liter og ophældningsdato");
            return;
        }

        Destillering selectedDestillering = (Destillering) destilleringer.getSelectionModel().getSelectedItem();
        Fad selectedFadTom = (Fad) fadeTom.getSelectionModel().getSelectedItem();
        Fad selectedFadFyldt = (Fad) fadeFyldt.getSelectionModel().getSelectedItem();

        if (selectedFadTom != null) {
            if (lagerComboBox.getSelectionModel().getSelectedItem() == null) {
                AlertTemplates.mangelAlert("Vælg venligst et lager til fadet");
                return;
            }
            // Hvis tomt fad er det valgte - skaber først destillatet på fadet
            Controller.opretDestillatPaaFad(ophældningsdato.getValue(), selectedFadTom);
            selectedFadTom.getDestillatPaaFad().createDestillatMængde(Integer.parseInt(literMængde.getText()), selectedDestillering, ophældningsdato.getValue());

            // Put fadet på det nuværende lager
            selectedFadTom.setLager(lagerComboBox.getSelectionModel().getSelectedItem());
        } else {
            if (literMængde.getText() == null) {
                AlertTemplates.mangelAlert("Indtast venligst antal liter");
                return;
            }
            // Hvis fyldt fad er det valgte
            selectedFadFyldt.getDestillatPaaFad().createDestillatMængde(Integer.parseInt(literMængde.getText()), selectedDestillering, ophældningsdato.getValue());
        }

        AlertTemplates.bekræftelsesAlert("Der er ophældt nyt destillat på fadet");

        opdaterFade();

        lagerComboBox.setDisable(false);
        fadeFyldt.setDisable(false);
        fadeTom.setDisable(false);

        lagerComboBox.getSelectionModel().clearSelection();
        ophældningsdato.setValue(null);
        fadeFyldt.getSelectionModel().clearSelection();
        fadeTom.getSelectionModel().clearSelection();
        destilleringer.getSelectionModel().clearSelection();
        literMængde.setText("");
    }

    /**
     * Opdaterer grænsefladens lister over tomme og fyldte fade ved at hente oplysninger fra systemet. Den fjerner
     * eksisterende elementer i listerne og tilføjer derefter de opdaterede fade baseret på systemets aktuelle tilstand.
     *
     * @see Controller#getFade() Controller.getFade henter den opdaterede liste over fade fra systemet.
     */
    private void opdaterFade() {
        fadeTom.getItems().clear();
        fadeFyldt.getItems().clear();

        ArrayList<Fad> tempFade = Controller.getFade();

        for (int i = 0; i < tempFade.size(); i++) {
            Fad fad = tempFade.get(i);
            if (fad.getDestillatPaaFad() == null) {
                fadeTom.getItems().add(fad);
            } else {
                fadeFyldt.getItems().add(fad);
            }
        }
    }
}
