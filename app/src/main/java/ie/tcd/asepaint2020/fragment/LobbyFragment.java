package ie.tcd.asepaint2020.fragment;

import android.os.Handler;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import ie.tcd.asepaint2020.R;
import ie.tcd.asepaint2020.event.StartGameEvent;

/**
 * the fragment for lobby
 */
public class LobbyFragment extends BaseFragment {

    @Override
    int getLayoutId() {
        return R.layout.fragment_lobby;
    }

    private Handler handler = new Handler();
    Timer myt = new Timer();

    @Override
    void initView(View view) {
        TimerTask Task = new TimerTask() {
            @Override
            public void run() {
                if (getGameStatus().GetGameStatus().TimeBeforeGameStart() == 0) {
                    myt.cancel();
                    EventBus.getDefault().post(new StartGameEvent());
                }
            }
        };
        myt.schedule(Task, 10, 10);
    }

}
