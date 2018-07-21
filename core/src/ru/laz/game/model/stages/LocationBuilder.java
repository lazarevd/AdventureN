package ru.laz.game.model.stages;

import ru.laz.game.model.stages.Locations.LocationGalley;

public class LocationBuilder {

	
	
	public static Location createGameLevel(int number) {
		
		Location location = null;
		
		if (number == 1) {
			location = new LocationGalley();
		}		
		location.init();
		return location;
	}
	
}
