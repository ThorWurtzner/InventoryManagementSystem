package Gui.Panes;

import Application.Controller.Controller;
import Application.Model.Fad;
import Application.Model.Lager;
import Gui.Helpers.AlertTemplates;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OversigtPane extends BorderPane {

    private final TextField lagerNavn = new TextField();

    private final ComboBox<Lager> lagere = new ComboBox<>();

    private final ComboBox<Lager> flytFadTilNytLager = new ComboBox<>();
    private final ListView<Fad> fadePaaLager = new ListView<>();

    public OversigtPane() {
        // Skub alting ind fra siderne
        this.setPadding(new Insets(40));

        // Title
        Label title = new Label("Lageroversigt");
        title.setId("paneTitle");

        // Label
        Image lagerIcon = new Image("/Icons/lagerIcon.png");
        ImageView lagerIconView = new ImageView(lagerIcon);
        lagerIconView.setFitHeight(75);
        lagerIconView.setFitWidth(60);
        HBox titleBox = new HBox(550);
        titleBox.getChildren().addAll(title, lagerIconView);

        // Inputs
        lagerNavn.setPromptText("Indtast ønsket lagernavn...");
        Label lagerNavnLabel = new Label("Opret nyt lager:");
        VBox lagerNavnBox = new VBox(3);
        lagerNavnBox.getChildren().addAll(lagerNavnLabel, lagerNavn);

        lagere.valueProperty().addListener(mouseEvent -> visFadePaaLager());

        // Oprettelse
        Button btnOpretLager = new Button("Opret lager");
        btnOpretLager.getStyleClass().add("opretBtn-noTranslate");
        btnOpretLager.setOnAction(event -> opretLager());

        Button btnFlytLager = new Button("Flyt lager");
        btnFlytLager.getStyleClass().add("opretBtn-noTranslate");
        btnFlytLager.setOnAction(event -> flytLager());
        Label lagerFlytLabel = new Label("Vælg nyt lager til markeret fad:");
        flytFadTilNytLager.getItems().addAll(Controller.getLagere());
        VBox lagerflytBox = new VBox(3);
        lagerflytBox.getChildren().addAll(
                lagerFlytLabel,
                flytFadTilNytLager,
                btnFlytLager
        );


        // Indsættelse
        VBox inputBox = new VBox(20);
        inputBox.setTranslateX(-110);
        inputBox.getChildren().addAll(
                lagerNavnBox,
                btnOpretLager,
                lagerflytBox
        );

        Label lagerLabel = new Label("Vælg lager for at se dets fade:");
        VBox listBox = new VBox(3);
        listBox.getChildren().addAll(lagerLabel, lagere, fadePaaLager);

        lagere.getItems().addAll(Controller.getLagere());

        this.setTop(titleBox);
        this.setLeft(listBox);
        this.setRight(inputBox);

        // Styling
        lagere.getStyleClass().add("list-view-short");
        lagere.setMinHeight(34);
        flytFadTilNytLager.setMinHeight(34);
        flytFadTilNytLager.setMinWidth(250);
        btnFlytLager.setTranslateY(20);
        lagerflytBox.setTranslateY(10);
    }

    /**
     * Viser de fade, der er placeret på det valgte lager. Hvis intet lager er
     * valgt, forbliver listen tom. Hvis fade allerede er vist, fjernes de først for at opdatere listen.
     *
     * @implNote Hvis fade ikke har et tilknyttet lager, eller lageret ikke matcher det valgte lager, ignoreres de.
     *
     * @see Controller#getFade()
     * @see Fad#getLager()
     */
    private void visFadePaaLager() {
        fadePaaLager.getItems().clear();

        Lager selectedLager = lagere.getSelectionModel().getSelectedItem();

        for (Fad fad : Controller.getFade()) {
            if (fad.getLager() != null && fad.getLager().equals(selectedLager)) {
                fadePaaLager.getItems().add(fad);
            }
        }
    }

    /**
     * Opretter et nyt lager med det indtastede navn.
     * Efter oprettelse af lageret vises en bekræftelsesmeddelelse, og listen af lagre opdateres.
     *
     * @implNote Hvis lager-navnet allerede eksisterer, forhindres oprettelsen af et nyt lager med samme navn.
     *
     * @see Controller#opretLager(String)
     * @see Controller#getLagere()
     * @see Lager
     */
    private void opretLager() {
        if (lagerNavn.getText() == "") {
            AlertTemplates.mangelAlert("Indtast venligst et navn for det nye lager");
            return;
        }

        // Check om lager navn allerede eksisterer
        for (int i = 0; i < lagere.getItems().size(); i++) {
            if (lagere.getItems().get(i).equals(lagerNavn.getText())) {
                AlertTemplates.forkertAlert("Det lagernavn eksisterer allerede!");
                return;
            }
        }

        Lager lager = Controller.opretLager(lagerNavn.getText());
        flytFadTilNytLager.getItems().add(lager);

        AlertTemplates.bekræftelsesAlert("Lager er oprettet, og klart til at få fade tildelt gennem 'Hæld på fad' siden");

        lagere.getItems().clear();
        lagere.getItems().addAll(Controller.getLagere());
        lagere.getSelectionModel().select(lager);

        lagerNavn.setText("");
    }

    /**
     * Flytter valgt fad fra nuværende lager til nyt lager.
     * Efter flytningen vises en bekræftelsesmeddelelse, og listen af fade på det valgte lager opdateres.
     *
     * @see Fad#setLager(Lager)
     * @see AlertTemplates#bekræftelsesAlert(String) AlertTemplates.bekræftelsesAlert for visning af bekræftelsesmeddelelse.
     * @see #visFadePaaLager() visFadePaaLager for at opdatere listen af fade på det valgte lager.
     */
    private void flytLager() {
        if (fadePaaLager.getSelectionModel().getSelectedItem() == null || flytFadTilNytLager.getSelectionModel().getSelectedItem() == null) {
            AlertTemplates.mangelAlert("Vælg venligst et fad at flytte, samt det ønskede lager");
            return;
        }

        Lager selectedLager = flytFadTilNytLager.getSelectionModel().getSelectedItem();
        Fad fad = fadePaaLager.getSelectionModel().getSelectedItem();
        fad.setLager(selectedLager);

        AlertTemplates.bekræftelsesAlert("Fad er flyttet til: " + selectedLager);

        visFadePaaLager();
    }
}
