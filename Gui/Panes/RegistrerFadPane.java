package Gui.Panes;

import Application.Controller.Controller;
import Application.Model.Fad;
import Gui.Helpers.AlertTemplates;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrerFadPane extends BorderPane {

    private final TextField fadNr = new TextField();
    private final TextField leverandør = new TextField();
    private final TextField fadtype = new TextField();
    private final TextField antalTidligereFyldninger = new TextField();
    private final TextField literStørrelse = new TextField();

    private final ListView fade = new ListView<>();

    public RegistrerFadPane() {
        // Skub alting ind fra siderne
        this.setPadding(new Insets(40));

        // Title
        Label title = new Label("Registrer fad");
        title.setId("paneTitle");

        // Inputs
        Label fadNrLabel = new Label("Fad Nr:");
        fadNrLabel.getStyleClass().add("inputLabel");
        fadNr.setPromptText("Indtast fad Nr...");
        HBox fadNrBox = new HBox(10);
        fadNrBox.setAlignment(Pos.CENTER_LEFT);
        fadNrBox.getChildren().addAll(fadNrLabel, fadNr);

        Label leverandørLabel = new Label("Leverandør:");
        leverandørLabel.getStyleClass().add("inputLabel");
        leverandør.setPromptText("Indtast leverandør...");
        HBox leverandørBox = new HBox(10);
        leverandørBox.setAlignment(Pos.CENTER_LEFT);
        leverandørBox.getChildren().addAll(leverandørLabel, leverandør);

        Label fadtypeLabel = new Label("Fadtype:");
        fadtypeLabel.getStyleClass().add("inputLabel");
        fadtype.setPromptText("Indtast fadtype...");
        HBox fadtypeBox = new HBox(10);
        fadtypeBox.setAlignment(Pos.CENTER_LEFT);
        fadtypeBox.getChildren().addAll(fadtypeLabel, fadtype);

        Label antalTidligereFyldningerLabel = new Label("Antal fyldninger:");
        antalTidligereFyldningerLabel.getStyleClass().add("inputLabel");
        antalTidligereFyldninger.setPromptText("Indtast antal tidligere fyldninger...");
        HBox antalTidligereFyldningerBox = new HBox(10);
        antalTidligereFyldningerBox.setAlignment(Pos.CENTER_LEFT);
        antalTidligereFyldningerBox.getChildren().addAll(antalTidligereFyldningerLabel, antalTidligereFyldninger);

        Label literStørrelseLabel = new Label("Liter Størrelse:");
        literStørrelseLabel.getStyleClass().add("inputLabel");
        literStørrelse.setPromptText("Indtast liter størrelse...");
        HBox literStørrelseBox = new HBox(10);
        literStørrelseBox.setAlignment(Pos.CENTER_LEFT);
        literStørrelseBox.getChildren().addAll(literStørrelseLabel, literStørrelse);

        // Oprettelse
        Button btnRegistrerFad = new Button("Registrer fad");
        btnRegistrerFad.getStyleClass().add("opretBtn");
        btnRegistrerFad.setOnAction(event -> registrerFad());

        // Indsættelse
        VBox inputBox = new VBox(20);
        inputBox.getChildren().addAll(
                fadNrBox, leverandørBox, fadtypeBox,
                antalTidligereFyldningerBox, literStørrelseBox,
                btnRegistrerFad
        );

        fade.getItems().addAll(Controller.getFade());

        this.setTop(title);
        this.setLeft(inputBox);
        this.setRight(fade);

        // Styling
        fade.getStyleClass().add("list-view-template");
    }

    /**
     * Registrerer et nyt fad med de angivne oplysninger. Hvis påkrævede oplysninger ikke er angivet, vises en fejlmeddelelse.
     * Efter registreringen nulstilles tekstfelterne, og listen af fade opdateres.
     *
     * @implNote Hvis fadnummeret allerede eksisterer, forhindres oprettelsen af et nyt fad med samme nummer.
     *
     * @see Controller#opretFad(String, String, String, int, int)
     * @see Fad
     * @see AlertTemplates#mangelAlert(String)
     */
    private void registrerFad() {
        if (fadNr.getText() == "" || leverandør.getText() == "" || fadtype.getText() == "" || antalTidligereFyldninger.getText() == "" || literStørrelse.getText() == "") {
            AlertTemplates.mangelAlert("Alle felter skal angives!");
            return;
        }

        for (int i = 0; i < fade.getItems().size(); i++) {
            Fad fad = (Fad) fade.getItems().get(i);
            if (fad.getFadNr().equals(fadNr.getText())) {
                AlertTemplates.forkertAlert("Et fad med det nr eksisterer allerede!");
                return;
            }
        }

        try {
            Controller.opretFad(fadNr.getText(), leverandør.getText(), fadtype.getText(), Integer.parseInt(antalTidligereFyldninger.getText()), Integer.parseInt(literStørrelse.getText()));
        } catch (Exception ex) {
            AlertTemplates.forkertAlert("Tidligere fyldninger og literstørrelse skal være et tal!");
            return;
        }

        resetFelter();

        fade.getItems().clear();
        fade.getItems().addAll(Controller.getFade());
    }

    private void resetFelter() {
        fadNr.setText("");
        leverandør.setText("");
        fadtype.setText("");
        antalTidligereFyldninger.setText("");
        literStørrelse.setText("");
    }
}
