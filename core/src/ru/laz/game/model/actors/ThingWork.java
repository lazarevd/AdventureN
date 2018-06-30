package ru.laz.game.model.actors;


import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.stages.Level;

public class ThingWork extends Work {

	private ThingContainer pickThing;


	public ThingWork(ThingContainer pickThing, Level level) {
		super(level);
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
		pickThing.getThing().actOnClick();
		setStatus(WorkStatus.FINISHED);
	}

	
	
}