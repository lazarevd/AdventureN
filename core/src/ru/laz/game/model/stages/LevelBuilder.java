package ru.laz.game.model.stages;

import ru.laz.game.view.ui.UI;




public class LevelBuilder {

	
	
	public static GameLevel createGameLevel(int number, UI ui) {
		
		GameLevel level = null;
		
		if (number == 1) {
			level = new Level_01();
		}		
		level.init(ui);
		return level;
	}
	
}
