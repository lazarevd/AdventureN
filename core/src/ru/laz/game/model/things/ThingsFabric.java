package ru.laz.game.model.things;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.things.instances.MixerStatic;
import ru.laz.game.model.things.instances.Mug;
import ru.laz.game.model.things.instances.Oil;
import ru.laz.game.model.things.instances.Pan;
import ru.laz.game.model.things.instances.Rope;
import ru.laz.game.model.things.instances.Stove;
import ru.laz.game.view.render.TextureFabric;

/**
 * Created by Dmitry Lazarev on 04.11.2017.
 */

 public class ThingsFabric {

    private interface Handler {
        ThingContainer genThing();
    }

    private static HashMap<String , String []> mixThings = new HashMap<String, String[]>();
    private static HashMap<String,Handler> thingsMethods = new HashMap<String,Handler>();



    public static void addRule(String firstThing, String secondThing, String resultThing) {
        String [] pair = {firstThing, secondThing};
        mixThings.put(resultThing, pair);
    }

    public static void init() {
        genComplThingsRules();
        genMethods();
    }


    private static void genComplThingsRules(){
        addRule("mug", "rope", "mug_with_rope");
    }

    private static void genMethods() {
        thingsMethods.put("mug_with_rope", new Handler() {
            @Override
            public ThingContainer genThing() {
                TextureFabric.addTexture("mug_with_rope", new TextureRegion(new Texture(Gdx.files.internal("mug_with_rope.png"))));
                return new ThingContainer("mug_with_rope", new Thing(true, 200, 200, 1.0f, 50, 100, "", "mug_with_rope"));
            }
        });

        thingsMethods.put("mug", new Handler() {
            @Override
            public ThingContainer genThing() {
                TextureFabric.addTexture("mug", new TextureRegion(new Texture(Gdx.files.internal("mug.png"))));
                return new ThingContainer("mug", new Mug(500, 200, 1.5f, 50, 50, "nodeMug", "mug"));
            }
        });


        thingsMethods.put("rope", new Handler() {
            @Override
            public ThingContainer genThing() {
        TextureFabric.addTexture("rope", new TextureRegion(new Texture(Gdx.files.internal("rope.png"))));
        return new ThingContainer("rope", new Rope(930,300, 1.5f, 120,60, "nodeRope", "rope"));
    }
});


        thingsMethods.put("mixerStatic", new Handler() {
            @Override
            public ThingContainer genThing() {
                MixerStatic mixerStatic = new MixerStatic(790, 230, 1.5f, 90, 130, "nodeRope", "dummy.png");
                mixerStatic.setInteractionThing("mug_with_rope");
                return new ThingContainer("mixerStatic", mixerStatic);
            }
        });


        thingsMethods.put("stove", new Handler() {
            @Override
            public ThingContainer genThing() {
                TextureFabric.addTexture("stove_on", new TextureRegion(new Texture(Gdx.files.internal("stove_on.png"))));
                Stove stove = new Stove(650, 174, 1.5f, 30,30, "nodeMug", "dummy.png");
                return new ThingContainer("stove", stove);
            }
        });


        thingsMethods.put("oil", new Handler() {
            @Override
            public ThingContainer genThing() {
                TextureFabric.addTexture("oil", new TextureRegion(new Texture(Gdx.files.internal("oil.png"))));
                Oil oil = new Oil(true, 550, 210, 1.4f,70,40,"nodeMug", "oil");
                oil.setRenderWidth(70);
                oil.setxShift(-14);
                return new ThingContainer("oil", oil);
            }
        });

        thingsMethods.put("pan", new Handler() {
            @Override
            public ThingContainer genThing() {
                Pan pan = new Pan(730,200,1.6f,30,70,"nodeRope", "dummy");
                pan.setRenderWidth(128);
                pan.setxShift(-32);
                pan.setRenderHeight(64);
                pan.setInteractionThing("oil");
                TextureFabric.addTexture("pan_with_oil", new TextureRegion(new Texture(Gdx.files.internal("pan_with_oil.png"))));
                TextureFabric.addAnimation("oil_boils", "pan_with_oil_ani.png", 3,1,512,256, 0.3f);
                return new ThingContainer("pan", pan);
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
