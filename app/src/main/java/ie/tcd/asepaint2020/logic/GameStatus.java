package ie.tcd.asepaint2020.logic;

import ie.tcd.asepaint2020.common.Cursor;
import ie.tcd.asepaint2020.common.GameBoard;
import ie.tcd.asepaint2020.common.GameInput;

public interface GameStatus {
    void SetViewpointSize(Float X,Float Y);
    GameBoard GetGameStatus();
    void SubmitMovement(GameInput input);
    Cursor GetCursor();
    String GetFlashMsg();
    void OpenConnection(String s);
}
