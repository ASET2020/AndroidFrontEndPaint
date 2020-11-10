package ie.tcd.asepaint2020.logic.internal;

public class CollideDetectionCircle implements CollideJudgementHandler {
    @Override
    public boolean IsIntersectionExist(Collidable a, Collidable b) {
        CollidableCircle ai = (CollidableCircle) a;
        CollidableCircle bi = (CollidableCircle) b;
        return Point.Distance(ai.GetOrigin(),bi.GetOrigin()) < (ai.GetSize() + bi.GetSize()) ;
    }
    public static final boolean _Init = init();
    private static boolean init(){
        CollideJudgment.RegisterCollideJudgementHandler(new CollidableClassPair(CollidableCircle.class,CollidableCircle.class),new CollideDetectionCircle());
        return true;
    }
}
