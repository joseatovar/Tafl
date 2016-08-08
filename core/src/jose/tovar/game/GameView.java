package jose.tovar.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import jose.tovar.gameElements.Board;
import jose.tovar.gameElements.King;
import jose.tovar.gameElements.Move;
import jose.tovar.gameElements.Warrior;
import jose.tovar.utils.Color;
import jose.tovar.utils.Constants;
import jose.tovar.utils.Position;

/**
 * Created by Jose on 26/02/2016.
 */
public class GameView
{
    Viewport viewport;
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture whiteWarrior;
    Texture blackWarrior;
    Texture king;
    Texture tile;
    Texture specialTile;

    public GameView()
    {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ExtendViewport(1024,768,camera);
        batch = new SpriteBatch();

        whiteWarrior = new Texture(Constants.path + Constants.whiteWarrior);
        blackWarrior = new Texture(Constants.path + Constants.blackWarrior);
        king = new Texture(Constants.path + Constants.king);
        tile = new Texture(Constants.path + Constants.tile);
        specialTile = new Texture(Constants.path + Constants.specialTile);
    }

    public void drawGame(Game game)
    {
        Gdx.gl.glClearColor(254, 254, 254, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        Position boardPosition = boardPosition(game.getBoard().getSize());
        drawBoard(boardPosition, game.getBoard().getSize());
        drawWarriors(boardPosition, game.getBoard());
        batch.end();
    }

    private void drawTexture(Texture texture, int x, int y)
    {
        batch.draw(texture, x, y);
    }

    private void drawBoard(Position boardPosition, int size)
    {
        for (int row = 0;row < size;row++)
        {
            for(int column = 0;column < size;column++)
            {
                int positionRow = boardPosition.getRow() + (row * tileHeigth());
                int positionColumn = boardPosition.getColumn() + (column * tileWidth());
                if( ((row == 0 || row == size-1) && (column == 0 || column == size-1)) || (row == size/2 && column == row) )
                {
                    drawTexture(specialTile,positionRow,positionColumn);
                }
                else
                {
                    drawTexture(tile,positionRow,positionColumn);
                }
            }
        }
    }

    private void drawWarriors(Position boardPosition, Board board)
    {
        for (int row = board.getSize()-1; row >= 0; row--)
        {
            for(int column = 0; column < board.getSize(); column++)
            {
                Warrior warrior = board.getPosition(row,column);
                if(warrior != null)
                {
                    int positionRow = boardPosition.getRow() + ((row%board.getSize())* tile.getHeight());
                    int positionColumn = boardPosition.getColumn() + (column * tile.getWidth());
                    if(warrior instanceof King)
                    {
                        drawTexture(king,positionRow, positionColumn);
                    }
                    else if(warrior.getColor().equals(Color.WHITE))
                    {
                        drawTexture(whiteWarrior,positionRow,positionColumn);
                    }
                    else
                    {
                        drawTexture(blackWarrior,positionRow,positionColumn);
                    }
                }
            }
        }
    }

    private int tileWidth()
    {
        return tile.getWidth();
    }

    private int tileHeigth()
    {
        return tile.getHeight();
    }

    private int boardWidth(int size)
    {
        return tileWidth() * size;
    }

    private int boardHeigth(int size)
    {
        return tileHeigth() * size;
    }

    private Position boardPosition(int size)
    {
        return new Position(Gdx.graphics.getWidth()/2 - boardWidth(size)/2 ,
                            Gdx.graphics.getHeight()/2 - boardHeigth(size)/2);
    }

    private Position positionToScreenPosition(Position pos/*,Game game*/)
    {
        Position position = null;
        if (pos != null)
        {
            Vector3 touchPos = new Vector3();
            touchPos.set(pos.getRow(), pos.getColumn(), 0);
            camera.unproject(touchPos);
            position = boardPosition(11);
            int row = (Math.round(touchPos.x) - position.getRow());
            position.setRow(row >0? row / tileHeigth() : -1);
            int colum = (Math.round(touchPos.y) - position.getColumn());
            position.setColumn(colum > 0? colum / tileWidth() : -1);
        }
        return position;
    }

    public Move transformMove(Move move)
    {
        Move resultMove = new Move();
        resultMove.setInitialPosition(positionToScreenPosition(move.getInitialPosition()));
        resultMove.setFinalPosition(positionToScreenPosition(move.getFinalPosition()));
        return resultMove;
    }

    public boolean isValidMove(Game game, Move move)
    {
        return move.isCompleteMove() && move.isBoardPosition(game.getBoard().getSize()) &&
                game.getBoard().getPosition(move.getInitialPosition())!=null &&
                game.getBoard().getPosition(move.getInitialPosition()).getColor() == game.getTurn() &&
                move.areDifferentPositions() &&
                (game.getBoard().isKingMove(move) || game.getBoard().isWarriorMove(move))
                ;
    }

    public void disposeGameView()
    {
        tile.dispose();
        specialTile.dispose();
        whiteWarrior.dispose();
        blackWarrior.dispose();
        king.dispose();
        batch.dispose();
    }
}
