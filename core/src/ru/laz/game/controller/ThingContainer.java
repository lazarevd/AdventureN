package ru.laz.game.controller;

import ru.laz.game.model.things.instances.Thing;

/**
 * Created by Dmitry Lazarev on 10.06.2018.
 */
public class ThingContainer {

    private Thing thing;

    public Thing getThing() {
        return thing;
    }

    public String getThingName() {
        return thingName;
    }

    private String thingName;

    public ThingContainer(String thingName, Thing thing) {
        this.thing = thing;
        this.thingName = thingName;
    }
}
