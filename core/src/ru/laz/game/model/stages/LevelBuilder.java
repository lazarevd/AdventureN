package ru.laz.game.model.stages;

import ru.laz.game.model.stages.Level_01.Level_01;

public class LevelBuilder {

	
	
	public static Level createGameLevel(int number) {
		
		Level level = null;
		
		if (number == 1) {
			level = new Level_01();
		}		
		level.init();
		return level;
	}
	
}
