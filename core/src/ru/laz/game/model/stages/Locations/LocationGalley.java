package ru.laz.game.model.stages.Locations;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

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
/*
		MixerStatic mixerStatic = new MixerStatic(790, 230, 1.5f, 90,130, "nodeRope", "dummy.png" );
		mixerStatic.setInteractionThing("mug_with_rope");
		addThing("mixerStatic", mixerStatic);

		TextureFabric.addTexture("stove_on", new TextureRegion(new Texture(Gdx.files.internal("stove_on.png"))));
		Stove stove = new Stove(650, 174, 1.5f, 30,30, "nodeMug", "dummy.png");
	    addThing("stove", stove);

		TextureFabric.addTexture("oil", new TextureRegion(new Texture(Gdx.files.internal("oil.png"))));
		Oil oil = new Oil(true, 550, 210, 1.4f,70,40,"nodeMug", "oil");
        oil.setRenderWidth(70);
        oil.setxShift(-14);
	    addThing("oil", oil);

		Pan pan = new Pan(730,200,1.6f,30,70,"nodeRope", "dummy");
        pan.setRenderWidth(128);
        pan.setxShift(-32);
        pan.setRenderHeight(64);
        pan.setInteractionThing("oil");
        TextureFabric.addTexture("pan_with_oil", new TextureRegion(new Texture(Gdx.files.internal("pan_with_oil.png"))));
        TextureFabric.addAnimation("oil_boils", "pan_with_oil_ani.png", 3,1,512,256, 0.3f);
        pan.setCurrentAnimationName("oil_boils");
	    addThing("pan", pan);

*/

	    scCam.position.x = 800.0f;
		scCam.position.y = 245.0f;
		scCam.zoom = 1.0f;
		this.setInitalSceneCameraPosition(new Vector2(scCam.position.x,scCam.position.y));
	}
	
	

}