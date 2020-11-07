package ie.tcd.asepaint2020.logic.game;

import ie.tcd.asepaint2020.logic.internal.CollidableCircle;
import ie.tcd.asepaint2020.logic.internal.Point;

public class PaintImpl implements CollidableCircle {
    BoardImpl board;
    Point relPosition;

    Float PaintSize;

    @Override
    public Point GetOrigin() {
        return new Point(board.GetOrigin().getX() + relPosition.getX(),board.GetOrigin().getY() + relPosition.getY());
    }

    @Override
    public Float GetSize() {
        return PaintSize;
    }

    @Override
    public Point GetPrincipleLocation() {
        return GetOrigin();
    }

    @Override
    public Float GetPrincipleSize() {
        return GetSize();
    }
}
