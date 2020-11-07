package ie.tcd.asepaint2020.logic.internal;

import android.os.SystemClock;

public class Metronome {
    public Metronome(Integer tickPerSecond) {
        TickPerSecond = tickPerSecond;
        ResetTimer();
    }

    public static long GetTime() {
        if (UnitTestDetector.isJUnitTest()) {
            return System.nanoTime();
        }
        //MUST USE kernel/time/hrtimer.c clock_gettime(CLOCK_BOOTTIME)
        //This time will move forward regardless set time and device hibernation
        //System.nanoTime() is only used for mocking
        return SystemClock.elapsedRealtimeNanos();
    }

    private Integer TickPerSecond;

    private long lastTime;

    public void Pulse(TickReceiver tr) {
        Integer ticks = updateTick();
        Float scalier = 1.0f / TickPerSecond;
        for (int i = 0; i < ticks; i++) {
            tr.Tick(scalier);
        }
    }

    private Integer updateTick() {
        long currentTime = Metronome.GetTime();
        long timeChange = currentTime - lastTime;
        long nanoSecPerTick = (long) (1e9 / (long) TickPerSecond);
        long ticks = (timeChange / nanoSecPerTick);
        long nanoMoveForward = ticks * nanoSecPerTick;
        lastTime += nanoMoveForward;
        return (int) ticks;

    }

    public void ResetTimer(){
        lastTime = Metronome.GetTime();
    }

}
