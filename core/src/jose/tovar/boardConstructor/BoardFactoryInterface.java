package jose.tovar.boardConstructor;

import jose.tovar.gameElements.Board;
import jose.tovar.utils.TaflTypes;

/**
 * Created by Jose on 27/12/2015.
 */
public interface BoardFactoryInterface
{
    public Board makeBoard(TaflTypes taflType);
}
