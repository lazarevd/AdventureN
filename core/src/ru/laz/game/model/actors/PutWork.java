package ru.laz.game.model.actors;


import ru.laz.game.AGame;
import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.Thing;

public class PutWork extends Work {

	private ThingContainer pickThing;
	private ThingContainer targetThing;


	public PutWork(ThingContainer pickThing, ThingContainer targetThing, Level level) {
		super(level);
		this.targetThing = targetThing;
		this.pickThing = pickThing;
		//Gdx.app.log("TakeThingWork", "construct");
	}
	
	@Override
	public void init() {
		setStatus(WorkStatus.PROCESS);
		//Gdx.app.log("Thing taken","inited");		
	}
	
	@Override
	public void act(float delta) {
		Thing curThing = AGame.getGame().getGameScreen().getLevel().getThings().get(targetThing.getThingName());
		targetThing.getThing().actWithObject(pickThing);
		setStatus(WorkStatus.FINISHED);
	}

	
	
}