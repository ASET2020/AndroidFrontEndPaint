package ie.tcd.asepaint2020.logic.internal;

public class CollidableCircleImpl implements CollidableCircle, Collidable{
    private Point Location;

    public Point getLocation() {
        return Location;
    }

    public void setLocation(Point location) {
        Location = location;
    }

    public Float getSize() {
        return Size;
    }

    public void setSize(Float size) {
        Size = size;
    }

    public CollidableCircleImpl(Point location, Float size) {
        Location = location;
        Size = size;
    }

    private Float Size;
    @Override
    public Point GetOrigin() {
        return getLocation();
    }

    @Override
    public Float GetSize() {
        return getSize();
    }

    @Override
    public Point GetPrincipleLocation() {
        return getLocation();
    }

    @Override
    public Float GetPrincipleSize() {
        return getSize();
    }
}
