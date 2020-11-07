package ie.tcd.asepaint2020.logic;

import ie.tcd.asepaint2020.common.Cursor;
import ie.tcd.asepaint2020.common.GameBoard;
import ie.tcd.asepaint2020.common.GameInput;
import ie.tcd.asepaint2020.logic.internal.Point;

public class GameStatusImpl implements GameStatus{
    private Float MaxMovementSpeed = 30f;
    private Float ShootingCooldown = 3f;

    private Point CurrentUserLocation = new Point(0f,0f);

    private Point Viewpoint;
    @Override
    public void SetViewpointSize(Float X, Float Y) {
        Viewpoint = new Point(X,Y);
    }

    @Override
    public GameBoard GetGameStatus() {
        return null;
    }

    @Override
    public void SubmitMovement(GameInput input) {

    }

    @Override
    public Cursor GetCursor() {
        return null;
    }
}
