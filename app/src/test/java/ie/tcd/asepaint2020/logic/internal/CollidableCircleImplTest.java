package ie.tcd.asepaint2020.logic.internal;

import org.junit.Test;

import static org.junit.Assert.*;

public class CollidableCircleImplTest {
    @Test
    public void TestImpl() {
        CollidableCircleImpl cci = new CollidableCircleImpl(new Point(0f,1f),2f);
        assertEquals(0f,cci.getLocation().getX(),0.01f);
        assertEquals(1f,cci.getLocation().getY(),0.01f);
        assertEquals(2f,cci.getSize(),0.01f);
    }
}