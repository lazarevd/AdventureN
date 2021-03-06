package ru.laz.game.model.things;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ru.laz.game.AGame;
import ru.laz.game.controller.ThingContainer;

public class Trunk implements Json.Serializable {


    private int COLUMNS = 6;
    private int ROWS = 5;
    private int COLUMN_SHIFT = 100;
    private int ROWS_SHIFT = 100;


    private HashMap<String, Thing> things  = new HashMap<String, Thing>();

    public Trunk() {

    }

    public void addToTrunk(ThingContainer thingContainer) {
        things.put(thingContainer.getThingName(), thingContainer.getThing());
        arrangeThings();
    }


    public void removeFromTrunk(ThingContainer thingContainer) {
        things.remove(thingContainer.getThingName());
        arrangeThings();
    }

    public HashMap<String, Thing> getThings(){
        return new HashMap<String, Thing>(things);
    }

    public void setThings(HashMap<String, Thing> things) {
        this.things = things;
    }

    public Thing getTrunkThing(String thingName) {
        return things.get(thingName);
    }


    public void genCompositeThing(ThingContainer firstThing, ThingContainer secondThing) {
        ThingContainer newThing = ThingsFabric.getCompositeThing(firstThing.getThingName(), secondThing.getThingName());
        if (newThing != null) {
            things.remove(firstThing.getThingName());
            things.remove(secondThing.getThingName());
            addToTrunk(newThing);
        }
    }


    public ThingContainer getHitItem(Vector2 xy) {
        for (Map.Entry<String, Thing> entry : things.entrySet()) {
            if (entry.getValue().isHit(xy)) {
                return new ThingContainer(entry.getKey(), entry.getValue());
            }
        }
        return null;
    }


    public Thing getHitActor(Vector2 xy) {
        for (Map.Entry<String, Thing> entry : things.entrySet()) {
            //Gdx.app.log("TRUNK", "hit actor " + entry.getKey() + " " + entry.getValue().getX() + ":" + entry.getValue().getY());
            if (entry.getValue().isHit(xy)) {
                return entry.getValue();
            }
        }

        return null;
    }


    public String getHitActorName(Vector2 xy) {
            for (Map.Entry<String, Thing> entry : things.entrySet()) {
                if (entry.getValue().isHit(xy)) {
                    return entry.getKey();
                }
            }

        return null;
    }

    public void arrangeThings() {
        int tmpRShift = ROWS_SHIFT;
        int tmpCShift = COLUMN_SHIFT;
        int tmpRowCount = 0;
        int tmpColCount = 0;

        for(Entry<String, Thing> entry : things.entrySet()) {
                    entry.getValue().setXY(new Vector2(tmpCShift, tmpRShift));
                    Gdx.app.log("Arrange", entry.getKey() + " " + entry.getValue().getXY().toString());
                    tmpCShift+=COLUMN_SHIFT;
                    tmpColCount+=1;
                    if (tmpColCount >= COLUMNS) {
                        tmpRShift += ROWS_SHIFT;
                        tmpCShift = COLUMN_SHIFT;
                    }
  }
    }


    @Override
    public void write(Json json) {
        Gdx.app.log("trunk ", "write " + things.size());
        json.writeFields(this);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        json.readFields(this,jsonData);
        Gdx.app.log("trunk ", "read " + things.size() + " " + this.toString());
        Gdx.app.log("game screen ", "read " + AGame.getGame().getGameScreen().toString());
    }
}
