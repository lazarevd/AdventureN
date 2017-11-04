package ru.laz.game.model.actors;


import com.badlogic.gdx.Gdx;

import ru.laz.game.AGame;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.instances.Thing;
import ru.laz.game.view.ui.UI;

public class TakeWork extends Work {

	private String targetThing;
	
	
	public TakeWork(String thing, Level level) {
		super(level);
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
		
		Thing curThing = AGame.getGame().getGameScreen().getLevel().getThings().get(targetThing);
		Gdx.app.log("PUT", curThing.getClass() + " name " + targetThing);
		UI.getUI().getTrunk().addToTrunk(targetThing, curThing);
		level.removeThing(targetThing);
		setStatus(WorkStatus.FINISHED);
	}

	
	
}