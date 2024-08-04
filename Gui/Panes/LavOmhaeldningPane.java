package Gui.Panes;

import Application.Controller.Controller;
import Application.Model.DestillatMængde;
import Application.Model.DestillatPaaFad;
import Application.Model.Fad;
import Application.Model.Lager;
import Gui.Helpers.AlertTemplates;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class LavOmhaeldningPane extends BorderPane {

    private final ListView<Fad> fadeTom = new ListView<>();

    private final ListView<Fad> fadeFyldt = new ListView<>();

    private final DatePicker omhældningsDato = new DatePicker();

    private final ComboBox<Lager> lagerComboBox = new ComboBox<>();

    public LavOmhaeldningPane() {
        // Skub alting ind fra siderne
        this.setPadding(new Insets(40));

        // Title
        Label title = new Label("Lav omhældning");
        title.setId("paneTitle");

        // Oprettelse
        Button btnOmhæld = new Button("Omhæld fade");
        btnOmhæld.getStyleClass().add("opretBtn");
        btnOmhæld.setOnAction(event -> lavOmhældning());

        Label lagerValgLabel = new Label("Vælg lager til nyt fad:");
        lagerComboBox.getItems().addAll(Controller.getLagere());
        VBox lagerValgBox = new VBox(3);
        lagerValgBox.getChildren().addAll(lagerValgLabel, lagerComboBox);
        lagerValgBox.setAlignment(Pos.CENTER_LEFT);
        lagerComboBox.setMinWidth(200);
        lagerComboBox.setMinHeight(35);

        Label omhældningsDatoLabel = new Label("Vælg dato for omhældning:");
        VBox omhældningsDatoBox = new VBox(3);
        omhældningsDatoBox.getChildren().addAll(omhældningsDatoLabel, omhældningsDato);

        Label fadeTomLabel = new Label("Tomme fade:");
        VBox fadeTomBox = new VBox(3);
        fadeTomBox.getChildren().addAll(fadeTomLabel, fadeTom, btnOmhæld);

        Label fadeFyldtLabel = new Label("Fyldte fade: (kan vælges flere)");
        VBox fadeFyldtBox = new VBox(3);
        fadeFyldtBox.getChildren().addAll(fadeFyldtLabel, fadeFyldt, lagerValgBox, omhældningsDatoBox);
        fadeFyldt.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        opdaterFade();

        setTop(title);
        setLeft(fadeFyldtBox);
        setCenter(new Label("->"));
        setRight(fadeTomBox);

        // Styling
        fadeTom.getStyleClass().add("list-view-template");
        fadeFyldt.getStyleClass().add("list-view-template");
        omhældningsDatoLabel.setPadding(new Insets(10, 0, 0, 0));
        lagerValgLabel.setPadding(new Insets(20, 0, 0, 0));
    }

    /**
     * Udfører en omhældning af destillat fra valgte tomme fade til et bestemt tomt fad, registrerer omhældningen i systemet og
     * opdaterer grænsefladen med de nye oplysninger. Hvis påkrævede valg ikke er foretaget (fadvalg, dato eller lager),
     * vises en fejlmeddelelse ved hjælp af {@link AlertTemplates#mangelAlert(String)}.
     *
     * @see Controller#lavOmhældning(Fad, ArrayList, Lager, LocalDate) Controller.lavOmhældning for den underliggende omhældningslogik.
     */
    private void lavOmhældning() {
        if (fadeTom.getSelectionModel().getSelectedItem() == null || fadeFyldt.getSelectionModel().getSelectedItem() == null) {
            AlertTemplates.mangelAlert("Vælg venligst fad(e) fra venstre liste, og et fad fra højre liste du ønsker at omhælde dem til.");
            return;
        }

        if (omhældningsDato.getValue() == null || lagerComboBox.getSelectionModel().getSelectedItem() == null) {
            AlertTemplates.mangelAlert("Vælg venglist et lager og dato for omhældningen.");
            return;
        }

        Fad selectedTomtFad = fadeTom.getSelectionModel().getSelectedItem();
        ArrayList<Fad> selectedFyldtFade = new ArrayList<>(fadeFyldt.getSelectionModel().getSelectedItems());
        Lager lager = lagerComboBox.getSelectionModel().getSelectedItem();
        LocalDate omhældningsDag = omhældningsDato.getValue();

        DestillatPaaFad nyeDestillatPaaFad = Controller.lavOmhældning(selectedTomtFad, selectedFyldtFade, lager, omhældningsDag);

        AlertTemplates.bekræftelsesAlert(selectedFyldtFade.size() + " fade omhældt til: " + selectedTomtFad.getFadNr());

        opdaterFade();
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
