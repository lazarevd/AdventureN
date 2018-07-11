package ru.laz.game.model.things.instances;

import ru.laz.game.controller.Controller;
import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.things.Thing;

/**
 * Created by Dmitry Lazarev on 11.07.2018.
 */
public class MixerStatic extends Thing {


    public MixerStatic() {
        super();
    }


    public MixerStatic(float x, float y, float zDepth, float h, float w, String nodeName, String textureName) {
        super(x,y,zDepth,h,w,nodeName,textureName);
    }

    @Override
    public void actOnClick() {

    }

    @Override
    public void actWithObject(ThingContainer otherThing) {
            if (otherThing.getThingName().equals(this.getInteractionThing())) {
                otherThing.getThing().setCanBeTaken(false);
                Controller.moveThingTrunkToWorld(otherThing, 780, 300, 1.5f, 110, 140);
                otherThing.getThing().setDone(true);
            }
    }
}
