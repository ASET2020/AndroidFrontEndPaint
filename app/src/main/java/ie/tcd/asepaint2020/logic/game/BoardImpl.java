package ie.tcd.asepaint2020.logic.game;

import ie.tcd.asepaint2020.logic.internal.*;

import java.util.List;

public class BoardImpl implements CollidableBox {
    private OuterLimit screen;
    private List<PaintImpl> paintList;
    private Point CurrentLocation;
    private Point Size;

    @Override
    public Point GetOrigin() {
        return new Point(screen.GetOrigin().getX() + CurrentLocation.getX(), screen.GetOrigin().getY() + CurrentLocation.getY());
    }

    @Override
    public Point GetSize() {
        return Size;
    }

    @Override
    public Point GetPrincipleLocation() {
        return new Point(CurrentLocation.getX() + (Size.getX() / 2f), CurrentLocation.getY() + (Size.getY() / 2f));
    }

    @Override
    public Float GetPrincipleSize() {
        return Math.max(Size.getX() / 2, Size.getY() / 2);
    }

    static {
        AllCollideJudgements.loadAllJudgements();
    }

    public boolean JudgePaintHitOrMiss(CollidableCircle PaintWish) {
        //First make sure that the paint is on the screen canvas
        if (!CollideJudgment.IsIntersectionExist(PaintWish, this)) {
            return false;
        }

        //then no overlap
        for (PaintImpl pi : paintList
        ) {
            if (CollideJudgment.IsIntersectionExist(PaintWish, pi)) {
                return false;
            }
        }
        //Caller Send to server for confirmation
        return true;
    }

    public void AddConfirmedPaint(PaintImpl paint) {
        paintList.add(paint);
    }
}
