package ru.laz.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;

import ru.laz.game.model.graph.MathGame;
import ru.laz.game.view.ui.screens.GameScreen;

public class AGame extends Game {
	
	//To add external lib (Astar.jar) -add to build.gradle in ROOT project: compile fileTree(dir: '../libs', include: '*.jar').
	//For core, android, desktop. In EACH subfolder should be folder 'libs' with jar.
	


	
	private static AGame game;
	private GameScreen gameScreen;
	
	public static final float W_WIDTH = 2048;
	public static final float W_HEIGHT = 512;
	
	
	FPSLogger fps;
	
	private AGame() {//Singletone
		super();


		
		fps = new FPSLogger();
	}
	
	public static AGame getGame() {




		if(game == null) {

			game = new AGame();
		}


		return game;
	}
	
	public GameScreen getGameScreen() {
		return this.gameScreen;
	}
	
	@Override
	public void create () {
		
		gameScreen = new GameScreen();
		gameScreen.init();
		this.setScreen(gameScreen);


		Array<Float> scales = MathGame.getArrayOfFloats(1.0f, 0.8f, 10);

		Gdx.app.log("SCALES ", scales.toString());

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.getScreen().render(Gdx.graphics.getDeltaTime());
		//fps.log();
	}
}
