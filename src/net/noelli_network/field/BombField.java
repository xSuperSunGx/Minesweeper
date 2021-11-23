package net.noelli_network.field;

import net.noelli_network.utils.position.Position;

public class BombField extends Field{

    public BombField(Position position) {
        super(position);
    }

    @Override
    public String toStringTest() {
        return "B ";
    }


    @Override
    public String toString() {
        return super.isOpen() ? "B " : "  ";
    }
}
