package ie.tcd.asepaint2020.logic;

import ie.tcd.asepaint2020.common.Cursor;
import ie.tcd.asepaint2020.common.GameBoard;
import ie.tcd.asepaint2020.common.GameInput;

public class GameStatusImpl implements GameStatus{
    private Float MaxMovementSpeed = 30f;
    private Float ShootingCooldown = 3f;

    private Float CurrentUserLocationX = 0f;
    private Float CurrentUserLocationY = 0f;

    private Float ViewpointX;
    private Float ViewpointY;
    @Override
    public void SetViewpointSize(Float X, Float Y) {
        ViewpointX=X;
        ViewpointY=Y;
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
