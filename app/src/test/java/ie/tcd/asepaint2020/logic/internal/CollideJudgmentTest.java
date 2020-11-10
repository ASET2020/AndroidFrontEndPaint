package ie.tcd.asepaint2020.logic.internal;
import ie.tcd.asepaint2020.logic.internal.AllCollideJudgements;

import org.junit.Test;
import static org.junit.Assert.*;

public class CollideJudgmentTest {
    static {
        AllCollideJudgements.loadAllJudgements();
    }
    @Test
    public void testIsIntersectionExist() {
        CollidableCircle cc = new CollidableCircleImpl(new Point(2f,2f),2f);
        CollidableCircle cc2 = new CollidableCircleImpl(new Point(2f,2f),2f);
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(true,result);
    }

    @Test
    public void testIsIntersectionExist2() {
        CollidableCircle cc = new CollidableCircleImpl(new Point(2f,2f),2f);
        CollidableCircle cc2 = new CollidableCircleImpl(new Point(2f,7f),2f);
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(false,result);
    }

    @Test
    public void testIsIntersectionExist3() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(1f,1f));
        CollidableBox cc2 = new CollidableBoxImpl(new Point(2f,2f),new Point(1f,1f));
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(false,result);
    }

    @Test
    public void testIsIntersectionExist4() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(1f,1f));
        CollidableBox cc2 = new CollidableBoxImpl(new Point(-2f,-2f),new Point(1f,1f));
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(false,result);
    }

    @Test
    public void testIsIntersectionExist5() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(1f,1f));
        CollidableBox cc2 = new CollidableBoxImpl(new Point(0.5f,0.5f),new Point(1f,1f));
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(true,result);
    }

    @Test
    public void testIsIntersectionExist6() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(1f,1f));
        CollidableCircle cc2 = new CollidableCircleImpl(new Point(1.5f,0f),2f);
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(true,result);
    }

    @Test
    public void testIsIntersectionExist7() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(1f,1f));
        CollidableCircle cc2 = new CollidableCircleImpl(new Point(7.5f,0f),2f);
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(false,result);
    }

    @Test
    public void testIsIntersectionExist8() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(1f,1f));
        CollidableCircle cc2 = new CollidableCircleImpl(new Point(-1.5f,0f),2f);
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(true,result);
    }

    @Test
    public void testIsIntersectionExist9() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(1f,1f));
        CollidableCircle cc2 = new CollidableCircleImpl(new Point(0f,1.5f),2f);
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(true,result);
    }

    @Test
    public void testIsIntersectionExist10() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(1f,1f));
        CollidableCircle cc2 = new CollidableCircleImpl(new Point(0f,-1.5f),2f);
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(true,result);
    }

    @Test
    public void testIsIntersectionExist11() {
        CollidableBox cc = new CollidableBoxImpl(new Point(0f,0f),new Point(2f,2f));
        CollidableCircle cc2 = new CollidableCircleImpl(new Point(2.9f,2.9f),1f);
        Boolean result = CollideJudgment.IsIntersectionExist(cc,cc2);
        assertEquals(false,result);
    }
}