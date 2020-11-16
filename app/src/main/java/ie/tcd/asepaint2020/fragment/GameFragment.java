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
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import ie.tcd.asepaint2020.MainActivity;
import ie.tcd.asepaint2020.R;
import ie.tcd.asepaint2020.common.GameInput;
import ie.tcd.asepaint2020.event.NameInputEvent;
import ie.tcd.asepaint2020.logic.GameStatus;
import io.github.controlwear.virtual.joystick.android.JoystickView;

/**
 * the fragment for gaming
 */
public class GameFragment extends BaseFragment {

    private View cursor;

    // object for controlling cursor
    private JoystickView joystick;

    private Button btnThrow;

    private ImageView ivPaint;

    private Handler handler = new Handler();
    Timer myt = new Timer();

    // the game board
    private GameBoardView board;

    // the game status
    private GameStatus gs;

    private Boolean isMoving = false;
    private Float joystickDirection = 0f;
    private Float joystickForce = 0f;
    private Boolean isShooting = false;

    // the tip for hitting results
    private TextView tvShootTip;

    private boolean isDialogShowing;

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
    int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    void initView(final View view) {
        cursor = view.findViewById(R.id.cursor);
        joystick = view.findViewById(R.id.joystickView);
        board = view.findViewById(R.id.board);
        tvShootTip = view.findViewById(R.id.tv_shoot_tip);
        this.gs = getGameStatus();

        view.getRootView().post(new Runnable() {
            @Override
            public void run() {
                gs.SetViewpointSize((float) board.getWidth(), (float) board.getHeight());
            }
        });

        board.setGameLogic(gs);

        btnThrow = view.findViewById(R.id.btn_throw);
        btnThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                isMoving = true;
                joystickDirection = (float) Math.toRadians(angle);
                joystickForce = (float) strength;
                gs.SubmitMovement(gi);

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
                        parent.scrollTo(Math.round(cursor.getLeft() - gs.GetCursor().GetX() + cursor.getWidth() / 2f),
                                Math.round(cursor.getTop() - gs.GetCursor().GetY() + +cursor.getHeight() / 2f));

                        changeTips(gs.GetFlashMsg());


                        if (gs.GetGameStatus().IsGameEnded() && !isDialogShowing) {
                            final ScoreDialog dialog = new ScoreDialog(getContext());
                            dialog.showScores(gs.GetGameStatus().GetGameResult());
                            isDialogShowing = true;
                            dialog.findViewById(R.id.btn_back_lobby).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    isDialogShowing = false;
                                    dialog.dismiss();
                                    EventBus.getDefault().post(new NameInputEvent());
                                }
                            });

                            myt.cancel();
                            ((MainActivity) getActivity()).getGameStatus(true);
                        }

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
//                Log.d("jump", "scrollY:" + scrollY);
//                Log.d("jump", "scrollX:" + scrollX);
//                board.getLocalThrowResult(scrollX, scrollY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivPaint.startAnimation(translateAnimation);
    }


    /**
     * change the content of tip view
     */
    public void changeTips(String text) {
        if (tvShootTip != null) {
            tvShootTip.setText(text);
        }
    }
}
