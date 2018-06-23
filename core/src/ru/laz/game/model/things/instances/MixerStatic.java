package ru.laz.game.model.things.instances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.Thing;

/**
 * Created by Dmitry Lazarev on 23.06.2018.
 */
public class MixerStatic extends Thing {

    public MixerStatic(float x, float y, float zDepth, float h, float w, String nodeName, Level level) {
        super(false,x, y, zDepth, h, w, nodeName, level);
        Texture tex = new Texture(Gdx.files.internal("dummy.png"));
        this.actorTex = new TextureRegion(tex);
    }

    @Override
    public void actOnClick() {
        Gdx.app.log("actOnClick","MixerStatic");
    }

    @Override
    public void actWithObject(ThingContainer otherThing) {
        Gdx.app.log("actWithObject","otherThing");
    }
}
