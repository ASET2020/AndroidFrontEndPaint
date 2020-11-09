package ie.tcd.asepaint2020;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ie.tcd.asepaint2020.event.EnterLobbyEvent;
import ie.tcd.asepaint2020.event.StartGameEvent;
import ie.tcd.asepaint2020.fragment.GameFragment;
import ie.tcd.asepaint2020.fragment.LobbyFragment;
import ie.tcd.asepaint2020.fragment.NameInputFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        replaceFragment(new NameInputFragment());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void enterLobby(EnterLobbyEvent event) {
        // TODO: 09/11/2020  new LobbyFragment should have user name parameters
        replaceFragment(new LobbyFragment());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startGame(StartGameEvent event) {
        // TODO: 09/11/2020  should have game init data
        replaceFragment(new GameFragment());
    }
}