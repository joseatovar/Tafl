package jose.tovar.boardConstructor;

import jose.tovar.gameElements.Board;
import jose.tovar.gameElements.Warrior;
import jose.tovar.utils.Color;
import jose.tovar.utils.Constants;
import jose.tovar.utils.TaflTypes;

/**
 * Created by Jose on 27/12/2015.
 */
public class BoardFactory implements BoardFactoryInterface
{
    @Override
    public Board makeBoard(TaflTypes taflType)
    {
        Board board;
        switch (taflType)
        {
            case HNEFATAFL:
                board = configureHnefatafl();
                break;
            default:
                board = configureHnefatafl();
                break;
        }
        return board;
    }

    public Board configureHnefatafl()
    {
        /**
         * Create black legion
         */
        int[] blackLegion = new int[]{4,5,6,7,8,17,34,44,45,55,56,57,65,66,67,77,78,88,105,114,115,116,117,118};
        /**
         * Create white legion
         */
        int[] whiteLegion = new int[]{39,49,50,51,59,60,62,63,71,72,73,83};
        /**
         * Create board game
         */
        return new Board(Constants.HNEFATAFL_SIZE,whiteLegion,blackLegion);
    }
}
