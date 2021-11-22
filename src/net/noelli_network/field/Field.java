package net.noelli_network.field;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;
import net.noelli_network.utils.Position;

@Data
public abstract class Field {

    private boolean flag;
    private boolean open;
    private final Position position;

    public Field(Position position) {
        this.flag = false;
        this.open = false;
        this.position = position;
    }




}
