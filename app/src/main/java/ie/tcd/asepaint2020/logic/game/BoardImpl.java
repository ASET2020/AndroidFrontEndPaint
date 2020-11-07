package ie.tcd.asepaint2020.logic.game;

import ie.tcd.asepaint2020.common.Paint;
import ie.tcd.asepaint2020.logic.internal.Collidable;
import ie.tcd.asepaint2020.logic.internal.CollidableBox;
import ie.tcd.asepaint2020.logic.internal.Point;

import java.util.List;

public class BoardImpl implements CollidableBox{
    private OuterLimit screen;
    private List<PaintImpl> paintList;
    private Point CurrentLocation;
    private Point Size;

    @Override
    public Point GetOrigin() {
        return new Point(screen.GetOrigin().getX() + CurrentLocation.getX(),screen.GetOrigin().getY() + CurrentLocation.getY());
    }

    @Override
    public Point GetSize() {
        return Size;
    }

    @Override
    public Point GetPrincipleLocation() {
        return new Point(CurrentLocation.getX()+(Size.getX()/2f),CurrentLocation.getY()+(Size.getY()/2f));
    }

    @Override
    public Float GetPrincipleSize() {
        return Math.max(Size.getX()/2,Size.getY()/2);
    }
}
