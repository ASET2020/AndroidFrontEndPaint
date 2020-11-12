package ie.tcd.asepaint2020.logic.game;

public interface Player {

    Integer getID();

    void setID(Integer ID);


    String getColor();

    void setColor(String color);

    String getName();

    void setName(String name);

    Integer getScore();

    void setScore(Integer score);
}
