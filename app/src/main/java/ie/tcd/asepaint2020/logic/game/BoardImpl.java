package ie.tcd.asepaint2020.logic.game;

import ie.tcd.asepaint2020.logic.internal.*;

import java.util.LinkedList;
import java.util.List;

public class BoardImpl implements CollidableBox, TickReceiver {
    private OuterLimit screen;
    private List<PaintImpl> paintList;
    private Point CurrentLocation;
    private Point Size;

    private Point MovementVector = new Point(0f,0f);

    public BoardImpl(OuterLimit screen) {
        this.screen = screen;
        this.paintList = new LinkedList<>();
        this.CurrentLocation = new Point(0f,0f);
        this.Size = new Point(320f,180f);
    }

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

    @Override
    public void Tick(Float tickScaler) {

    }
}
