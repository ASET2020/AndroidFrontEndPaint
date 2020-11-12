package ie.tcd.asepaint2020.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ie.tcd.asepaint2020.logic.GameStatus;

public class GameBoardView extends View {

    private Paint paint;
    private float left;
    private float top;
    private int size = 500;
    private int speed = 3;

    private boolean needStop;

    private ObjectAnimator moveAnimator;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public List<Hits> hitResults = new ArrayList<>();

    public GameBoardView(Context context) {
        this(context, null);
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        left = gs.GetGameStatus().GetRelativeX();
        top = gs.GetGameStatus().GetRelativeY();

        Float sizex = gs.GetGameStatus().GetSizeX();
        Float sizey = gs.GetGameStatus().GetSizeY();

        // get center coordinate
        float centerX = left + sizex / 2;
        float centerY = top + sizey / 2;

        canvas.drawRect(left, top, left + sizex, top + sizey, paint);

        if (!hitResults.isEmpty()) {
            List<ie.tcd.asepaint2020.common.Paint> pi = gs.GetGameStatus().GetPaints();

            for (ie.tcd.asepaint2020.common.Paint hit : pi) {
                String pclor = hit.Color();
                switch (pclor) {
                    case "Blue":
                        paint.setColor(Color.BLUE);
                }

                canvas.drawCircle(left + hit.LocationX(), top + hit.LocationY(), 15, paint);
            }
        }
    }

    GameStatus gs;

    public void setGameLogic(GameStatus gs){
        this.gs = gs;
    }

    public void startMove(int seconds) {
        moveAnimator = ObjectAnimator.ofFloat(this, "progress",
                1f, 2f);
        moveAnimator.setInterpolator(new AccelerateInterpolator());
        moveAnimator.setDuration(seconds * 1000);
        moveAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        moveAnimator.start();
    }

    int directionY = 1;
    int directionX = 1;

    public void setProgress(float value) {
        if (top <= 0 && directionY == -1) {
            directionY = 1;
        } else if (top >= (getBottom() - size) && directionY == 1) {
            directionY = -1;
        }
        top = top + directionY * speed * value;
        if (left <= -(getRight() / 2) + size / 2 && directionX == -1) {
            directionX = 1;
        } else if (left >= (getRight() / 2 - size / 2) && directionX == 1) {
            directionX = -1;
        }
        left = left + directionX * speed * value;
        invalidate();
    }

    public void voidStopMove() {
        if (moveAnimator != null) {
            moveAnimator.cancel();
        }
    }
}
