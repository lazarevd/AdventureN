package ru.laz.game.model.actors;


import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.stages.Level;

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
		targetThing.getThing().actWithObject(pickThing);
		setStatus(WorkStatus.FINISHED);
	}

	
	
}