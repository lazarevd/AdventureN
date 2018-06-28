package ru.laz.game.model.things.instances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.laz.game.controller.Controller;
import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.Thing;
import ru.laz.game.model.things.ThingAction;

/**
 * Created by Dmitry Lazarev on 23.06.2018.
 */
public class MixerStatic extends Thing {

    ThingAction thingAction = null;

    public MixerStatic(float x, float y, float zDepth, float h, float w, String nodeName, Level level) {
        super(false,x, y, zDepth, h, w, nodeName, new TextureRegion(new Texture(Gdx.files.internal("dummy.png"))), level);
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void actOnClick() {
        Gdx.app.log("actOnClick","MixerStatic");
    }

    @Override
    public void actWithObject(ThingContainer otherThing) {
        Gdx.app.log("actWithObject","otherThing: " + otherThing.getThingName());
        if (otherThing.getThingName().equals("mug_with_rope")) {
            otherThing.getThing().setCanBeTaken(true);
            Controller.moveThingTrunkToWorld(otherThing, 780, 320, 1.5f, 110, 140, Controller.getLevel());
        }
    }
}
