package ie.tcd.asepaint2020.fragment;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import ie.tcd.asepaint2020.MainActivity;
import ie.tcd.asepaint2020.R;
import ie.tcd.asepaint2020.event.EnterLobbyEvent;


/**
 * the fragment for inputting user name
 */
public class NameInputFragment extends BaseFragment {

    // EditText for editing name
    private EditText mEtName;

    // Button for submitting name
    private Button mBtnSubmit;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_name_input;
    }

    @Override
    public void initView(View view) {
        if (view == null) {
            return;
        }

        mEtName = view.findViewById(R.id.et_name);
        mEtName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView text, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submitUserName(mEtName.getText().toString());
                    return true;
                }
                return false;
            }
        });

        mBtnSubmit = view.findViewById(R.id.bt_name_submit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitUserName(mEtName.getText().toString());
            }
        });

    }

    /**
     * submit the user name
     */
    private void submitUserName(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        ((MainActivity) getActivity()).getGameStatus().OpenConnection(text);
        EventBus.getDefault().post(new EnterLobbyEvent());
    }

}
