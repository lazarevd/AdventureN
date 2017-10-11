package ru.laz.game.model.actors;

import ru.laz.game.model.actors.Work.WorkStatus;

public class WorkProcessor {
	
	boolean workDone = false;
	
	public static boolean processWork(Work work, float delta) {
		
		boolean ret = false;
		
		if (work != null) {
			
			if(work.getStatus() == WorkStatus.INIT) {
				work.init();
			}
			if(work.getStatus() == WorkStatus.PROCESS) {
				work.act(delta);
			}
			if(work.getStatus() == WorkStatus.FINISHED) {
				ret = true;
			}
			
		}
		return ret;
	}
	

}
