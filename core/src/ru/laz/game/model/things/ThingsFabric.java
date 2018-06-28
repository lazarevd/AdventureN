package ru.laz.game.model.things;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import ru.laz.game.controller.ThingContainer;

/**
 * Created by Dmitry Lazarev on 04.11.2017.
 */

 class ThingsFabric {

    private interface Handler {
        ThingContainer genThing();
    }

    private HashMap<String , String []> mixThings = new HashMap<String, String[]>();
    private HashMap<String,Handler> thingsMethods = new HashMap<String,Handler>();


    private void genComplThingsRules(){
        addRule("mug", "rope", "mug_with_rope");
    }

    public void addRule(String firstThing, String secondThing, String resultThing) {
        String [] pair = {firstThing, secondThing};
        mixThings.put(resultThing, pair);
    }

    public ThingsFabric() {
        genComplThingsRules();
        genMethods();
    }

    private void genMethods(){
        thingsMethods.put("mug_with_rope", new Handler() {
            @Override
            public ThingContainer genThing() {
                return new ThingContainer("mug_with_rope",new Thing(true, 0,0, 1.0f, 50,100, "", new TextureRegion(new Texture(Gdx.files.internal("mug_with_rope.png"))), null));
            }
        });
    }


    public ThingContainer genThing(String thingName) {
        Thing ret = null;
        return thingsMethods.get(thingName).genThing();
    }



    public ThingContainer getCompositeThing(String firstThing, String secondThing) {
        for (Map.Entry<String, String[]> entry : mixThings.entrySet()) {
            String[] tmpArr = entry.getValue();
            if(firstThing.equals(tmpArr[0]) && secondThing.equals(tmpArr[1]) || firstThing.equals(tmpArr[1]) && secondThing.equals(tmpArr[0])) {
                return genThing(entry.getKey());
            }
        }
        return null;
    }


}
