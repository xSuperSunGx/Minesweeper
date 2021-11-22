package net.noelli_network.field;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import net.noelli_network.utils.MyStringProperty;

public class FieldProperty {

    private StringProperty field;
    private BooleanProperty flag;
    private BooleanProperty open;

    public FieldProperty(Field field, boolean flag, boolean open) {
        this.field = new MyStringProperty(field);
        this.flag = new SimpleBooleanProperty(flag);
        this.open = new SimpleBooleanProperty(open);
    }

    public Field getField() {
        return (Field) field.getBean();
    }

    public StringProperty fieldProperty() {
        return field;
    }


    public boolean isFlag() {
        return flag.get();
    }

    public BooleanProperty flagProperty() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag.set(flag);
    }

    public boolean isOpen() {
        return open.get();
    }

    public BooleanProperty openProperty() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open.set(open);
    }
}
