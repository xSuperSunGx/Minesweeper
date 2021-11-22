package net.noelli_network.utils;

import javafx.beans.property.SimpleStringProperty;

public class MyStringProperty extends SimpleStringProperty {

    private Object object;

    public MyStringProperty(Object object) {
        this.object = object;
    }

    @Override
    public Object getBean() {
        return object;
    }
}
