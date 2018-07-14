package ru.laz.game.view.ui.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import ru.laz.game.controller.Controller;
import ru.laz.game.model.actors.MainActor;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.stages.LevelBuilder;
import ru.laz.game.model.things.Trunk;
import ru.laz.game.view.render.Render;
import ru.laz.game.view.ui.UI;


public class GameScreen extends ScreenAdapter implements Screen {


    private static Trunk trunk;
    Level level;

	static transient Render render;
	static transient UI ui;
	
	public GameScreen() {
		ui = UI.getUI();
		render = new Render(ui);
	}
	
	public Level getLevel() {
		return this.level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void init() {
	    trunk = new Trunk();
		if (this.level == null) {
			this.level = LevelBuilder.createGameLevel(1);
		}
        this.level.setMainActor(new MainActor(this.level, 1000, 100, 20, 2));
		Controller.setLevel(this.level);
		Controller.setSceneControls();
	}


	public void initSaved() {
		Controller.setLevel(this.level);
		Controller.setSceneControls();
	}


	public static Trunk getTrunk() {
	    return trunk;
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
