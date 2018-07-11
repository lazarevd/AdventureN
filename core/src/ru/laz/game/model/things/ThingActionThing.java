package ru.laz.game.model.things;

import ru.laz.game.controller.ThingContainer;

/**
 * Created by Dmitry Lazarev on 27.06.2018.
 */
public abstract class ThingActionThing {

    public abstract void run(Thing thisThing, ThingContainer otherThing);

}
