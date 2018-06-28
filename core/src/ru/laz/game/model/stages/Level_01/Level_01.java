package ru.laz.game.model.stages.Level_01;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.laz.game.model.actors.MainActor;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.StaticObject;
import ru.laz.game.model.things.Thing;
import ru.laz.game.model.things.ThingAction;


public class Level_01 extends Level {//Wrapper for Gdx Stage, Graph and all stage stuff.


	Music rainMusic;


	public Level_01 () {	
		super(1024, 768);
	}	
	
	@Override
	public void init() {
		super.init();

	    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	    rainMusic.setLooping(true);
	   // rainMusic.play();

	    
	    StaticObject back = new StaticObject(new TextureRegion(new Texture(Gdx.files.internal("back.png"))), 0, 0, 0, 1240, 540);
	    addStaticObject("back", back);
	    StaticObject fr3 = new StaticObject(new TextureRegion(new Texture(Gdx.files.internal("fr3.png"))), 908, 6, 30, 512, 256);
		fr3.setParallaxFactor(0.5f);
		addStaticObject("fr3", fr3);
	    
	    Thing mug  = new Thing(true, 520,200, 1.5f, 50,50, "nodeMug", new TextureRegion(new Texture(Gdx.files.internal("mug.png"))), this);
	    addThing("mug",mug);

		Thing rope  = new Thing(true,930,300, 1.5f, 120,60, "nodeRope", new TextureRegion(new Texture(Gdx.files.internal("rope.png"))), this);
		addThing("rope",rope);

		Thing mixerStatic = new Thing(780, 210, 1.5f, 110,140, "nodeRope", new TextureRegion(new Texture(Gdx.files.internal("dummy.png"))), this);
		mixerStatic.setActWithObject(new ThingAction() {
			@Override
			public void run() {
			}
		});
		addThing("mixerStatic", mixerStatic);

	    
	    setMainActor(new MainActor(this, 1000, 100, 20, 2));

	    scCam.position.x = 800.0f;
		scCam.position.y = 245.0f;
		scCam.zoom = 1.0f;
		this.setInitalSceneCameraPosition(new Vector2(scCam.position.x,scCam.position.y));
	}
	
	

}
