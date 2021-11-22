package net.noelli_network.field;

import net.noelli_network.utils.Position;

public class BombField extends Field{

    public BombField(Position position) {
        super(position);
    }


    @Override
    public String toString() {
        return super.isOpen() ? "B " : "  ";
    }
}
