package ru.laz.game.model.actors;


import ru.laz.game.controller.Controller;
import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.stages.Location;
import ru.laz.game.model.things.Thing;

public class TakeWork extends Work {

	private ThingContainer targetThing;
	
	
	public TakeWork(ThingContainer thing, Location location) {
		super(location);
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
		Thing curThing = targetThing.getThing();
		Controller.moveThingWorldToTrunk(new ThingContainer(targetThing.getThingName(), curThing));
		location.removeThing(targetThing.getThingName());
		setStatus(WorkStatus.FINISHED);
	}

	
	
}