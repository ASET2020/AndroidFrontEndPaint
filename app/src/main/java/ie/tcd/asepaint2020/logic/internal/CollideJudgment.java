package ie.tcd.asepaint2020.logic.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollideJudgment {
    private static final Map<CollidableClassPair,CollideJudgementHandler> dispatch = new HashMap<>();


    public static void RegisterCollideJudgementHandler(CollidableClassPair ccp, CollideJudgementHandler cjh){
        dispatch.put(ccp,cjh);
        if (!ccp.Reversible()) {
            dispatch.put(new CollidableClassPair(ccp.getB(),ccp.getA()),new ReversedCollideJudgementHandler(cjh));
        }
    }

    public static boolean IsIntersectionExist(Collidable a, Collidable b){
        CollidableClassPair ccp = new CollidableClassPair(a,b);
        CollideJudgementHandler cjh = null;
        for ( CollidableClassPair ccpl: dispatch.keySet()) {
            if(ccpl.getA().isAssignableFrom(a.getClass()) && ccpl.getB().isAssignableFrom(b.getClass())){
                cjh = dispatch.get(ccpl);
            }
        }
        if (cjh == null){
            Logger.getLogger("CollideJudgment").log(Level.WARNING,String.format("No way to judge collide between %s, %s\n",
                    ccp.getA().getCanonicalName(),ccp.getB().getCanonicalName()));
            return Point.Distance(a.GetPrincipleLocation(), b.GetPrincipleLocation()) < a.GetPrincipleSize() + b.GetPrincipleSize();
        }
        return cjh.IsIntersectionExist(a,b);
    }
}
