package ie.tcd.asepaint2020.logic;


import android.os.Handler;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ie.tcd.asepaint2020.logic.game.RemotePlayer;
import ie.tcd.asepaint2020.logic.internal.CollidableCircle;
import ie.tcd.asepaint2020.logic.internal.Point;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class BackendNetworkSync implements NetworkSync, NetworkSyncX {
    OkHttpClient client = new OkHttpClient();
    String Endpoint = "://asepaint.kkdev.org:8080/";
    Boolean MatchMakingFinished = false;

    Boolean GameReady = false;
    Boolean GameOver = false;

    String userNickName = "Nameless Hero";

    private Float StartTime;

    Integer seed;
    Integer userColor;
    Integer roundID;
    Integer userID;

    Handler handler = new Handler();

    String flashmsg;

    Map<Integer, RemotePlayer> playerMap = new HashMap<>();
    Map<Integer, Integer> playerIDMap = new HashMap<>();

    Map<String, Integer> scoreboard = new HashMap<>();

    public BackendNetworkSync(String name) {
        userNickName = name;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String s = enroll();
                    final JSONObject resp = new JSONObject(s);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                seed = resp.getInt("seed");
                                userColor = resp.getInt("userColor");
                                roundID = resp.getInt("roundId");
                                userID = resp.getInt("userId");
                                MatchMakingFinished = true;
                                Log.d("BackendNetworkSync", s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Integer userID = resp.getInt("userId");
                    startws(userID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private String enroll() throws IOException {
        Request req = new Request.Builder().url("http" + Endpoint + "lobby").build();
        try (Response response = client.newCall(req).execute()) {
            return response.body().string();
        }
    }

    class Ws extends WebSocketListener {
        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);
            Log.d("BackendNetworkSync", response.toString());
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            super.onMessage(webSocket, text);
            Log.d("BackendNetworkSync", text);
            final NetworkPaintSync np = new NetworkPaintSync();
            try {
                if (text.startsWith("success")) {
                    MatchMakingFinished = true;
                    return;
                }
                final JSONObject jo = new JSONObject(text);
                String Event = jo.getString("eventType");
                switch (Event) {
                    case "connect":
                        break;
                    case "gameStart":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject UsernameObj = null;
                                try {
                                    UsernameObj = jo.getJSONObject("userName");

                                    Integer i = 0;
                                    for (Iterator<String> it = UsernameObj.keys(); it.hasNext(); ) {
                                        i++;
                                        String s = it.next();
                                        Integer thispid = Integer.valueOf(s);
                                        playerIDMap.put(thispid, i);
                                        RemotePlayer rp = new RemotePlayer(i, UsernameObj.getString(s));
                                        playerMap.put(thispid, rp);
                                    }

                                    GameReady = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case "HitNeg":
                        flashmsg = "Missed";
                        break;
                    case "HitPos":
                        np.FromJson(text);
                        if (np.ID == userID) {
                            flashmsg = "Hitted";
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                buf.add(np);
                            }
                        });
                        break;
                    case "gameOver":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject UsernameObj = null;
                                try {
                                    UsernameObj = jo.getJSONObject("score");

                                    Integer i = 0;
                                    for (Iterator<String> it = UsernameObj.keys(); it.hasNext(); ) {
                                        i++;
                                        String s = it.next();

                                        scoreboard.put(s, UsernameObj.getInt(s));
                                    }

                                    GameOver = true;
                                    ws.close(1000,"");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    default:
                        Log.d("BackendNetworkSync", "Unknown Event Type" + Event);
                        return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    WebSocket ws;

    private void startws(Integer userID) {
        Request request = new Request.Builder().url("ws" + Endpoint + "websocket/" + userID.toString() + "-" + userNickName).build();

        ws = client.newWebSocket(request, new Ws());

        client.dispatcher().executorService().shutdown();
    }

    private void uploadHit(CollidableCircle cc) {
        NetworkPaintSync nps = new NetworkPaintSync();
        nps.Location = cc.GetPrincipleLocation();
        nps.Size = Math.round(cc.GetPrincipleSize());
        nps.Event = "Hit";
        try {
            ws.send(nps.ToJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean IsMatchMakingFinished() {
        return MatchMakingFinished;
    }

    @Override
    public Float GetTimeBeforeGameStart() {
        if (!GameReady) {
            return 1f;
        }
        StartTime = Float.valueOf(System.currentTimeMillis());
        return (StartTime - Float.valueOf(System.currentTimeMillis())) / 1000f;
    }

    @Override
    public List<RemotePlayer> GetPlayers() {
        List<RemotePlayer> al = new ArrayList<RemotePlayer>();

        for (Map.Entry<Integer, RemotePlayer> integerRemotePlayerEntry : playerMap.entrySet()) {
            al.add(integerRemotePlayerEntry.getValue());
        }


        return al;
    }

    private List<NetworkPaint> buf = new LinkedList<>();

    @Override
    public void SubmitHit(CollidableCircle cc) {
        uploadHit(cc);
    }

    @Override
    public List<NetworkPaint> GetNewConfirmedHits() {
        List<NetworkPaint> bufv = buf;
        buf = new LinkedList<>();
        return bufv;
    }

    @Override
    public String GetFlashMsg() {
        String fm = flashmsg;
        flashmsg = null;
        return fm;
    }

    @Override
    public Boolean IsGameOvered() {
        return GameOver;
    }

    @Override
    public Map<String, Integer> GetGameResult() {
        return scoreboard;
    }

    @Override
    public Integer GetSeed() {
        return seed;
    }

    class NetworkPaintSync implements NetworkPaint {
        public String Event;
        public Integer Size;
        public Integer ID;
        public Integer LID;
        public Point Location;

        public void FromJson(String s) throws JSONException {
            JSONObject jo = new JSONObject(s);
            Event = jo.getString("eventType");
            JSONObject detail = jo.getJSONObject("detail");
            Size = detail.getInt("Size");
            ID = jo.getInt("userId");
            LID = playerIDMap.get(ID);
            Location = new Point((float) detail.getInt("LocationX"), (float) detail.getInt("LocationY"));
        }

        public String ToJson() throws JSONException {
            JSONObject jo = new JSONObject();
            jo.put("eventType", Event);
            JSONObject detail = new JSONObject();
            detail.put("Size", Size);
            detail.put("ID", ID);
            detail.put("LocationX", Math.round(Location.getX()));
            detail.put("LocationY", Math.round(Location.getY()));
            jo.put("detail", detail);
            return jo.toString();

        }

        @Override
        public Integer OwnerID() {
            if (ID.equals(userID)) {
                return 0;
            } else {
                return LID;
            }

        }

        @Override
        public Point Location() {
            return Location;
        }

        @Override
        public Float Size() {
            return (float) Size;
        }
    }

}

