package ie.tcd.asepaint2020.logic.internal;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;

public class CollidableBoxImplTest{

    @Test
    public void TestImpl() {
        CollidableBoxImpl cbi = new CollidableBoxImpl(new Point(0f,1f),new Point(5f,6f));
        assertEquals(0f,cbi.getLocation().getX(),0.01);
        assertEquals(1f,cbi.getLocation().getY(),0.01);
        assertEquals(5f,cbi.getSize().getX(),0.01);
        assertEquals(6f,cbi.getSize().getY(),0.01);
    }
}