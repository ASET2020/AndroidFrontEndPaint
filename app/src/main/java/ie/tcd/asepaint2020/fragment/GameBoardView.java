package ie.tcd.asepaint2020.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

import ie.tcd.asepaint2020.logic.GameStatus;

public class GameBoardView extends View {

    private Paint paint;
    private float left;
    private float top;

    private ObjectAnimator moveAnimator;


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


            List<ie.tcd.asepaint2020.common.Paint> pi = gs.GetGameStatus().GetPaints();

            for (ie.tcd.asepaint2020.common.Paint hit : pi) {
                String pclor = hit.Color();
                switch (pclor) {
                    case "Blue":
                        paint.setColor(Color.BLUE);
                }

                canvas.drawCircle(left + hit.LocationX(), top + hit.LocationY() , hit.Size(), paint);
            }

    }

    GameStatus gs;

    public void setGameLogic(GameStatus gs){
        this.gs = gs;
    }

}
