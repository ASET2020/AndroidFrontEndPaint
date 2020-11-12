package ie.tcd.asepaint2020.logic.internal;

import ie.tcd.asepaint2020.logic.internal.Point;

public interface ViewPointTranslator {
    Point translatePointToMatchViewpoint(Point pt);
}
