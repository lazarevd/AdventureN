package ru.laz.game.model.things.instances;

import ru.laz.game.AGame;
import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.things.Thing;

/**
 * Created by Dmitry Lazarev on 06.07.2018.
 */
public class Stove extends Thing {

    public Stove() {
        super();
    }


    public Stove(float x, float y, float zDepth, float h, float w, String nodeName, String textureName) {
        super(x,y,zDepth,h,w,nodeName,textureName);
    }


    public void actOnClick() {
        this.setCurrentTextureName("stove_on");
        Thing pan = AGame.getGame().getGameScreen().getCurrentLocation().getThings().get("pan");
        if (pan.getCurrentTextureName().equals("pan_with_oil")) {
            pan.setCurrentAnimationName("oil_boils");
        }
        this.setDone(true);
    }

    @Override
    public void actWithObject(ThingContainer otherThing) {

    };

}
