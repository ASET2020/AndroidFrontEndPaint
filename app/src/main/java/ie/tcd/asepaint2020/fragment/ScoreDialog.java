package ie.tcd.asepaint2020.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import ie.tcd.asepaint2020.R;

/**
 * the dialog for final results
 */
public class ScoreDialog extends Dialog {

    private static final int MAX_USERS = 4;

    // user names and use scores
    private TextView[] tvNames = new TextView[MAX_USERS];
    private TextView[] tvScores = new TextView[MAX_USERS];

    private Button btnBack;

    public ScoreDialog(@NonNull Context context) {
        super(context);
    }

    public ScoreDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ScoreDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_score);
        setCanceledOnTouchOutside(false);
        tvNames[0] = findViewById(R.id.tv_name1);
        tvNames[1] = findViewById(R.id.tv_name2);
        tvNames[2] = findViewById(R.id.tv_name3);
        tvNames[3] = findViewById(R.id.tv_name4);

        tvScores[0] = findViewById(R.id.tv_score1);
        tvScores[1] = findViewById(R.id.tv_score2);
        tvScores[2] = findViewById(R.id.tv_score3);
        tvScores[3] = findViewById(R.id.tv_score4);

        int i = 0;
        if (nameScoreMap == null) {
            return;
        }
        for (Map.Entry<String, Integer> entry : nameScoreMap.entrySet()) {
            if (i >= 4) {
                break;
            }
            tvNames[i].setText(entry.getKey());
            tvScores[i].setText(entry.getValue().toString());
            i++;
        }

    }

    Map<String, Integer> nameScoreMap;

    public void showScores(Map<String, Integer> nameScoreMap) {
        if (nameScoreMap == null) {
            return;
        }
        this.nameScoreMap = nameScoreMap;
        show();
    }

}
