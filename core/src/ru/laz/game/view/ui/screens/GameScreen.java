package ru.laz.game.view.ui.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

import ru.laz.game.controller.Controller;
import ru.laz.game.model.actors.MainActor;
import ru.laz.game.model.stages.Location;
import ru.laz.game.model.stages.LocationBuilder;
import ru.laz.game.model.things.ThingsFabric;
import ru.laz.game.model.things.Trunk;
import ru.laz.game.view.render.Render;
import ru.laz.game.view.ui.UI;


public class GameScreen extends ScreenAdapter implements Screen, Json.Serializable {

    Trunk trunk;
    Location currentLocation;
    HashMap<String,Location> episodeLocations;

	static transient Render render;
	static transient UI ui;
	
	public GameScreen() {
        ThingsFabric.init();
        episodeLocations = new HashMap<String,Location>();
		ui = UI.getUI();
		render = new Render(ui);
	}

    @Override
    public void write(Json json) {
        Gdx.app.log("GameScreen", "write()");
        json.writeFields(this);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        Gdx.app.log("GameScreen", "read()");
        json.readFields(this,jsonData);
        this.currentLocation.getMainActor().setLocation(this.currentLocation);
        initSaved();
    }

	public Location getCurrentLocation() {
		return this.currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public void addLocation(String locationName, Location location) {
		episodeLocations.put(locationName, location);
	}

	public Location getLocation(String locationName) {
		return episodeLocations.get(locationName);
	}

	public void init() {
	    trunk = new Trunk();
		if (this.currentLocation == null) {
			this.currentLocation = LocationBuilder.createGameLevel(1);
		}
        this.currentLocation.setMainActor(new MainActor(this.currentLocation, 1000, 100, 20, 2));
		Controller.setLocation(this.currentLocation);
		Controller.setSceneControls();
	}


	public void initSaved() {
		Gdx.app.log("Gamescreen", "init saved " + trunk.getThings().size());
		Controller.setLocation(this.currentLocation);
		Controller.setSceneControls();
	}


	public Trunk getTrunk() {
	    return trunk;
    }

    public void setTrunk(Trunk trunk) {
        this.trunk = trunk;
    }
	
	@Override
	public void show() {
		Gdx.app.log("Screen", "show");
	}
	
	
	@Override
	public void render(float delta) {
		ui.act(delta);
		this.currentLocation.act(delta);
		render.drawObjects(this.currentLocation);
	}


	@Override 
	public void resize(int width, int height) {
		UI.getViewportScene().update(width, height, false);
		UI.getViewportUI().update(width, height, false);
	//??????????? ?????? ?????? ????????, ????? ?? ?? ????????.
	}


}
