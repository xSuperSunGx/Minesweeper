package net.noelli_network.utils.property;

import javafx.beans.property.SimpleBooleanProperty;

public class MyBooleanProperty extends SimpleBooleanProperty {

    private Object object;

    public MyBooleanProperty(Object object) {
        super();
        this.object = object;
    }

    @Override
    public Object getBean() {
        return object;
    }
}
