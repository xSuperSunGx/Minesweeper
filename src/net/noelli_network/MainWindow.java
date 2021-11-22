package net.noelli_network;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.noelli_network.fx.MainWindowController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        int xmax = 40;
        int ymax = 20;


        int resx = askX(xmax);
        int resy = askY(ymax);
        int bombmax = (resx*resy)/6;
        askBomb(bombmax);


        Parent root = FXMLLoader.load(getClass().getResource("fx/mainwindow.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Minesweaper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void askBomb(int bombmax) {
        List<String> choices = new ArrayList<>();
        for (int i = 5; i <= bombmax; i++) {
            choices.add(i+"");
        }
        do {
            ChoiceDialog<String> dialog = new ChoiceDialog<>( "" +bombmax, choices);
            dialog.setTitle("Wähle die Größe");
            dialog.setHeaderText(null);
            dialog.setContentText("Gib die Bombenanzhl an: ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                MainWindowController.bombCount = Integer.parseInt(result.get());
                break;
            } else
                continue;

        }while (true);
    }

    private int askX(int xmax) {
        List<String> choices = new ArrayList<>();
        for (int i = 5; i <= xmax; i++) {
            choices.add(i+"");
        }
        do {
            ChoiceDialog<String> dialog = new ChoiceDialog<>("20", choices);
            dialog.setTitle("Wähle die Größe");
            dialog.setHeaderText(null);
            dialog.setContentText("Gib die Länge des Spielfeldes an: ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                MainWindowController.size_x = Integer.parseInt(result.get());
                return MainWindowController.size_x;
            } else
                continue;

        }while (true);
    }
    private int askY(int ymax) {
        List<String> choices = new ArrayList<>();
        for (int i = 5; i <= ymax; i++) {
            choices.add(i+"");
        }
        do {
            ChoiceDialog<String> dialog = new ChoiceDialog<>("10", choices);
            dialog.setTitle("Wähle die Größe");
            dialog.setHeaderText(null);
            dialog.setContentText("Gib die Breite des Spielfeldes an: ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                MainWindowController.size_y = Integer.parseInt(result.get());
                return MainWindowController.size_y;
            } else
                continue;

        }while (true);
    }



}
