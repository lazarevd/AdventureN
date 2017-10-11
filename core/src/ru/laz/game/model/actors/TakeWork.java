package ru.laz.game.model.actors;


import com.badlogic.gdx.Gdx;

import ru.laz.game.AGame;
import ru.laz.game.model.stages.GameLevel;
import ru.laz.game.model.things.Thing;
import ru.laz.game.view.ui.UI;

public class TakeWork extends Work {

	private String targetThing;
	
	
	public TakeWork(String thing, GameLevel gameLevel) {
		super(gameLevel);
		this.targetThing = thing;
		//Gdx.app.log("TakeThingWork", "construct");
	}
	
	@Override
	public void init() {
		setStatus(WorkStatus.PROCESS);
		//Gdx.app.log("Thing taken","inited");		
	}
	
	@Override
	public void act(float delta) {
		Gdx.app.log("Thing taken", "OK");
		
		Thing curThing = AGame.getGame().getGameScreen().getGameLevel().getThings().get(targetThing);
		Gdx.app.log("PUT", curThing.getClass() + " name " + targetThing);
		UI.getUI().getTrunk().addToTrunk(targetThing, curThing);
		gameLevel.removeThing(targetThing);
		setStatus(WorkStatus.FINISHED);
	}

	
	
}