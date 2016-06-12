package jose.tovar.tafl;

import com.badlogic.gdx.ApplicationAdapter;
import jose.tovar.controllers.Controller;
import jose.tovar.controllers.HumanController;
import jose.tovar.game.Game;
import jose.tovar.game.GameView;
import jose.tovar.gameElements.Move;
import jose.tovar.utils.TaflTypes;

public class TaflGame extends ApplicationAdapter
{
	Game game;
	GameView gameView;
	Controller whitePlayer, blackPlayer;
	Move move;

	@Override
	public void create ()
	{
		whitePlayer = new HumanController();
		blackPlayer = new HumanController();
		game = new Game(TaflTypes.HNEFATAFL,whitePlayer,blackPlayer);
		gameView = new GameView();
		move = new Move();
	}

	@Override
	public void render ()
	{
		game.advanceGame();
	}


	@Override
	public void dispose()
	{
		game.disposeGame();
	}
}
