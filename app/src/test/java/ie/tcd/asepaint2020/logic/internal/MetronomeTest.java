package ie.tcd.asepaint2020.logic.internal;

import org.junit.Test;

import static org.junit.Assert.*;

public class MetronomeTest {
    @Test
    public void TestImpl() {
        Metronome mt = new Metronome(60);
        Metronome.GetTime();
    }

    @Test
    public void TestImpl2() throws InterruptedException {
        Metronome mt = new Metronome(60);
        Thread.sleep(1000);
        class Counter implements TickReceiver{
            public Float counter = 0f;
            @Override
            public void Tick(Float tickScaler) {
                counter += tickScaler;
            }
        }
        Counter ct = new Counter();
        mt.Pulse(ct);
        assertEquals(1f,ct.counter,0.01f);
    }
}