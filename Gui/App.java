package Gui;

import Application.Controller.Controller;
import javafx.application.Application;

public class App {
    public static void main(String[] args) {
        // SKAL LIGGE FØR NÆSTE LINJE!!
        Controller.initContent();

        Application.launch(StartWindow.class);
    }
}
