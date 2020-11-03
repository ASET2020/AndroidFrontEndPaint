package ie.tcd.asepaint2020.common;

import java.util.List;

public interface GameBoard {
    Float GetRelativeX();
    Float GetRelativeY();
    List<Paint> GetPaints();

    Float GetSizeX();
    Float GetSizeY();

    Boolean IsGameStarted();
    Float TimeBeforeGameStart();

    Boolean IsGameEnded();
    Player GetOwnStatus();
    List<Player> GetAllStatus();
}
