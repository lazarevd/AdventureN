package ru.laz.game.model.things.instances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.things.Thing;
import ru.laz.game.view.ui.UI;

/**
 * Created by Dmitry Lazarev on 06.07.2018.
 */
public class Pan extends Thing {


    public Pan() {
        super();
    }


    public Pan(float x, float y, float zDepth, float h, float w, String nodeName, String textureName) {
        super(x,y,zDepth,h,w,nodeName,textureName);
    }


    @Override
    public void actOnClick() {

    }

    @Override
    public void actWithObject(ThingContainer otherThing) {
        if (otherThing.getThingName().equals("oil")) {
            this.setCurrentTexture(new TextureRegion(new Texture(Gdx.files.internal("pan_with_oil.png"))));
            this.setDone(true);
            UI.getTrunk().removeFromTrunk(otherThing);
        }
    }

}