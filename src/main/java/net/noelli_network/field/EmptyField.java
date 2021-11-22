package net.noelli_network.field;

import lombok.Getter;
import lombok.Setter;
import net.noelli_network.utils.position.Position;

@Getter
@Setter
public class EmptyField extends Field{
    private int bombCount;

    public EmptyField(Position position) {
        super(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmptyField)) return false;
        if (!super.equals(o)) return false;
        EmptyField that = (EmptyField) o;
        return bombCount == that.bombCount;
    }



    @Override
    public String toString() {
        return super.isOpen() ? bombCount > 0 ? bombCount < 10 ? bombCount + " " : bombCount + "" : "__" : "  ";
    }
}
