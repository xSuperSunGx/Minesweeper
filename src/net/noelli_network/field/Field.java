package net.noelli_network.field;

import javafx.beans.property.BooleanProperty;
import net.noelli_network.utils.property.MyBooleanProperty;
import net.noelli_network.utils.position.Position;


public abstract class Field {

    private final BooleanProperty flag = new MyBooleanProperty(this);
    private final BooleanProperty open = new MyBooleanProperty(this);
    private final Position position;

    public Field(Position position) {
        this.position = position;
        this.setOpen(false);
        this.setFlag(false);
    }

    public void setFlag(boolean flag) {
        this.flag.set(flag);
    }

    public void setOpen(boolean open) {
        this.open.set(open);
    }

    public boolean isFlag() {
        return flag.get();
    }

    public BooleanProperty flagProperty() {
        return flag;
    }

    public boolean isOpen() {
        return open.get();
    }

    public BooleanProperty openProperty() {
        return open;
    }

    public Position getPosition() {
        return position;
    }

}
