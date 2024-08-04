package Gui;

import Gui.Panes.*;
import javafx.application.Application;
import javafx.css.Stylesheet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.text.html.StyleSheet;
import java.awt.*;

public class StartWindow extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Sall Whisky Destillery");
        BorderPane pane = new BorderPane();

        this.initContent(pane);

        Scene scene = new Scene(pane, 1000, 800);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // ------------------------------------------

    private void initContent(BorderPane primaryPane) {

        // pane til at vise indhold dynamisk i højre side
        StackPane contentSection = new StackPane();
        contentSection.setId("contentSection");

        // Tilføjelse af tekst ved program start
        Label labelInit = new Label("Sall Whisky Destillery");
        labelInit.setFont(new Font(24));
        contentSection.getChildren().addAll(labelInit);


        // ----------- Navigation
        // Ikon i nav
        Image whiskyBrandIcon = new Image("/Icons/whiskyBrandIcon.jpg");
        ImageView whiskyBrandIconView = new ImageView(whiskyBrandIcon);
        whiskyBrandIconView.setFitHeight(150);
        whiskyBrandIconView.setFitWidth(150);

        // Buttons hertil
        Button btnOversigt = new Button("Lageroversigt");
        Button btnHældPåFad = new Button("Hæld på fad");
        Button btnOpretDestillering = new Button("Opret destillering");
        Button btnRegistrerFad = new Button("Registrer fad");
        Button btnLavOmhældning = new Button("Lav omhældning");
        Button btnTidligereProduce = new Button("Tidligere produce");

        Button btnOpretWhisky = new Button("Opret whisky");

        // Vertical box til at placere knapperne i
        VBox navSection = new VBox(
                whiskyBrandIconView,

                btnOversigt,
                btnHældPåFad,
                btnOpretDestillering,
                btnRegistrerFad,
                btnLavOmhældning,
                btnTidligereProduce,
                btnOpretWhisky
        );

        // handling af klik på nav knapper, ved at skabe et pane med relevant content deri.
        btnOversigt.setOnAction(event -> showContent(new OversigtPane(), contentSection));
        btnHældPåFad.setOnAction(event -> showContent(new HaeldPaaFadPane(), contentSection));
        btnOpretDestillering.setOnAction(event -> showContent(new OpretDestilleringPane(), contentSection));
        btnRegistrerFad.setOnAction(event -> showContent(new RegistrerFadPane(), contentSection));
        btnLavOmhældning.setOnAction(event -> showContent(new LavOmhaeldningPane(), contentSection));
        btnTidligereProduce.setOnAction(event -> showContent(new TidligereProducePane(), contentSection));
        btnOpretWhisky.setOnAction(event -> showContent(new OpretWhiskyPane(), contentSection));

        // Indsættelse i primære panes
        primaryPane.setLeft(navSection);
        primaryPane.setCenter(contentSection);

        // Styling
        btnOpretWhisky.setId("opretWhisky");
        btnOpretWhisky.setTranslateY(170);
        navSection.setSpacing(15);
        navSection.setId("navSection");
        btnOversigt.setId("navBtn");
        btnHældPåFad.setId("navBtn");
        btnOpretDestillering.setId("navBtn");
        btnRegistrerFad.setId("navBtn");
        btnLavOmhældning.setId("navBtn");
        btnTidligereProduce.setId("navBtn");
    }

    private void showContent(BorderPane content, StackPane contentSection) {
        contentSection.getChildren().clear();
        contentSection.getChildren().add(content);
    }

}
