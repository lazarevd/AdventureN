package ru.laz.game.view.ui.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import ru.laz.game.controller.Controller;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.stages.LevelBuilder;
import ru.laz.game.view.render.Render;
import ru.laz.game.view.ui.UI;


public class GameScreen extends ScreenAdapter implements Screen {


	Level level;

	static Render render;
	static UI ui;
	
	public GameScreen() {
		ui = UI.getUI();
		render = new Render(ui);
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	
	public void init() {
		this.level = LevelBuilder.createGameLevel(1);
		ui.setLevel(this.level);
		Controller.setLevel(this.level);
		Controller.setSceneControls();
		render.setLevel(level);
	}
	

	
	@Override
	public void show() {
		Gdx.app.log("Screen", "show");
	}
	
	
	@Override
	public void render(float delta) {
		ui.act(delta);
		this.level.act(delta);
		render.drawObjects(this.level);
	}


	@Override 
	public void resize(int width, int height) {
		UI.getViewportScene().update(width, height, false);
		UI.getViewportUI().update(width, height, false);
	//??????????? ?????? ?????? ????????, ????? ?? ?? ????????.
	}
	
}
