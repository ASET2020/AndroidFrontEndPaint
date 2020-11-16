package ie.tcd.asepaint2020.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

import ie.tcd.asepaint2020.logic.GameStatus;

/**
 * the view for game board
 */
public class GameBoardView extends View {

    private Paint paint;
    private float left;
    private float top;

    // game status
    private GameStatus gs;


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

        // draw the game board
        canvas.drawRect(left, top, left + sizex, top + sizey, paint);

        // draw the hitting
        List<ie.tcd.asepaint2020.common.Paint> pi = gs.GetGameStatus().GetPaints();
        for (ie.tcd.asepaint2020.common.Paint hit : pi) {
            String pclor = hit.Color();
            switch (pclor) {
                case "Blue":
                    paint.setColor(Color.BLUE);
                    break;
                case "Green":
                    paint.setColor(Color.GREEN);
                    break;
                case "Red":
                    paint.setColor(Color.RED);
                    break;
                case "Orange":
                    paint.setColor(Color.YELLOW);
                    break;
            }
            // every hit is a circle
            canvas.drawCircle(left + hit.LocationX(), top + hit.LocationY(), hit.Size(), paint);
        }

    }

    /**
     * set the Game Status
     */
    public void setGameLogic(GameStatus gs) {
        this.gs = gs;
    }

}
