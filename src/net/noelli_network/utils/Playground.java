package net.noelli_network.utils;

import javafx.application.Platform;
import net.noelli_network.field.BombField;
import net.noelli_network.field.EmptyField;
import net.noelli_network.field.Field;
import net.noelli_network.field.FieldProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Playground {

    private Field[][] matrix;
    private int bombs;
    private List<FieldProperty> fields;
    private Logger f = Logger.getLogger("net.noelli_network");


    public void init(int with, int hight, int bombs) {
        f.setLevel(Level.INFO);
        this.fields = new ArrayList<>();
        Random random = new Random();
        matrix = new Field[hight][with];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                matrix[y][x] = new EmptyField(new Position(x, y));
                this.fields.add(new FieldProperty(matrix[y][x], matrix[y][x].isFlag(), matrix[y][x].isOpen()));
            }
        }
        for (int i = 0; i < bombs; i++) {
            int x = random.nextInt(with);
            int y = random.nextInt(hight);
            matrix[y][x] = new BombField(new Position(x,y));
            this.fields.set(i, new FieldProperty(matrix[y][x], matrix[y][x].isFlag(), matrix[y][x].isOpen()));
        }
        this.bombs = bombs;
    }

    public List<FieldProperty> getFields() {
        return fields;
    }

    public boolean show(int x, int y) {
        if(isBomb(x,y)==1)return true;
        this.show(0, x, y);
        return false;
    }

    public Field getField(Position position) {
        return this.matrix[position.getY()][position.getX()];
    }
    private boolean isArrayInBound(int x, int y) {
        return (x >=0 && x < matrix[0].length) && (y >= 0 && y < matrix.length);
    }

    private void show(int layer, int x, int y) {
        f.info("Bin in der Schleife");
        if(!isArrayInBound(x, y)) return;
        EmptyField f = (EmptyField) getField(new Position(x, y));
        if(f.isOpen()) return;
        int neigh = cntNeighbourBombs(x, y);
        this.f.info("Bin vor dem Opening");
        this.opening(x, y, true);
        this.f.info("Bin nach dem Opening");
        this.setBombs(f, neigh);
        this.f.info("Bin vor der IF");
        if(neigh == 0) {
            for (int yy = y-1; yy <= y+1; yy++) {
                for (int xx = x-1; xx <= x+1; xx++) {
                    this.show(layer+1, xx, yy);
                }
            }
        } else {
            for (int yy = y-1; yy <= y+1; yy++) {
                for (int xx = x-1; xx <= x+1; xx++) {
                    if(isBomb(xx, yy) != 1) {
                        this.show(layer+1, x,y);
                    }
                }
            }
        }
    }



    public void flagging(int x, int y, boolean status) throws ArrayIndexOutOfBoundsException{
        matrix[y][x].setFlag(status);
        Platform.runLater(() -> {

            for (FieldProperty field : this.getFields()) {
                if(field.getField().getPosition().equals(new Position(x,y))) {
                    field.setFlag(status);
                }
            }
        });
    }
    public void opening(int x, int y, boolean status) throws ArrayIndexOutOfBoundsException{
        matrix[y][x].setOpen(status);
            for (FieldProperty field : this.getFields()) {
                if(field.getField().getPosition().equals(new Position(x,y))) {
                    field.setOpen(status);
                }
            }

    }
    public void setBombs(EmptyField emptyField, int bombs) {
        emptyField.setBombCount(bombs);
        for (FieldProperty field : this.getFields()) {
            if(field.getField().getPosition().equals(emptyField.getPosition())) {
                field.setField(emptyField);
            }
        }
    }

    public boolean finished() {
        int sers = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].isFlag() && isBomb(j, i) == 1) {
                    sers++;
                } else if(matrix[i][j].isOpen() && isBomb(j, i) == 1){
                    return true;
                }
            }
        }
        return sers == this.bombs;
    }

    private int cntNeighbourBombs(int x, int y) {
        int result = 0;
        for (int yy = y-1; yy <= y+1; yy++) {
            for (int xx = x-1; xx <= x+1; xx++) {
                result += isBomb(xx, yy);
            }
        }
        return result;
    }

    private int isBomb(int x, int y) {

       return !(x >= 0 && x < matrix[0].length) || !(y >= 0 && y < matrix.length) ? 0 : matrix[y][x] instanceof BombField ? 1 : 0;
    }

    /**
     * @deprecated wird demnächst entfernt
     * @param showall für Testzweck auf true stellen, um alle Felder aufzudecken :D
     */
    @Deprecated
    public void showField(boolean showall) {
        if(showall){
            String str = "";
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j].setOpen(true);
                    str += "|" + matrix[i][j];
                }
                str += "|\n";
            }
            System.out.println(str);
        } else {
            String str = "";
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    str += "|" + matrix[i][j];
                }
                str += "|\n";
            }
            System.out.println(str);
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                ret += "|" + matrix[i][j].toString();
            }
            ret += "|\n";
        }
        return ret;

    }
}
