package ie.tcd.asepaint2020.logic.game;

import ie.tcd.asepaint2020.logic.internal.CollidableBox;
import ie.tcd.asepaint2020.logic.internal.Point;

public class OuterLimit implements CollidableBox {
    private Float SizeX;
    private Float SizeY;

    public Float getSizeX() {
        return SizeX;
    }

    public Float getSizeY() {
        return SizeY;
    }

    public OuterLimit(Float sizeX, Float sizeY) {
        SizeX = sizeX;
        SizeY = sizeY;
    }

    @Override
    public Point GetOrigin() {
        return new Point(0f,0f);
    }

    @Override
    public Point GetSize() {
        return new Point(SizeX,SizeY);
    }

    @Override
    public Point GetPrincipleLocation() {
        return new Point(SizeX/2,SizeY/2);
    }

    @Override
    public Float GetPrincipleSize() {
        return Math.max(SizeX/2,SizeY/2);
    }
}
