package ie.tcd.asepaint2020.fragment;

import android.os.Handler;
import android.view.View;

import ie.tcd.asepaint2020.MainActivity;
import org.greenrobot.eventbus.EventBus;

import ie.tcd.asepaint2020.R;
import ie.tcd.asepaint2020.event.StartGameEvent;

import java.util.Timer;
import java.util.TimerTask;

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
                if(((MainActivity) getActivity()).getGameStatus().GetGameStatus().TimeBeforeGameStart()==0){
                    myt.cancel();
                    EventBus.getDefault().post(new StartGameEvent());
                }
            }
        };
        myt.schedule(Task,10,10);


        view.findViewById(R.id.btn_start_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new StartGameEvent());
            }
        });
    }

}
