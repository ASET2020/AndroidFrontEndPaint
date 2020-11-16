package ie.tcd.asepaint2020.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

import ie.tcd.asepaint2020.MainActivity;
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

        etName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView text, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit(etName.getText().toString());
                    return true;
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
        if (TextUtils.isEmpty(text)) {
            return;
        }
        ((MainActivity) getActivity()).getGameStatus().OpenConnection(text);
        EventBus.getDefault().post(new EnterLobbyEvent());
    }

}
