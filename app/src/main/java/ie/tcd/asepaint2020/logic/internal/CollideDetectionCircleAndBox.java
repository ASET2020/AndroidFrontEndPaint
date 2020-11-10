package ie.tcd.asepaint2020.logic.internal;

public class CollideDetectionCircleAndBox implements CollideJudgementHandler{

    @Override
    public boolean IsIntersectionExist(Collidable a, Collidable b) {
        CollidableCircle ia = (CollidableCircle) a;
        CollidableBox ib = (CollidableBox) b;
        Float Dir = Point.Direction(ia.GetPrincipleLocation(),ib.GetPrincipleLocation());
        Float Dist = Point.Distance(ia.GetPrincipleLocation(),ib.GetPrincipleLocation());
        Float BoxPrincipleDist = (float) Math.min(Math.abs((ib.GetSize().getX()/Math.sin(Dir))),Math.abs((ib.GetSize().getY()/Math.cos(Dir)))) / 2f ;
        return BoxPrincipleDist + ia.GetSize() > Dist;
    }
    public static final boolean _Init = init();
    private static boolean init(){
        CollideJudgment.RegisterCollideJudgementHandler(new CollidableClassPair(CollidableCircle.class,CollidableBox.class),new CollideDetectionCircleAndBox());
        return true;
    }
}
