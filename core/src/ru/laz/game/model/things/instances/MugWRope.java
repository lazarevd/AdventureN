package ru.laz.game.model.things.instances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.Thing;

/**
 * Created by Dmitry Lazarev on 05.11.2017.
 */

public class MugWRope extends Thing {

    public MugWRope(float x, float y, float zDepth, float h, float w, String nodeName, Level level){
            super(x, y, zDepth, h, w, nodeName, level);
            Texture tex = new Texture(Gdx.files.internal("mug_with_rope.png"));
            this.actorTex = new TextureRegion(tex);
    }

    @Override
    public void actOnClick() {

    }

    @Override
    public void actWithObject(ThingContainer otherThing) {

    }

}
