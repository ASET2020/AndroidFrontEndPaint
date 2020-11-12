package ie.tcd.asepaint2020.common;

import java.util.List;

public interface GameBoard {
    //The location of printing board
    Float GetRelativeX();
    Float GetRelativeY();
    List<Paint> GetPaints();

    Float GetSizeX();
    Float GetSizeY();

    Boolean IsGameStarted();
    // < -1 if remain time unknown
    Float TimeBeforeGameStart();

    Boolean IsGameEnded();
    Player GetOwnStatus();
    List<Player> GetAllStatus();
}
