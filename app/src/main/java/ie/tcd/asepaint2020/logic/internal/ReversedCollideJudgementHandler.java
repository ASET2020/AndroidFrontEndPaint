package ie.tcd.asepaint2020.logic.internal;

public class ReversedCollideJudgementHandler implements CollideJudgementHandler{
    private CollideJudgementHandler OriginalHandler;

    public ReversedCollideJudgementHandler(CollideJudgementHandler originalHandler) {
        OriginalHandler = originalHandler;
    }

    @Override
    public boolean IsIntersectionExist(Collidable a, Collidable b) {
        return OriginalHandler.IsIntersectionExist(b,a);
    }
}
