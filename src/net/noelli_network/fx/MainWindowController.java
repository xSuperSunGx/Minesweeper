package net.noelli_network.fx;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import net.noelli_network.utils.Playground;
import net.noelli_network.utils.Position;
import net.noelli_network.field.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable, EventHandler<MouseEvent> {

    private GridPane pane;
    private Gson gson = new Gson();
    private Label label;
    private Playground playground;
    @FXML
    private BorderPane border;

    public static int size_x;
    public static int size_y;
    public static int bombCount;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            playground = new Playground();
            playground.init(size_x, size_y, bombCount);
            ChangeListener<Boolean> changeb = new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    SimpleBooleanProperty o = (SimpleBooleanProperty) observable;
                    Field f = (Field) o.getBean();
                    Button b = getButtonByPosition(f.getPosition());
                    if(f.isFlag()) {
                        b.setText("Flag");
                    } else {
                        b.setText("");

                        if (f.isOpen()) {
                            if (f instanceof EmptyField) {
                                EmptyField em = (EmptyField) f;
                                b.setText(em.getBombCount() + "");
                            } else {
                                b.setText("BOMB");
                            }

                        } else {
                            b.setText("");
                        }
                    }
                }
            };
            playground.getFields().forEach(fieldProperty -> {
                fieldProperty.flagProperty().addListener(changeb);
            });

        });

        pane = new GridPane();
        label = new Label("Status: ");
        for (int y = 0; y < size_y; y++) {//Spalte
            for (int x = 0; x < size_x; x++) { //Zeile

                pane.add(createButton(x,y), x, y);
            }
        }

        border.setCenter(pane);
        border.setBottom(label);
        
    }

    private Button createButton(int x, int y) {
        Position p = new Position(x, y);
        Button b = new Button();
        b.setPrefSize(50, 50);
        b.setId(gson.toJson(p));
        b.setOnMousePressed(this);
        return b;
    }

    private Button getButtonByPosition(Position position) throws NullPointerException {
        ObservableList<Node> b = pane.getChildren();
        for (Node node : b) {
            if(node instanceof Button) {
                Button button = (Button) node;
                if (button.getId().equalsIgnoreCase(this.gson.toJson(position))) {
                    return button;
                }
            }
        }
        return null;
    }


    private void sendLoose() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Du hast verloren!");

        alert.showAndWait();
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getSource() instanceof Button) {
            Button b = (Button) event.getSource();
            Position p = gson.fromJson(b.getId(), Position.class);
            if (event.isPrimaryButtonDown()) {
                System.out.println(p);

                if(playground.show(p.getX(), p.getY())) {

                    sendLoose();
                }
                playground.showField(false);
            } else if (event.isSecondaryButtonDown()) {
                Field f = playground.getField(p);
                if(f.isFlag()) {
                    playground.flagging(p.getX(), p.getY(), false);
                } else {
                    playground.flagging(p.getX(), p.getY(), true);
                }
            }
        }
    }
}
