package jose.tovar.game;

import jose.tovar.boardConstructor.BoardFactory;
import jose.tovar.controllers.Controller;
import jose.tovar.controllers.HumanController;
import jose.tovar.gameElements.Board;
import jose.tovar.gameElements.Move;
import jose.tovar.utils.Color;
import jose.tovar.utils.TaflTypes;

/**
 * Created by Jose on 18/02/2016.
 */
public class Game
{
    private Board board;
    GameView gameView;
    Controller whitePlayer, blackPlayer;
    Color turn;
    Move move;

    public Game(TaflTypes boardType, Controller whitePlayer, Controller blackPlayer)
    {
        board = new BoardFactory().makeBoard(boardType);
        gameView = new GameView();
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        turn = Color.BLACK;
        move = new Move();
    }

    public Board getBoard()
    {
        return board;
    }

    public Color getTurn()
    {
        return turn;
    }

    public Move getLastMove()
    {
        return move;
    }

    public void drawGame()
    {
        gameView.drawGame(this);
    }

    public void advanceGame()
    {
        move = getMove();
        updateGame();
        drawGame();
    }

    public void disposeGame()
    {
        gameView.disposeGameView();
    }

    private void updateGame()
    {
        if(gameView.isValidMove(this,move))
        {
            if(move.isTowerMove() && board.isEmptyPath(move))
            {
                board.updateBoard(move);
                nextTurn();
            }
            else
            {
                move.clearMove();
            }
        }
    }

    private void nextTurn()
    {
            turn = turn.nextColor();
            move.clearMove();
    }

    private Move getMove()
    {
        Move move;
        Controller controller;
        if (turn == Color.WHITE)
        {
            controller = whitePlayer;
        }
        else
        {
            controller = blackPlayer;
        }
        move = controller.getMove(this);
        return controller instanceof HumanController ? gameView.transformMove(move) : move;
    }
}
