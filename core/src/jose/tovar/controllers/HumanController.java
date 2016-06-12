package jose.tovar.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import jose.tovar.game.Game;
import jose.tovar.gameElements.Move;
import jose.tovar.utils.Position;

/**
 * Created by Jose on 10/03/2016.
 */
public class HumanController implements Controller
{
    Move controllerMove = new Move();

    @Override
    public Move getMove(Game game)
    {
        if(Gdx.input.justTouched())
        {
            if(controllerMove.isCompleteMove())
            {
                controllerMove.clearMove();
            }
            if(controllerMove.isEmptyMove())
            {
                controllerMove.setInitialPosition(getPositionTouch());
            }
            else if (controllerMove.isNotCompleteMove())
            {
                controllerMove.setFinalPosition(getPositionTouch());
            }
        }
        return controllerMove;
    }

    public Position getPositionTouch()
    {
        return new Position(Gdx.input.getX(),Gdx.input.getY());
    }
}
