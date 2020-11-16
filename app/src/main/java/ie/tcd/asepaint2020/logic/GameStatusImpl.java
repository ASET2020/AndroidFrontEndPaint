package ie.tcd.asepaint2020.logic;

import ie.tcd.asepaint2020.common.Player;
import ie.tcd.asepaint2020.common.*;
import ie.tcd.asepaint2020.logic.game.*;
import ie.tcd.asepaint2020.logic.internal.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameStatusImpl implements GameStatus, ViewPointTranslator, TickReceiver, GameBoard {
    private static final int ScreenPointX = 1080;
    private static final int ScreenPointY = 1920;
    private Float MaxMovementSpeed = 300f;
    private Float ShootingCooldownSec = 0.8f;
    private Float CursorSize = 30f;

    private Point CursorLocationVector = new Point(0f, 0f);
    private Point CursorSpeedVector = new Point(0f, 0f);

    //private Point CurrentCursorLocation = new Point(0f, 0f);
    private Float JoystickMovementDir = 0f;
    private Float JoystickMovementForce = 0f;

    private boolean IsShooting = false;

    private Float ShootingCooldownRemain = 0f;

    private Point Viewpoint;

    private Metronome mt;

    private OuterLimit viewpointLimit;
    private BoardImpl CanvasBoard;

    private final Integer TickPerSecond = 60;

    private Float SecondsAfterGameStart;

    private final Float SecondsGameRoundTime = 180f;

    private LocalPlayer LocalPlayer = new LocalPlayer("Us");

    private List<RemotePlayer> remotePlayers;

    private NetworkSync ns;
    private NetworkSyncFactory nsf;

    private String Flashmsg = "";
    private Float Flashremain = 0f;

    public GameStatusImpl(NetworkSyncFactory ns) {
        this.nsf = ns;
    }

    @Override
    public void SetViewpointSize(Float X, Float Y) {
//        if (((X / Y) - (9f / 16f)) > 0.1) {
//            throw new RuntimeException("Aspect Ratio Should be 16:9");
//        }
        if (Viewpoint != null) {
            throw new RuntimeException("Do not support the change of Viewpoint");
        }
        Viewpoint = new Point(X, Y);
        viewpointLimit = new OuterLimit(X, Y);
    }

    @Override
    public GameBoard GetGameStatus() {
        pollSync();
        return this;
    }

    private void pollSync() {
        updateInternal();
    }

    @Override
    public void SubmitMovement(GameInput input) {
        if (!input.IsMoving()) {
            JoystickMovementForce = 0f;
        } else {
            JoystickMovementForce = input.GetForce();
        }
        JoystickMovementDir = input.GetDirection();

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
                return (Size.getY() + Size.getX()) / 2;
            }

            CursorInt(Point Location, Float size) {
                Loc = translatePointToMatchViewpoint(Location);
                Size = translatePointToMatchViewpoint(new Point(size, size));
            }
        }
        return new CursorInt(CursorLocationVector, CursorSize);
    }

    @Override
    public String GetFlashMsg() {
        return Flashmsg;
    }

    @Override
    public void OpenConnection(String s) {
        ns = nsf.Create(s);
    }

    public void updateInternal() {

        if ((ns.IsMatchMakingFinished() && this.SecondsAfterGameStart == null) || (this.SecondsAfterGameStart != null && this.SecondsAfterGameStart <= 0)) {
            this.SecondsAfterGameStart = -ns.GetTimeBeforeGameStart();
            if (Viewpoint == null) {
                return;
            }
            this.remotePlayers = ns.GetPlayers();
            if (this.SecondsAfterGameStart >= 0) {
                if (CanvasBoard == null) {
                    initGame();
                }
            }
        }
        if (Viewpoint == null) {
            return;
        }
        if (this.SecondsAfterGameStart != null && this.SecondsAfterGameStart >= 0) {
            mt.Pulse(this);
            this.remotePlayers = ns.GetPlayers();
            List<NetworkPaint> networkPaints = ns.GetNewConfirmedHits();
            for (NetworkPaint np : networkPaints
            ) {
                if (np.OwnerID() == 0) {
                    PaintImpl pi = new PaintImpl(CanvasBoard, np.Location(), np.Size(), LocalPlayer, this);
                    updateBoardWithPaint(pi);
                } else {
                    PaintImpl pi = new PaintImpl(CanvasBoard, np.Location(), np.Size(), remotePlayers.get(np.OwnerID() - 1), this);
                    updateBoardWithPaint(pi);
                }


            }
        }
    }

    @Override
    public Point translatePointToMatchViewpoint(Point pt) {
        return new Point(((Viewpoint.getX() / ScreenPointX) * pt.getX()), ((Viewpoint.getY() / ScreenPointY) * pt.getY()));
    }

    private void initGame() {
        CanvasBoard = new BoardImpl(viewpointLimit);
        mt = new Metronome(TickPerSecond);
    }

    static {
        AllCollideJudgements.loadAllJudgements();
    }

    @Override
    public void Tick(Float tickScaler) {
        SecondsAfterGameStart += tickScaler;

        //Reduce the cooldown counter of cursor
        ShootingCooldownRemain -= tickScaler;
        if (ShootingCooldownRemain < 0) {
            ShootingCooldownRemain = 0f;
        }

        Flashremain -= tickScaler;
        if (Flashremain < 0) {
            Flashremain = 0f;
            Flashmsg = "";
        }

        if(NetworkSyncX.class.isAssignableFrom(ns.getClass())){
            NetworkSyncX nsx = (NetworkSyncX) ns;
            String fsg = nsx.GetFlashMsg();
            if(fsg!=null){
                Flashmsg = fsg;
                Flashremain = 1f;
            }

        }

        //Cursor Movement Attrition

        //So that after one second of inactivity, the movement on each direction reduced to half of previous second
        Float AttritionFactor = (float) Math.pow(0.5, tickScaler);

        Float MovementFactor = 1 - AttritionFactor;

        //Cursor Movement Acceleration
        Float XMovement = (float) Math.cos(JoystickMovementDir) * JoystickMovementForce;
        Float YMovement = -(float) Math.sin(JoystickMovementDir) * JoystickMovementForce;

        CursorSpeedVector.setX(Math.min((CursorSpeedVector.getX() * AttritionFactor) + (XMovement * MovementFactor), MaxMovementSpeed));
        CursorSpeedVector.setY(Math.min((CursorSpeedVector.getY() * AttritionFactor) + (YMovement * MovementFactor), MaxMovementSpeed));

        //Bound Check and update speed
        /*
        Point loc = new Point(CursorLocationVector.getX() + CursorSpeedVector.getX() * tickScaler,CursorLocationVector.getY()+ CursorSpeedVector.getY()*tickScaler);

        if (!CollideJudgment.IsIntersectionExist(new CollidableCircleImpl(loc,CursorSize), viewpointLimit)){
            if (Pointutil.isHittingTopOrBottom(viewpointLimit.GetPrincipleLocation(),loc)){
                CursorSpeedVector.setY( - CursorSpeedVector.getY());
            }
            if (Pointutil.isHittingLeftOrRight(viewpointLimit.GetPrincipleLocation(),loc)){
                CursorSpeedVector.setX( - CursorSpeedVector.getX());
            }
        }else{
            CursorLocationVector = loc;
        }*/

        CursorLocationVector = new Point(CursorLocationVector.getX() + XMovement * tickScaler, CursorLocationVector.getY() + YMovement * tickScaler);

        //Ask Canvas board to update
        CanvasBoard.Tick(tickScaler);


        //Do the shooting
        if (GameInSession()) {
            if (ShootingCooldownRemain <= 0 && IsShooting) {
                ShootingCooldownRemain = ShootingCooldownSec;
                //Judge If There is a hit
                CollidableCircle cc = new CollidableCircleImpl(CursorLocationVector, CursorSize);
                boolean hit = CanvasBoard.JudgePaintHitOrMiss(cc);
                if (hit) {
                    submitHitToServer(cc);
                }else {
                    Flashmsg = "Missed";
                    Flashremain = 1f;
                }
            }
        }
    }

    private void submitHitToServer(CollidableCircle cc) {
        //Translate to canvas reference first
        ns.SubmitHit(new CollidableCircleImpl(CanvasBoard.GetRelativeLocation(cc.GetPrincipleLocation()), cc.GetPrincipleSize()));
    }


    private void updateBoardWithPaint(PaintImpl paint) {
        CanvasBoard.AddConfirmedPaint(paint);
    }


    private boolean GameInSession() {
        return GameNotEnded() && GameStarted();
    }

    private boolean GameStarted() {
        return SecondsAfterGameStart >= 0;
    }

    private boolean GameNotEnded() {
        return SecondsAfterGameStart < SecondsGameRoundTime;
    }


    @Override
    public Float GetRelativeX() {
        pollSync();
        if (CanvasBoard == null) {
            return 0f;
        }
        return translatePointToMatchViewpoint(CanvasBoard.getCurrentLocation()).getX();
    }

    @Override
    public Float GetRelativeY() {
        pollSync();
        if (CanvasBoard == null) {
            return 0f;
        }
        return translatePointToMatchViewpoint(CanvasBoard.getCurrentLocation()).getY();
    }

    @Override
    public List<Paint> GetPaints() {
        pollSync();
        List<Paint> pt = new LinkedList<>();
        if (CanvasBoard == null) {
            return pt;
        }
        //Because java cannot Understand Interface in generics
        List<PaintImpl> paintList = CanvasBoard.getPaintList();

        for (PaintImpl pa : paintList
        ) {
            pt.add(pa);
        }
        return pt;
    }

    @Override
    public Float GetSizeX() {
        pollSync();
        if (CanvasBoard == null) {
            return 0f;
        }
        return CanvasBoard.getSize().getX();
    }

    @Override
    public Float GetSizeY() {
        pollSync();
        if (CanvasBoard == null) {
            return 0f;
        }
        return CanvasBoard.getSize().getY();
    }

    @Override
    public Boolean IsGameStarted() {
        return GameStarted();
    }

    @Override
    public Float TimeBeforeGameStart() {
        updateInternal();
        if (SecondsAfterGameStart == null) {
            return -2f;
        }

        if (SecondsAfterGameStart >= 0) {
            return 0f;
        }

        return -SecondsAfterGameStart;
    }

    @Override
    public Boolean IsGameEnded() {
        return !GameNotEnded();
    }

    @Override
    public Player GetOwnStatus() {
        return LocalPlayer;
    }

    @Override
    public List<Player> GetAllStatus() {
        List<Player> ret = new ArrayList<>();
        ret.add(LocalPlayer);
        if (remotePlayers != null) {
            for (RemotePlayer rp : remotePlayers
            ) {
                ret.add(rp);
            }
        }
        return ret;
    }
}
