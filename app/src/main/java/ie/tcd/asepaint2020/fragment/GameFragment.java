package ie.tcd.asepaint2020.fragment;

import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import ie.tcd.asepaint2020.R;
import ie.tcd.asepaint2020.utils.DisplayUtil;
import io.github.controlwear.virtual.joystick.android.JoystickView;

public class GameFragment extends BaseFragment {

    public final int MOVE_DISTANCE = DisplayUtil.dip2px(getContext(), 5);

    View cursor;

    JoystickView joystick;

    Button btnThrow;

    ImageView ivPaint;


    @Override
    int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    void initView(View view) {

        // TODO: 09/11/2020  should init color
        cursor = view.findViewById(R.id.cursor);
        joystick = view.findViewById(R.id.joystickView);

        btnThrow = view.findViewById(R.id.btn_throw);
        btnThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 09/11/2020
                Toast.makeText(getContext(), "shooting ....", Toast.LENGTH_LONG).show();
                startThrowAnim();
            }
        });

        ivPaint = view.findViewById(R.id.iv_paint);

        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {

                Log.d("GameFragment", "angle" + angle + "--strength" + strength);
                if (angle == 0 && strength == 0) {
                    return;
                }

                int y = (int) (Math.sin(Math.toRadians(angle)) * MOVE_DISTANCE);
                int x = -(int) (Math.cos(Math.toRadians(angle)) * MOVE_DISTANCE);

                Log.d("GameFragment", "x=" + x);
                Log.d("GameFragment", "y=" + y);

                // TODO: 08/11/2020 detect margin
                ((View) cursor.getParent()).scrollBy(x, y);

            }
        });
    }

    private void startThrowAnim() {


        View parent = (View) cursor.getParent();
        int scrollY = parent.getScrollY();
        int scrollX = parent.getScrollX();

        int y = ivPaint.getBottom() / 2 + scrollY;
        int x = scrollX;
        ivPaint.setVisibility(View.VISIBLE);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -x / (ivPaint.getRight() - ivPaint.getLeft()),
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -y / (ivPaint.getBottom() - ivPaint.getTop()));
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivPaint.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivPaint.startAnimation(translateAnimation);


    }
}
