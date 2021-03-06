package ru.laz.game.model.actors;

import ru.laz.game.model.stages.Location;




public class Work{

	protected MainActor mActor;
	protected Location location;

	public enum WorkStatus {INIT, PROCESS, FINISHED};
	
	private WorkStatus status;

	
	public Work(Location location) {
		this.location = location;
		setStatus(WorkStatus.INIT);
	}
	
	public void init () {
		
		
	}
	
	
	public void act(float delta) {
	}

	
	
	public void setActor(MainActor mActor) {
		this.mActor = mActor;
	}


	public WorkStatus getStatus() {
		return status;
	}


	public void setStatus(WorkStatus status) {
		this.status = status;
	}

	
}
