package ie.tcd.asepaint2020.logic.internal;

public class Pointutil {

    public static boolean isHittingTopOrBottom(Point center, Point objectcenter){
        Float Dir = Point.Direction(center,objectcenter);
        return  (Math.abs(Dir)<Math.PI/4) || (Math.abs(Dir)>(3*Math.PI/4));
    }

    public static boolean isHittingLeftOrRight(Point center, Point objectcenter){
        Float Dir = Point.Direction(center,objectcenter);
        return  (Math.abs(Dir)>Math.PI/4) && (Math.abs(Dir)<(3*Math.PI/4));
    }
}
