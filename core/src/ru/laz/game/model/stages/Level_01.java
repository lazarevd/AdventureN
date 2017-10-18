package ru.laz.game.model.stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.laz.game.model.actors.MainActor;
import ru.laz.game.model.things.Mug;
import ru.laz.game.model.things.Rope;
import ru.laz.game.model.things.StaticObject;






public class Level_01 extends Level {//Wrapper for Gdx Stage, Graph and all stage stuff.

	public static int BACKGROUND_WIDTH = 1240;
	public static int BACKGROUND_HEIGHT = 540;

	Music rainMusic;


	public Level_01 () {	
		super(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
	}	
	
	@Override
	public void init() {
		super.init();

	    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	    rainMusic.setLooping(true);
	   // rainMusic.play();

	    
	    StaticObject back = new StaticObject(new TextureRegion(new Texture(Gdx.files.internal("back.png"))), 0, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
	    addStaticObject("back", back);
	    StaticObject fr3 = new StaticObject(new TextureRegion(new Texture(Gdx.files.internal("fr3.png"))), 908, 6, 30, 512, 256);
		fr3.setParallaxFactor(0.5f);
		addStaticObject("fr3", fr3);
	    
	    Mug mug  = new Mug(520,200, 1.5f, 50,50, "nodeMug", this);
	    addThing("mug",mug);

		Rope rope  = new Rope(930,300, 1.5f, 120,60, "nodeRope", this);
		addThing("rope",rope);
	    
	    setMainActor(new MainActor(this, 1000, 100, 20, 2));

	    scCam.position.x = 1000.0f;
		scCam.position.y = 255.0f;
		scCam.zoom = 0.35f;
		this.setInitalSceneCameraPosition(new Vector2(scCam.position.x,scCam.position.y));


		

	}
	
	

}
