package ie.tcd.asepaint2020.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ie.tcd.asepaint2020.MainActivity;
import ie.tcd.asepaint2020.logic.GameStatus;

/**
 * the basic fragment class, all the fragment class need extend this class
 */
abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    /**
     * get the layout id of fragment layput
     */
    abstract int getLayoutId();

    /**
     * init the view of fragment
     */
    abstract void initView(View view);

    /**
     * get the GameStatus from Activity
     */
    public GameStatus getGameStatus() {
        return ((MainActivity) getActivity()).getGameStatus();
    }
}
