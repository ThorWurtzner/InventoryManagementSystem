package Gui.Panes;

import Application.Controller.Controller;
import Application.Model.*;
import Gui.Helpers.AlertTemplates;
import Storage.Storage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class OpretWhiskyPane extends BorderPane {

    private final ComboBox<Fad> fadDropdown = new ComboBox<>();

    private final TextField ønsketMængde = new TextField();

    private final ListView<WhiskyMængde> byggelseAfWhisky = new ListView<>();

    private final TextField whiskyNavn = new TextField();

    private final CheckBox caskStrengthCheck = new CheckBox();

    private final CheckBox tømtFadCheck = new CheckBox();

    private static int batchNr = 1;

    private ArrayList<Fad> tømteFade = new ArrayList<>();

    public OpretWhiskyPane() {
        // Skub alting ind fra siderne
        this.setPadding(new Insets(40));

        // Title
        Label title = new Label("Opret whisky");
        title.setId("paneTitle");

        // Inputs
        Label fadDropdownLabel = new Label("Fad over 3 år:");
        fadDropdownLabel.getStyleClass().add("inputLabel");
        VBox fadDropdownBox = new VBox(3);
        fadDropdownBox.setMaxWidth(200);
        fadDropdownBox.setMinWidth(200);
        fadDropdownBox.getChildren().addAll(fadDropdownLabel, fadDropdown);
        fadDropdown.getItems().addAll(Controller.getWhiskyFade());

        Label ønsketMængdeLabel = new Label("Ønsket mængde fra fad:");
        ønsketMængdeLabel.getStyleClass().add("inputLabel");
        ønsketMængde.setPromptText("Indtast ønsket mængde...");
        VBox ønsketmængdeBox = new VBox(3);
        ønsketmængdeBox.setAlignment(Pos.CENTER_LEFT);
        ønsketmængdeBox.getChildren().addAll(ønsketMængdeLabel, ønsketMængde);

        Label tømtFadCheckLabel = new Label("Dette fad tømmes");
        HBox tømtFadCheckBox = new HBox(5);
        tømtFadCheckBox.setAlignment(Pos.CENTER_LEFT);
        tømtFadCheckBox.getChildren().addAll(tømtFadCheck, tømtFadCheckLabel);

        // Tilføjelse
        Button btnTilføjMængde = new Button("Tilføj til whisky");
        btnTilføjMængde.setOnAction(event -> tilføjMængde());

        VBox ønsketMængdeBoxMedKnap = new VBox(17);
        ønsketMængdeBoxMedKnap.getChildren().addAll(ønsketmængdeBox, tømtFadCheckBox, btnTilføjMængde);

        HBox fadBox = new HBox(20);
        fadBox.getChildren().addAll(fadDropdownBox, ønsketMængdeBoxMedKnap);


        // Byggelse af whisky
        Label byggelseAfWhiskyLabel = new Label("Whisky opbygning:");
        byggelseAfWhiskyLabel.getStyleClass().add("inputLabel");
        VBox byggelseAfWhiskyBox = new VBox(3);
        byggelseAfWhiskyBox.getChildren().addAll(byggelseAfWhiskyLabel, byggelseAfWhisky);

        // Whisky informationer
        Label whiskyNavnLabel = new Label("Whisky navn:");
        whiskyNavnLabel.getStyleClass().add("inputLabel");
        whiskyNavn.setPromptText("Indtast whisky navn...");
        HBox whiskyNavnBox = new HBox(3);
        whiskyNavnBox.setAlignment(Pos.CENTER_LEFT);
        whiskyNavnBox.getChildren().addAll(whiskyNavnLabel, whiskyNavn);

        Label caskStrengthCheckLabel = new Label("Cask strength");
        caskStrengthCheckLabel.getStyleClass().add("inputLabel");
        HBox caskStrengthCheckBox = new HBox(5);
        caskStrengthCheckBox.setAlignment(Pos.CENTER_LEFT);
        caskStrengthCheckBox.getChildren().addAll(caskStrengthCheck, caskStrengthCheckLabel);


        Button btnOpretWhisky = new Button("Opret whisky");
        btnOpretWhisky.getStyleClass().add("opretBtn");
        btnOpretWhisky.setOnAction(event -> opretWhisky());

        VBox whiskyBox = new VBox(10);
        whiskyBox.getChildren().addAll(whiskyNavnBox, caskStrengthCheckBox, btnOpretWhisky);

        // Indsættelse
        VBox outerBox = new VBox(30);
        outerBox.getChildren().addAll(
                fadBox, byggelseAfWhiskyBox, whiskyBox
        );

        this.setTop(title);
        this.setLeft(outerBox);

        // Styling
        btnTilføjMængde.getStyleClass().add("opretBtn-noTranslate");
        fadDropdown.setMinWidth(200);
        fadDropdown.setMinHeight(35);
        byggelseAfWhisky.getStyleClass().add("list-view-short");
        fadBox.setMinHeight(150);
    }


    /**
     * Tilføjer ønsket mængde af destillat fra det valgte fad til listen til whisky-oprettelsen. Hvis nødvendige valg
     * ikke er foretaget (fadvalg eller ønsket mængde), vises en fejlmeddelelse. Derudover kontrolleres, om den ønskede
     * mængde er tilgængelig på det valgte fad. Hvis ikke, vises en fejlmeddelelse. Hvis tømtFadCheck er markeret,
     * bliver alle de fade som blev markeret som tømt, efterfølgende registreret som det i systemet
     *
     * @implNote Hvis ønsket mængde er mere end den tilgængelige mængde på fadet, vises en fejlmeddelelse.
     *
     * @see DestillatMængde
     * @see WhiskyMængde
     * @see AlertTemplates#forkertAlert(String)
     */
    private void tilføjMængde() {

        if (fadDropdown.getSelectionModel().getSelectedItem() == null || ønsketMængde.getText() == "") {
            AlertTemplates.mangelAlert("Vælg venligst et fad og en mængde at udtrække derfra");
            return;
        }

        ArrayList<DestillatMængde> destillatMængder = (ArrayList<DestillatMængde>) fadDropdown.getSelectionModel().getSelectedItem().getDestillatPaaFad().getDestillatMængder();
        double sumAfDestillatMængderPaaFad = 0;
        for (DestillatMængde mængde : destillatMængder) {
            sumAfDestillatMængderPaaFad += mængde.getLiterMængde();
        }

        if (Double.parseDouble(ønsketMængde.getText()) > sumAfDestillatMængderPaaFad) {
            AlertTemplates.forkertAlert("Din ønskede mængde er mere end der er tilgængeligt på fadet");
            return;
        }

        WhiskyMængde wm = new WhiskyMængde(Double.parseDouble(ønsketMængde.getText()), fadDropdown.getSelectionModel().getSelectedItem());

        byggelseAfWhisky.getItems().add(wm);

        if (tømtFadCheck.isSelected()) {
            tømteFade.add(fadDropdown.getSelectionModel().getSelectedItem());
            tømtFadCheck.setSelected(false);
            fadDropdown.getItems().remove(fadDropdown.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Opretter en ny whisky baseret på de indtastede oplysninger og mængder valgt fra fadene. Hvis byggelses-listen er tom,
     * eller et whisky-navn ikke er angivet vises en fejlmeddelelse.
     * Derudover nulstilles tekstfelterne og byggelses-listen, og batch-nummeret øges til næste whisky.
     *
     * @see Controller#opretWhisky(int, String, boolean, List)
     * @see Whisky
     * @see AlertTemplates#mangelAlert(String)
     */
    private void opretWhisky() {

        if (byggelseAfWhisky.getItems().size() == 0) {
            AlertTemplates.mangelAlert("Whiskyen er tom, udtræk venligst mængder fra fade øverst på siden");
            return;
        }

        if (whiskyNavn.getText() == "") {
            AlertTemplates.mangelAlert("Udfyldt venligst et navn til whiskyen");
            return;
        }

        Controller.opretWhisky(batchNr, whiskyNavn.getText(), caskStrengthCheck.isSelected(), byggelseAfWhisky.getItems());
        AlertTemplates.bekræftelsesAlert(
        "Whisky registreret: '" + whiskyNavn.getText() + "'"
                + "\nBatch nr: " + batchNr + "\nFra " + byggelseAfWhisky.getItems().size()
                + (byggelseAfWhisky.getItems().size() == 1 ? " fad, så er 'Single malt'" : " fade")
                + "\nCask strength: " + (caskStrengthCheck.isSelected() == true ? "Ja" : "Nej")
        );

        byggelseAfWhisky.getItems().clear();
        whiskyNavn.clear();
        ønsketMængde.clear();
        fadDropdown.getSelectionModel().clearSelection();
        caskStrengthCheck.setSelected(false);

        // Øger batch nummeret til næste whisky der bliver skabt
        batchNr++;
        tømFade();
    }

    /**
     * Tømmer de fade, der findes i listen af tømteFade. For hvert fad fjernes destillatet fra fadet og fadet
     * fjernes fra lageret.
     *
     * @see Fad#removeDestillatPaaFad()
     * @see Fad#fjernFraLager()
     */
    private void tømFade(){
        for (int i = 0; i < tømteFade.size(); i++) {
            tømteFade.get(i).removeDestillatPaaFad();
            tømteFade.get(i).fjernFraLager();
        }
    }
}
