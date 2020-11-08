package ie.tcd.asepaint2020.logic.game;

import ie.tcd.asepaint2020.common.Paint;
import ie.tcd.asepaint2020.logic.internal.ViewPointTranslator;
import ie.tcd.asepaint2020.logic.internal.CollidableCircle;
import ie.tcd.asepaint2020.logic.internal.Point;

public class PaintImpl implements CollidableCircle, Paint {
    BoardImpl board;
    Point relPosition;

    Float PaintSize;

    Player Owner;

    ViewPointTranslator tr;

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

    @Override
    public String Owner() {
        return Owner.getName();
    }

    @Override
    public String Color() {
        return Owner.getColor();
    }

    @Override
    public Float LocationX() {
        return tr.translatePointToMatchViewpoint(GetOrigin()).getX();
    }

    @Override
    public Float LocationY() {
        return tr.translatePointToMatchViewpoint(GetOrigin()).getY();
    }

    @Override
    public Float Size() {
        return (tr.translatePointToMatchViewpoint(new Point(PaintSize,PaintSize)).getX()+tr.translatePointToMatchViewpoint(new Point(PaintSize,PaintSize)).getY())/2;
    }
}
