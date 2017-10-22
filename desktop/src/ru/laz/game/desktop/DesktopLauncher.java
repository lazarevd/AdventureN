package ru.laz.game.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.laz.game.AGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		
		DisplayMode[] dm = LwjglApplicationConfiguration.getDisplayModes();
		
		for (DisplayMode d : dm) {
			d.toString();
		}
		//config.width = AGame.WIDTH;
		//config.height = AGame.HEIGHT;
		//config.width = UI.WIDTH;
		//config.height = UI.HEIGHT;

	    // fullscreen
		//config.fullscreen = true;
	    // vSync
		//config.vSyncEnabled = true;
		
		new LwjglApplication(AGame.getGame(), config);
	}
}
