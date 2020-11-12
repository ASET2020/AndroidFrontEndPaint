package ie.tcd.asepaint2020.logic;

import ie.tcd.asepaint2020.common.Paint;
import ie.tcd.asepaint2020.logic.game.RemotePlayer;
import ie.tcd.asepaint2020.logic.internal.CollidableCircle;
import ie.tcd.asepaint2020.logic.internal.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocalPlayNetworkSync implements NetworkSync {
    private Float StartTime;
    private List<NetworkPaint> buf = new LinkedList<>();

    public LocalPlayNetworkSync() {
        StartTime = Float.valueOf(System.currentTimeMillis());
    }

    @Override
    public boolean IsMatchMakingFinished() {
        return true;
    }

    @Override
    public Float GetTimeBeforeGameStart() {
        return (StartTime - Float.valueOf(System.currentTimeMillis())) / 1000f;
    }

    @Override
    public List<RemotePlayer> GetPlayers() {
        return new ArrayList<RemotePlayer>();
    }

    @Override
    public void SubmitHit(CollidableCircle cc) {
        buf.add(new NetworkPaintStub(cc.GetPrincipleSize(),cc.GetPrincipleLocation()));
    }

    @Override
    public List<NetworkPaint> GetNewConfirmedHits() {
        List<NetworkPaint> bufv = buf;
        buf = new LinkedList<>();
        return bufv;
    }
}

class NetworkPaintStub implements NetworkPaint {
    Float Size;
    Point Location;

    public NetworkPaintStub(Float size, Point location) {
        Size = size;
        Location = location;
    }

    @Override
    public Integer OwnerID() {
        return 0;
    }

    @Override
    public Point Location() {
        return Location;
    }

    @Override
    public Float Size() {
        return Size;
    }
}