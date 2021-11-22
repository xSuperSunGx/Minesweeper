package net.noelli_network.content;

import com.google.gson.Gson;
import javafx.beans.property.SimpleBooleanProperty;
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
import net.noelli_network.utils.position.FlagPositions;
import net.noelli_network.utils.Playground;
import net.noelli_network.utils.position.Position;
import net.noelli_network.field.*;
import net.noelli_network.utils.SweaperColor;

import java.net.URL;
import java.util.ResourceBundle;

public class ContentWindowController implements Initializable, EventHandler<MouseEvent> {

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

        init();
    }

    private void init() {
        playground = new Playground();
        playground.init(size_x, size_y, bombCount);
        ChangeListener<Boolean> flag = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                SimpleBooleanProperty o = (SimpleBooleanProperty) observable;
                Field f = (Field) o.getBean();
                Button b = getButtonByPosition(f.getPosition());
                FlagPositions fp = null;
                for (FlagPositions flagPosition : playground.getFlagPositions()) {
                    if(flagPosition.getPosition().equals(f.getPosition())) {
                        fp = flagPosition;
                    }
                }
                String oldflag = "";
                if(fp == null) {
                    fp = new FlagPositions(f.getPosition(), SweaperColor.INVISIBLE, -1);
                    oldflag = "#" + (fp.getColorcode().split("#")[1]);
                } else{
                    oldflag = "#" + (fp.getColorcode().split("#")[1]);
                }
                b.setText(newValue ? "Flag" : fp.getBombcount() <= 0 ? "" : fp.getBombcount()+"");
                b.setStyle(newValue ? "-fx-background-color: black;-fx-text-fill: " + oldflag : "-fx-background-color: " + oldflag + ";-fx-text-fill: black");
                int flgc = Integer.parseInt(label.getText().split(" ")[1]);
                label.setText(newValue ? label.getText().split(" ")[0] + " " + (flgc-1) : label.getText().split(" ")[0] + " " + (flgc+1));
            }
        };
        ChangeListener<Boolean> open = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                SimpleBooleanProperty o = (SimpleBooleanProperty) observable;
                Field f = (Field) o.getBean();
                Button b = getButtonByPosition(f.getPosition());
                FlagPositions fp = null;
                if((fp = playground.getFlagPositionByPosition(f.getPosition())) != null) {
                    playground.getFlagPositions().remove(fp);
                }
                if(f instanceof EmptyField) {
                    int bombcount = ((EmptyField)f).getBombCount();
                    b.setText(newValue ? bombcount > 0 ? bombcount + "" : "" : "");
                    switch (bombcount) {
                        case 0:
                            b.setStyle(newValue ? SweaperColor.VISIBLE_0 : SweaperColor.INVISIBLE );
                            fp = new FlagPositions(f.getPosition(), SweaperColor.VISIBLE_0, bombcount);
                            break;
                        case 1:
                            b.setStyle(newValue ? SweaperColor.VISIBLE_1 : SweaperColor.INVISIBLE );
                            fp = new FlagPositions(f.getPosition(), SweaperColor.VISIBLE_1, bombcount);
                            break;
                        case 2:
                            b.setStyle(newValue ? SweaperColor.VISIBLE_2 : SweaperColor.INVISIBLE );
                            fp = new FlagPositions(f.getPosition(), SweaperColor.VISIBLE_2, bombcount);
                            break;
                        case 3:
                            b.setStyle(newValue ? SweaperColor.VISIBLE_3 : SweaperColor.INVISIBLE );
                            fp = new FlagPositions(f.getPosition(), SweaperColor.VISIBLE_3, bombcount);
                            break;

                        default:
                            b.setStyle(newValue ? SweaperColor.VISIBLE_OTHER : SweaperColor.INVISIBLE);
                            fp = new FlagPositions(f.getPosition(), SweaperColor.VISIBLE_OTHER, bombcount);
                            break;
                    }
                    playground.getFlagPositions().add(fp);
                } else {
                    b.setText(newValue ? "BOMB" : "");
                    b.setStyle(newValue ? SweaperColor.BOMB : SweaperColor.INVISIBLE);
                }
            }
        };
        for (int y = 0; y < playground.getMatrix().length; y++) {
            for (int x = 0; x < playground.getMatrix()[y].length; x++) {
                playground.getMatrix()[y][x].flagProperty().addListener(flag);
                playground.getMatrix()[y][x].openProperty().addListener(open);
            }
        }



        pane = new GridPane();
        label = new Label("Flags: " + bombCount);
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
        b.setStyle("-fx-background-color: a7a7a7;");
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Du hast verloren!");

        alert.showAndWait();
    }
    private void sendWinn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Du hast Gewonnen!");

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
                    init();
                } else if(playground.finished()) {
                    sendWinn();
                    init();
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
