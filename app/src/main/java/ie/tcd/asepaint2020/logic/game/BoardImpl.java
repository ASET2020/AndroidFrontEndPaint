package ie.tcd.asepaint2020.logic.game;

import android.util.Log;
import ie.tcd.asepaint2020.logic.internal.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class BoardImpl implements CollidableBox, TickReceiver {
    private OuterLimit screen;
    private List<PaintImpl> paintList;

    public void setSeed(Integer seed) {
        Seed = seed;
    }

    private Integer Seed = 0;
    private Float CurrentSecond2 = 0f;

    public List<PaintImpl> getPaintList() {
        return paintList;
    }

    public Point getCurrentLocation() {
        return CurrentLocation;
    }

    public Point getSize() {
        return Size;
    }

    private Point CurrentLocation;
    private Point Size;

    private Point MovementVector = new Point(480f, 360f);

    public BoardImpl(OuterLimit screen) {
        this.screen = screen;
        this.paintList = new LinkedList<>();
        this.CurrentLocation = new Point(0f, 0f);
        this.Size = new Point(512f, 680f);
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

    //PaintWish's Location is relative to screen
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

    public Point GetRelativeLocation(Point absLoc) {
        return new Point(absLoc.getX() - CurrentLocation.getX(), absLoc.getY() - CurrentLocation.getY());
    }

    //paint's Location is relative to canvas
    public void AddConfirmedPaint(PaintImpl paint) {
        paintList.add(paint);
    }

    public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return digest;
    }

    @Override
    public void Tick(Float tickScaler) {
        //Bound Check and update speed
        CurrentSecond2+=tickScaler;

        Float RandNow = ((float) ((hmacDigest(String.valueOf(Seed.hashCode()) , String.valueOf(Integer.valueOf(Math.round(CurrentSecond2 / 3))),"HmacSHA1").hashCode()) % 128) / 128f);

        Log.d("BoardImpl",RandNow.toString());
        Log.d("BoardImpl",Seed.toString());
        Log.d("BoardImpl", String.valueOf(Integer.valueOf(Seed.hashCode())));

        MovementVector.setY(((float) Math.sin(RandNow * Math.PI) * tickScaler * 900) + MovementVector.getY());
        MovementVector.setX(((float) Math.cos(RandNow * Math.PI) * tickScaler * 900) + MovementVector.getX());

        Point loc = new Point(CurrentLocation.getX() + MovementVector.getX() * tickScaler, CurrentLocation.getY() + MovementVector.getY() * tickScaler);

        if (!CollideJudgment.IsIntersectionExist(new CollidableBoxImpl(loc, this.Size), screen)) {
            if (loc.getY() > screen.GetPrincipleLocation().getY()) {
                MovementVector.setY(-Math.abs(MovementVector.getY()));
            } else {
                MovementVector.setY(Math.abs(MovementVector.getY()));
            }

            if (loc.getX() > screen.GetPrincipleLocation().getX()) {
                MovementVector.setX(-Math.abs(MovementVector.getX()));
            } else {
                MovementVector.setX(Math.abs(MovementVector.getX()));
            }
        }
        CurrentLocation = loc;

    }
}
