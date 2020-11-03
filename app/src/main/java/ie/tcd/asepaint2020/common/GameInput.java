package ie.tcd.asepaint2020.common;

public interface GameInput {
    Boolean IsMoving();
    Float GetDirection();
    Float GetForce();
    Boolean IsShooting();
}
