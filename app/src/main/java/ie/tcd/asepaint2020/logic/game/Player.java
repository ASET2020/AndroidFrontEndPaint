package ie.tcd.asepaint2020.logic.game;

/**
 * Game Player
 */
public interface Player {

    Integer getID();

    void setID(Integer ID);

    // the paint color of user
    String getColor();

    void setColor(String color);

    // the name of player
    String getName();

    void setName(String name);

    // the score of player
    Integer getScore();

    void setScore(Integer score);
}
