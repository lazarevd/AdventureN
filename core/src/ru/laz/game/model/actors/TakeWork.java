package ru.laz.game.model.actors;


import ru.laz.game.AGame;
import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.Thing;
import ru.laz.game.view.ui.UI;

public class TakeWork extends Work {

	private ThingContainer targetThing;
	
	
	public TakeWork(ThingContainer thing, Level level) {
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
		Thing curThing = AGame.getGame().getGameScreen().getLevel().getThings().get(targetThing.getThingName());
		UI.getUI().getTrunk().addToTrunk(new ThingContainer(targetThing.getThingName(), curThing));
		level.removeThing(targetThing.getThingName());
		setStatus(WorkStatus.FINISHED);
	}

	
	
}