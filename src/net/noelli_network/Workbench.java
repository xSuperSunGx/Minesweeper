package net.noelli_network;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.noelli_network.content.ContentWindowController;
import net.noelli_network.utils.Playground;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Workbench extends Application {

    public static ContentWindowController contentWindowController;

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


        Parent root = FXMLLoader.load(getClass().getResource("content/contentwindow.fxml"));

        primaryStage.setTitle("Minesweaper");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("pictures/ico24.png")));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("pictures/ico32.png")));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                switch (sendMainExitMessage()) {
                    case 0:
                        event.consume();
                        break;
                    case 1:
                        contentWindowController.init();
                        event.consume();
                        break;
                    case 2:
                        Platform.exit();
                        break;

                }
            }
        });
        Scene scene = new Scene(root);
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
            ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("pictures/ico24.png")));
            ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("pictures/ico32.png")));


            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                ContentWindowController.bombCount = Integer.parseInt(result.get());
                return;
            } else
                continue;

        }while (!sendExitMessage());
        System.exit(0);
    }

    private boolean sendExitMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Verlassen?");
        alert.setHeaderText(null);
        alert.setContentText("Möchtest du das Programm wirklich schließen?");
        ButtonType yes = ButtonType.YES;
        ButtonType no = ButtonType.NO;
        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.YES;

    }

    /**
     *
     * @return -1 = Error; 0 = Nein; 1 = Neu; 2 = Ja
     */
    private int sendMainExitMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Verlassen?");
        alert.setHeaderText(null);
        alert.setContentText("Möchtest du das Programm wirklich schließen?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType no = new ButtonType("Nein");
        ButtonType newl = new ButtonType("Neues Layout");
        alert.getButtonTypes().setAll(yes, newl,no);
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType buttonType = result.get();
        if (yes.equals(buttonType)) {
            return 2;
        } else if (newl.equals(buttonType)) {
            return 1;
        } else if (no.equals(buttonType)) {
            return 0;
        }
        System.exit(0);
        return -1;
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
            ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("pictures/ico24.png")));
            ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("pictures/ico32.png")));

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                ContentWindowController.size_x = Integer.parseInt(result.get());
                return ContentWindowController.size_x;
            } else
                continue;

        }while (!sendExitMessage());
        System.exit(0);
        return -1;
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
            ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("pictures/ico24.png")));
            ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("pictures/ico32.png")));

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                ContentWindowController.size_y = Integer.parseInt(result.get());
                return ContentWindowController.size_y;
            } else
                continue;

        }while (!sendExitMessage());
        System.exit(0);
        return -1;
    }



}
