package ie.tcd.asepaint2020.logic.internal;

import java.util.Objects;

public class CollidableClassPair {
    private Class<? extends Collidable> a;
    private Class<? extends Collidable> b;

    public Class<? extends Collidable> getA() {
        return a;
    }

    public void setA(Class<? extends Collidable> a) {
        this.a = a;
    }

    public CollidableClassPair(Class<? extends Collidable> a, Class<? extends Collidable> b) {
        this.a = a;
        this.b = b;
    }

    public CollidableClassPair(Collidable a, Collidable b) {
        this.a = a.getClass();
        this.b = b.getClass();
    }

    public Class<? extends Collidable> getB() {
        return b;
    }

    public void setB(Class<? extends Collidable> b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollidableClassPair)) return false;
        CollidableClassPair that = (CollidableClassPair) o;
        return a.equals(that.a) &&
                b.equals(that.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    public boolean Reversible(){
        return a.equals(b);
    }
}
