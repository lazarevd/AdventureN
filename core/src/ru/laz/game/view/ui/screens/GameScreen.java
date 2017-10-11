package ru.laz.game.view.ui.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import ru.laz.game.controller.Controller;
import ru.laz.game.model.stages.GameLevel;
import ru.laz.game.model.stages.LevelBuilder;
import ru.laz.game.view.render.Render;
import ru.laz.game.view.ui.UI;


public class GameScreen extends ScreenAdapter implements Screen {


	GameLevel gameLevel;

	static Render render;
	static UI ui;
	
	public GameScreen() {
		ui = UI.getUI();
		render = new Render(ui);
	}
	
	public GameLevel getGameLevel() {
		return this.gameLevel;
	}
	
	
	public void init() {
		this.gameLevel = LevelBuilder.createGameLevel(1, ui);
        Gdx.app.log("GAME LEVEL", gameLevel.toString());
        Controller.setSceneControls(gameLevel);
		//Gdx.input.setInputProcessor(UI.getUIStage());//????????? ???? ????? UI ??? InputProcessor - ?????????? ???????.
		render.setGameLevel(gameLevel);
	}
	

	
	@Override
	public void show() {
		Gdx.app.log("Screen", "show");
	}
	
	
	@Override
	public void render(float delta) {
		ui.act(delta);
		this.gameLevel.act(delta);
		render.drawObjects(this.gameLevel);
	}


	@Override 
	public void resize(int width, int height) {
		UI.getViewportScene().update(width, height, false);
		UI.getViewportUI().update(width, height, false);
	//??????????? ?????? ?????? ????????, ????? ?? ?? ????????.
	}
	
}
