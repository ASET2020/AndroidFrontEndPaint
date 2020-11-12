package ie.tcd.asepaint2020.logic.game;

public class RemotePlayer implements Player, ie.tcd.asepaint2020.common.Player {
    private Integer ID;
    private Integer Score;
    private String name;

    private String colorFromID(){
        switch (ID){
            case 1:
                return "Green";
            case 2:
                return "Red";
            case 3:
                return "Orange";
            default:
                throw new RuntimeException("ID Unexpected");
        }
    }

    @Override
    public Float Score() {
        return null;
    }

    @Override
    public String Color() {
        return getColor();
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer ID_) {
        if (ID_>3||ID_<=0){
            throw new RuntimeException("Not Allowed to set ID of RemotePlayer >3 || <= 0.");
        }
        this.ID = ID_;
    }

    @Override
    public String getColor() {
        return colorFromID();
    }

    @Override
    public void setColor(String color) {
        throw new RuntimeException("Not Allowed to set Color of RemotePlayer, always calculated");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name_) {
        name = name_;
    }

    @Override
    public Integer getScore() {
        return Score;
    }

    public RemotePlayer(Integer ID, String name) {
        this.ID = ID;
        this.name = name;
        this.Score = 0;
    }

    @Override
    public void setScore(Integer score) {
        Score = score;
    }
}
