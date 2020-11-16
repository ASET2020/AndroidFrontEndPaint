package ie.tcd.asepaint2020.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ie.tcd.asepaint2020.MainActivity;
import org.greenrobot.eventbus.EventBus;

import ie.tcd.asepaint2020.R;
import ie.tcd.asepaint2020.event.EnterLobbyEvent;

public class NameInputFragment extends Fragment {

    private EditText etName;

    private Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_input, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (view == null) {
            return;
        }

        etName = view.findViewById(R.id.et_name);
        etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (null != keyEvent && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()) {
                    switch (keyEvent.getAction()) {
                        case KeyEvent.ACTION_UP:
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });

        btnSubmit = view.findViewById(R.id.bt_name_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(etName.getText().toString());
            }
        });

    }

    private void submit(String text) {
        // TODO: 09/11/2020
        String s;
        s = etName.getText().toString();
        ((MainActivity) getActivity()).getGameStatus().OpenConnection(s);
        EventBus.getDefault().post(new EnterLobbyEvent());
    }

}
