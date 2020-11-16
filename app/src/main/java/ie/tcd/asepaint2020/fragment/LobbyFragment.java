package ie.tcd.asepaint2020.fragment;

import android.view.View;

import ie.tcd.asepaint2020.MainActivity;
import org.greenrobot.eventbus.EventBus;

import ie.tcd.asepaint2020.R;
import ie.tcd.asepaint2020.event.StartGameEvent;

public class LobbyFragment extends BaseFragment {

    @Override
    int getLayoutId() {
        return R.layout.fragment_lobby;
    }

    @Override
    void initView(View view) {
        if(((MainActivity) getActivity()).getGameStatus().GetGameStatus().TimeBeforeGameStart()<=0){
            EventBus.getDefault().post(new StartGameEvent());
        }

        view.findViewById(R.id.btn_start_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new StartGameEvent());
            }
        });
    }

}
