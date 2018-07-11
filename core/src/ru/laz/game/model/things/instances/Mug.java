package ru.laz.game.model.things.instances;

import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.things.Thing;

/**
 * Created by Dmitry Lazarev on 11.07.2018.
 */
public class Mug extends Thing {

    public Mug() {
        super();
    }


    public Mug(float x, float y, float zDepth, float h, float w, String nodeName, String textureName) {
        super(true, x,y,zDepth,h,w,nodeName,textureName);
    }

    @Override
    public void actOnClick() {

    }

    @Override
    public void actWithObject(ThingContainer otherThing) {

    }


}
