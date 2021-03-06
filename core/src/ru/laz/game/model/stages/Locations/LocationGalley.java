package ru.laz.game.model.stages.Locations;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.laz.game.AGame;
import ru.laz.game.model.stages.Location;
import ru.laz.game.model.things.StaticObject;
import ru.laz.game.model.things.ThingsFabric;
import ru.laz.game.view.render.TextureFabric;


public class LocationGalley extends Location {//Wrapper for Gdx Stage, Graph and all stage stuff.


	private transient Music rainMusic;


	public LocationGalley() {
		//super(1024, 768);
        super();
		AGame.getGame().getGameScreen().addLocation("galley", this);
	}	




	@Override
	public void init() {
		super.init();
	    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	    rainMusic.setLooping(true);
	   // rainMusic.play();

        TextureFabric.addTexture("back", new TextureRegion(new Texture(Gdx.files.internal("back.png"))));
        TextureFabric.addTexture("fr3", new TextureRegion(new Texture(Gdx.files.internal("fr3.png"))));
	    StaticObject back = new StaticObject("back", 0, 0, 0, 1240, 540);
	    addStaticObject("back", back);
	    StaticObject fr3 = new StaticObject("fr3", 908, 6, 30, 512, 256);
		fr3.setParallaxFactor(0.5f);
		addStaticObject("fr3", fr3);

	    addThing(ThingsFabric.genThing("mug"));
	    addThing(ThingsFabric.genThing("rope"));
		addThing(ThingsFabric.genThing("mixerStatic"));
		addThing(ThingsFabric.genThing("stove"));
	    addThing(ThingsFabric.genThing("oil"));
	    addThing(ThingsFabric.genThing("pan"));

		this.setCameraPosition(800.0f, 245.0f);
		this.setInitalSceneCameraPosition(800.0f, 245.0f);
		this.setCurrentCameraZoom(1.0f);
	}
	
	

}
