package jose.tovar.tafl.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import jose.tovar.tafl.TaflGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 800;
		config.width = 800;
//		Graphics.DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
//		config.setFromDisplayMode(displayMode);
		new LwjglApplication(new TaflGame(), config);
	}
}
