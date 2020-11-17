package ie.tcd.asepaint2020.logic.game;

import ie.tcd.asepaint2020.common.Paint;
import ie.tcd.asepaint2020.logic.internal.CollidableCircle;
import ie.tcd.asepaint2020.logic.internal.Point;
import ie.tcd.asepaint2020.logic.internal.ViewPointTranslator;

/**
 * the implementation of Paint
 */
public class PaintImpl implements CollidableCircle, Paint {
    public PaintImpl(BoardImpl board, Point relPosition, Float paintSize, Player owner, ViewPointTranslator tr) {
        this.board = board;
        this.relPosition = relPosition;
        PaintSize = paintSize;
        Owner = owner;
        this.tr = tr;
    }

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
        return tr.translatePointToMatchViewpoint(relPosition).getX();
    }

    @Override
    public Float LocationY() {
        return tr.translatePointToMatchViewpoint(relPosition).getY();
    }

    @Override
    public Float Size() {
        return (tr.translatePointToMatchViewpoint(new Point(PaintSize,PaintSize)).getX()+tr.translatePointToMatchViewpoint(new Point(PaintSize,PaintSize)).getY())/2;
    }
}
