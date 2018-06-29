package ru.laz.game.model.things;

import ru.laz.game.controller.ThingContainer;

/**
 * Created by Dmitry Lazarev on 27.06.2018.
 */
public interface ThingActionThing {

    public void run(Thing thisThing, ThingContainer otherThing);

}
