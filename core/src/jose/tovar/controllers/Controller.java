package jose.tovar.controllers;

import jose.tovar.game.Game;
import jose.tovar.gameElements.Move;

/**
 * Created by Jose on 14/01/2016.
 */
public interface Controller
{
    Move getMove(Game game);
}
