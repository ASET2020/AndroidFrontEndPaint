package ie.tcd.asepaint2020.logic;

import java.util.Map;

public interface NetworkSyncX {
    String GetFlashMsg();

    Boolean IsGameOvered();

    Map<String, Integer> GetGameResult();
}
