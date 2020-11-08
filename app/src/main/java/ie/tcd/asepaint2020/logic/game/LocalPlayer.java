package ie.tcd.asepaint2020.logic.game;

public class LocalPlayer implements Player, ie.tcd.asepaint2020.common.Player {
    private final int ID = 0;
    private int Score = 0;

    private String Name;

    @Override
    public String getColor() {
        return "Blue";
    }

    @Override
    public void setColor(String color) {
        throw new RuntimeException("Not Allowed to set Color of LocalPlayer, always Blue");
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String name) {
        Name = name;
    }

    @Override
    public Integer getScore() {
        return Score;
    }

    @Override
    public void setScore(Integer score) {
        throw new RuntimeException("Not Allowed to set ID of LocalPlayer, it is calculated automatically");
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer ID) {
        throw new RuntimeException("Not Allowed to set ID of LocalPlayer, always 0");
    }

    @Override
    public Float Score() {
        return Float.valueOf(getScore());
    }

    @Override
    public String Color() {
        return getColor();
    }
}
