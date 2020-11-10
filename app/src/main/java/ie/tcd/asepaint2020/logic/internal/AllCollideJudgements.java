package ie.tcd.asepaint2020.logic.internal;
public class AllCollideJudgements {
    public static boolean _Init;
    static {
        _Init = CollideDetectionBox._Init;
        _Init = CollideDetectionCircle._Init;
        _Init = CollideDetectionCircleAndBox._Init;
    }
    public static void loadAllJudgements(){}
}
