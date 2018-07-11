package ru.laz.game.model.things;

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

    private static HashMap<String , String []> mixThings = new HashMap<String, String[]>();
    private static HashMap<String,Handler> thingsMethods = new HashMap<String,Handler>();


    private static void genComplThingsRules(){
        addRule("mug", "rope", "mug_with_rope");
    }

    public static void addRule(String firstThing, String secondThing, String resultThing) {
        String [] pair = {firstThing, secondThing};
        mixThings.put(resultThing, pair);
    }

    public static void init() {
        genComplThingsRules();
        genMethods();
    }

    private static void genMethods(){
        thingsMethods.put("mug_with_rope", new Handler() {
            @Override
            public ThingContainer genThing() {
                return new ThingContainer("mug_with_rope",new Thing(true, 0,0, 1.0f, 50,100, "", "mug_with_rope.png"));
            }
        });
    }


    public static ThingContainer genThing(String thingName) {
        return thingsMethods.get(thingName).genThing();
    }



    public  static ThingContainer getCompositeThing(String firstThing, String secondThing) {
        for (Map.Entry<String, String[]> entry : mixThings.entrySet()) {
            String[] tmpArr = entry.getValue();
            if(firstThing.equals(tmpArr[0]) && secondThing.equals(tmpArr[1]) || firstThing.equals(tmpArr[1]) && secondThing.equals(tmpArr[0])) {
                return genThing(entry.getKey());
            }
        }
        return null;
    }


}
