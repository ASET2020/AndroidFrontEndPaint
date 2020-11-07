package ie.tcd.asepaint2020.logic;

import ie.tcd.asepaint2020.common.Cursor;
import ie.tcd.asepaint2020.common.GameBoard;
import ie.tcd.asepaint2020.common.GameInput;
import ie.tcd.asepaint2020.logic.internal.Metronome;
import ie.tcd.asepaint2020.logic.internal.Point;

public class GameStatusImpl implements GameStatus {
    private static final int ScreenPointX = 1920;
    private static final int ScreenPointY = 1080;
    private Float MaxMovementSpeed = 300f;
    private Float ShootingCooldownSec = 0.8f;
    private Float CursorSize = 30f;

    private Point CursorLocationVector = new Point(0f, 0f);
    private Point CursorSpeedVector = new Point(0f, 0f);

    private Point CurrentCursorLocation = new Point(0f, 0f);
    private Float JoystickMovementDir = 0f;
    private Float JoystickMovementForce = 0f;

    private boolean IsShooting = false;

    private Integer ShootingCooldownRemain = 0;

    private Point Viewpoint;

    private Metronome mt;

    @Override
    public void SetViewpointSize(Float X, Float Y) {
        if (((X / Y) - (16f / 9f)) > 0.1) {
            throw new RuntimeException("Aspect Ratio Should be 16:9");
        }
        Viewpoint = new Point(X, Y);
    }

    @Override
    public GameBoard GetGameStatus() {

        return null;
    }

    @Override
    public void SubmitMovement(GameInput input) {
        if (!input.IsMoving()) {
            JoystickMovementForce = 0f;
        }
        JoystickMovementDir = input.GetDirection();
        JoystickMovementForce = input.GetForce();

        IsShooting = input.IsShooting();
    }

    @Override
    public Cursor GetCursor() {
        updateInternal();
        class CursorInt implements Cursor {
            Point Loc;
            Point Size;
            @Override
            public Float GetX() {
                return Loc.getX();
            }

            @Override
            public Float GetY() {
                return Loc.getY();
            }

            @Override
            public Float GetSize() {
                return (Size.getY() + Size.getX())/2;
            }
            CursorInt(Point Location, Float size){
                Loc = translatePointToMatchViewpoint(Location);
                Size = translatePointToMatchViewpoint(new Point(size,size));
            }
        }
        return new CursorInt(CursorLocationVector,CursorSize);
    }

    public void updateInternal() {

    }

    private Point translatePointToMatchViewpoint(Point pt) {
        return new Point(((Viewpoint.getX() / ScreenPointX) * pt.getX()), ((Viewpoint.getY() / ScreenPointY) * pt.getY()));
    }
}
