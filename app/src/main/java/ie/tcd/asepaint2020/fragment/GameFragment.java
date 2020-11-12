package ie.tcd.asepaint2020.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import ie.tcd.asepaint2020.R;
import ie.tcd.asepaint2020.common.GameInput;
import ie.tcd.asepaint2020.logic.GameStatus;
import ie.tcd.asepaint2020.logic.GameStatusImpl;
import ie.tcd.asepaint2020.logic.LocalPlayNetworkSync;
import ie.tcd.asepaint2020.utils.DisplayUtil;
import io.github.controlwear.virtual.joystick.android.JoystickView;

import java.util.Timer;
import java.util.TimerTask;

public class GameFragment extends BaseFragment {

    public final int MOVE_DISTANCE = DisplayUtil.dip2px(getContext(), 7);

    View cursor;

    JoystickView joystick;

    Button btnThrow;

    ImageView ivPaint;

    private Handler handler = new Handler();
    Timer myt = new Timer();


    @Override
    int getLayoutId() {
        return R.layout.fragment_game;
    }


    GameBoardView board;

    GameStatus gs;

    Boolean isMoving = false;
    Float joystickDirection = 0f;
    Float joystickForce = 0f;
    Boolean isShooting = false;

    GameInput gi = new GameInput() {
        @Override
        public Boolean IsMoving() {
            return isMoving;
        }

        @Override
        public Float GetDirection() {
            return joystickDirection;
        }

        @Override
        public Float GetForce() {
            return joystickForce;
        }

        @Override
        public Boolean IsShooting() {
            return isShooting;
        }
    };

    @Override
    void initView(final View view) {

        // TODO: 09/11/2020  should init color
        cursor = view.findViewById(R.id.cursor);
        joystick = view.findViewById(R.id.joystickView);

        board = view.findViewById(R.id.board);

        //board.startMove(60);

        this.gs = new GameStatusImpl(new LocalPlayNetworkSync());
        view.getRootView().post(new Runnable() {
            @Override
            public void run() {
                gs.SetViewpointSize((float) view.getRootView().getMeasuredWidth(), (float) view.getRootView().getMeasuredHeight());
            }
        });

        board.setGameLogic(gs);

        btnThrow = view.findViewById(R.id.btn_throw);
        btnThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 09/11/2020
//                Toast.makeText(getContext(), "shooting ....", Toast.LENGTH_LONG).show();
                //startThrowAnim();
                //isShooting = true;
            }
        });

        btnThrow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isShooting = true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    isShooting = false;
                }
                gs.SubmitMovement(gi);
                return false;
            }
        });

        ivPaint = view.findViewById(R.id.iv_paint);

        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {

                Log.d("GameFragment", "angle" + angle + "--strength" + strength);
                if (angle == 0 && strength == 0) {
                    isMoving = false;
                    gs.SubmitMovement(gi);
                    return;
                }

                int y = (int) (Math.sin(Math.toRadians(angle)) * MOVE_DISTANCE);
                int x = -(int) (Math.cos(Math.toRadians(angle)) * MOVE_DISTANCE);

                Log.d("GameFragment", "x=" + x);
                Log.d("GameFragment", "y=" + y);

                // TODO: 08/11/2020 detect margin
                //((View) cursor.getParent()).scrollBy(x, y);
                isMoving = true;
                joystickDirection = (float) Math.toRadians(angle);
                joystickForce = (float) strength;
                gs.SubmitMovement(gi);

                View parent = (View) cursor.getParent();
                final int scrollY = parent.getScrollY();
                final int scrollX = parent.getScrollX();

                Log.d("jump", "scrollY:" + scrollY);
                Log.d("jump", "scrollX:" + scrollX);
            }
        });

        joystick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    isMoving = false;
                }
                gs.SubmitMovement(gi);
                return false;
            }
        });

        myt.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        View parent = (View) cursor.getParent();
                        parent.scrollTo(Math.round(gs.GetCursor().GetX()),Math.round(gs.GetCursor().GetY()));
                        board.invalidate();
                    }
                });
            }
        },10,10);
    }


    private void startThrowAnim() {


        View parent = (View) cursor.getParent();
        final int scrollY = parent.getScrollY();
        final int scrollX = parent.getScrollX();

        Log.d("jump", "scrollY:" + scrollY);
        Log.d("jump", "scrollX:" + scrollX);

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
                Log.d("jump", "scrollY:" + scrollY);
                Log.d("jump", "scrollX:" + scrollX);
                board.getLocalThrowResult(scrollX, scrollY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivPaint.startAnimation(translateAnimation);


    }
}
