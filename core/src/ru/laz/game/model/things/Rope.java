package ru.laz.game.model.things;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.laz.game.model.stages.GameLevel;

/**
 * Created by Dmitry Lazarev on 01.10.2017.
 */

public class Rope extends Thing {
    public Rope(float x, float y, float zDepth, float h, float w, String nodeName, GameLevel gameLevel) {
        super(x, y, zDepth, h, w, nodeName, gameLevel);
        Texture tex = new Texture(Gdx.files.internal("rope.png"));
        this.actorTex = new TextureRegion(tex);
        this.setBounds(x, y, w, h);
    }
}
