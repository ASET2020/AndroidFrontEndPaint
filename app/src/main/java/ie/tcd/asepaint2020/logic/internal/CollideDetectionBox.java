package ie.tcd.asepaint2020.logic.internal;

public class CollideDetectionBox implements CollideJudgementHandler {

    @Override
    public boolean IsIntersectionExist(Collidable a, Collidable b) {
        CollidableBox ai = (CollidableBox) a;
        CollidableBox bi = (CollidableBox) b;
        return (ai.GetOrigin().getX() < (bi.GetOrigin().getX() + bi.GetSize().getX()))
                &&
                ((ai.GetOrigin().getX() + ai.GetSize().getX()) > bi.GetOrigin().getX())
                &&
                (ai.GetOrigin().getY() < (bi.GetOrigin().getY() + bi.GetSize().getY()))
                &&
                ((ai.GetOrigin().getY() + ai.GetSize().getY()) > bi.GetOrigin().getY());
    }
    public static final boolean _Init = init();
    private static boolean init(){
        CollideJudgment.RegisterCollideJudgementHandler(new CollidableClassPair(CollidableBox.class,CollidableBox.class),new CollideDetectionBox());
        return true;
    }

}
