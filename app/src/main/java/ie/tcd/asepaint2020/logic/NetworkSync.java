package ie.tcd.asepaint2020.logic;

import ie.tcd.asepaint2020.common.Paint;
import ie.tcd.asepaint2020.logic.game.PaintImpl;
import ie.tcd.asepaint2020.logic.game.RemotePlayer;
import ie.tcd.asepaint2020.logic.internal.CollidableCircle;

import java.util.List;

public interface NetworkSync {
    boolean IsMatchMakingFinished();
    Float GetTimeBeforeGameStart();

    List<RemotePlayer> GetPlayers();

    void SubmitHit(CollidableCircle cc);

    List<NetworkPaint> GetNewConfirmedHits();
}
