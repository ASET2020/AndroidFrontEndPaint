package ie.tcd.asepaint2020.logic.internal;

public class CollidableBoxImpl implements Collidable, CollidableBox{

    private Point Location;
    private Point Size;

    public CollidableBoxImpl(Point location, Point size) {
        Location = location;
        Size = size;
    }

    public Point getLocation() {
        return Location;
    }

    protected void setLocation(Point location) {
        Location = location;
    }

    public Point getSize() {
        return Size;
    }

    protected void setSize(Point size) {
        Size = size;
    }


    @Override
    public Point GetPrincipleLocation() {
        return new Point(Location.getX()+(Size.getX()/2),Location.getY()+(Size.getY()/2));
    }

    @Override
    public Float GetPrincipleSize() {
        return Math.max((Size.getX()/2),(Size.getY()/2));
    }

    @Override
    public Point GetOrigin() {
        return Location;
    }

    @Override
    public Point GetSize() {
        return Size;
    }
}
