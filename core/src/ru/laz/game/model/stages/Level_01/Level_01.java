package ru.laz.game.model.stages.Level_01;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.laz.game.controller.Controller;
import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.actors.MainActor;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.StaticObject;
import ru.laz.game.model.things.Thing;
import ru.laz.game.model.things.ThingAction;
import ru.laz.game.model.things.ThingActionThing;
import ru.laz.game.view.ui.UI;


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
	    
	    Thing mug  = new Thing(true, 500,200, 1.5f, 50,50, "nodeMug", new TextureRegion(new Texture(Gdx.files.internal("mug.png"))), this);
	    addThing("mug",mug);

		Thing rope  = new Thing(true,930,300, 1.5f, 120,60, "nodeRope", new TextureRegion(new Texture(Gdx.files.internal("rope.png"))), this);
		addThing("rope",rope);

		Thing mixerStatic = new Thing(790, 230, 1.5f, 90,130, "nodeRope", new TextureRegion(new Texture(Gdx.files.internal("dummy.png"))), this);

		mixerStatic.setInteractionThing("mug_with_rope");

		mixerStatic.setActWithObject(new ThingActionThing() {
			@Override
			public void run(Thing thisThing, ThingContainer otherThing) {
				if (otherThing.getThingName().equals(thisThing.getInteractionThing())) {
					otherThing.getThing().setCanBeTaken(false);
					Controller.moveThingTrunkToWorld(otherThing, 780, 300, 1.5f, 110, 140, Controller.getLevel());
					otherThing.getThing().setDone(true);
				}
			}
		});
		addThing("mixerStatic", mixerStatic);

	    Thing stove = new Thing(650, 174, 1.5f, 30,30, "nodeMug", new TextureRegion(new Texture(Gdx.files.internal("dummy.png"))), Controller.getLevel());
        stove.setActOnClick(new ThingAction() {
            @Override
            public void run(Thing thisThing) {
                thisThing.setActorTex(new TextureRegion(new Texture(Gdx.files.internal("stove_on.png"))));
                thisThing.setDone(true);
            }
        });
	    addThing("stove", stove);

	    Thing oil = new Thing(true, 550, 210, 1.4f,70,40,"nodeMug", new TextureRegion(new Texture(Gdx.files.internal("oil.png"))), Controller.getLevel());
        oil.setRenderWidth(70);
        oil.setxShift(-14);
	    addThing("oil", oil);

	    Thing pan = new Thing(730,200,1.6f,30,70,"nodeRope", new TextureRegion(new Texture(Gdx.files.internal("dummy.png"))), Controller.getLevel());
        pan.setRenderWidth(128);
        pan.setxShift(-32);
        pan.setRenderHeight(64);
        pan.setInteractionThing("oil");

        pan.addAnimation("oil_boils", "pan_with_oil_ani.png", 3,1,512,256, 0.3f);
        pan.setCurrentAnimation("oil_boils");
        pan.setActWithObject(new ThingActionThing() {
            @Override
            public void run(Thing thisThing, ThingContainer otherThing) {
                if (otherThing.getThingName().equals("oil")) {
                    thisThing.setActorTex(new TextureRegion(new Texture(Gdx.files.internal("pan_with_oil.png"))));
                    thisThing.setDone(true);
                    UI.getTrunk().removeFromTrunk(otherThing);
                }
            }
        });


	    addThing("pan", pan);


	    setMainActor(new MainActor(this, 1000, 100, 20, 2));

	    scCam.position.x = 800.0f;
		scCam.position.y = 245.0f;
		scCam.zoom = 1.0f;
		this.setInitalSceneCameraPosition(new Vector2(scCam.position.x,scCam.position.y));
	}
	
	

}
