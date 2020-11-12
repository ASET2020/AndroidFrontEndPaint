package ie.tcd.asepaint2020.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
        canvas.translate(getWidth() / 2 - size / 2, 0);
        paint.setColor(Color.BLACK);
        canvas.drawRect(left, top, left + size, top + size, paint);

        if (!hitResults.isEmpty()) {
            for (Hits hit : hitResults) {
                paint.setColor(hit.color);
                canvas.drawCircle(left + hit.hit_x, top + hit.hit_y, 15, paint);
            }
        }
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

    public void getLocalThrowResult(int scrollX, int scrollY) {
        int transX = (int) (-scrollX + size / 2 - left);
        int transY = (int) (getHeight() / 2 - scrollY - top);

        Log.d("jump", "transX" + transX);
        Log.d("jump", "transY" + transY);
        if (0 <= transX && transX <= size) {
            if (transY >= 0 && transY <= size) {
                Toast.makeText(getContext(), "Hit!!!", Toast.LENGTH_SHORT).show();
                hitResults.add(new Hits(transX, transY, Color.parseColor("#009688")));
                invalidate();
            }
        }
    }
}
