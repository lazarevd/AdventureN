package ru.laz.game.model.things.instances;

import com.badlogic.gdx.Gdx;

import ru.laz.game.AGame;
import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.things.Thing;

/**
 * Created by Dmitry Lazarev on 06.07.2018.
 */
public class Pan extends Thing {


    public Pan() {
        super();
    }


    public Pan(float x, float y, float zDepth, float h, float w, String nodeName, String textureName) {
        super(x,y,zDepth,h,w,nodeName,textureName);
    }


    @Override
    public void actOnClick() {

    }

    @Override
    public void actWithObject(ThingContainer otherThing) {
        Gdx.app.log("oil", "actWObj");
        if (otherThing.getThingName().equals("oil")) {
            if  (AGame.getGame().getGameScreen().getCurrentLocation().getThings().get("stove").isDone()) {
                this.setCurrentAnimationName("oil_boils");
                this.setDone(true);
            } else {
                this.setCurrentTextureName("pan_with_oil");
            }
            AGame.getGame().getGameScreen().getTrunk().removeFromTrunk(otherThing);
        }
    }

}