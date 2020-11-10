package ie.tcd.asepaint2020.logic.internal;

public class Point {
    private Float X;
    private Float Y;

    public Float getX() {
        return X;
    }

    public Point(Float x, Float y) {
        X = x;
        Y = y;
    }

    public void setX(Float x) {
        X = x;
    }

    public Float getY() {
        return Y;
    }

    public void setY(Float y) {
        Y = y;
    }

    public static Float Distance(Point a, Point b){
        return (float) Math.pow(Math.pow(a.X - b.X, 2) + Math.pow(a.Y - b.Y, 2), 0.5);
    }

    public static Float Direction(Point a, Point b){
        return (float) Math.atan(- (a.X - b.X) / (a.Y - b.Y));
    }
}
