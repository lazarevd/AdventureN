package ru.laz.game.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.laz.game.model.graph.Polygon4Game;

/**
 * Created by Dmitry Lazarev on 15.10.2017.
 */

public abstract class UIButton {

    private Vector2 size = new Vector2();
    private Vector2 position = new Vector2();
    private boolean clicked = false;
    private TextureRegion buttonTex;
    private Polygon4Game poly = new Polygon4Game();


    public UIButton(Texture texture, float x, float y, float h, float w) {
        this.buttonTex = new TextureRegion(texture);
        this.position.x = x;
        this.position.y = y;
        this.size.x = h;
        this.size.y = w;
        float[] nvertices = new float[]{x,y,x+w, x, x+w, y+h, x, y+h};
        poly.setVertices(nvertices);
    }

    public abstract void clicked();



    public boolean isHit(Vector2 xy) {
        boolean ret = false;
        Gdx.app.log("GET HIT ", xy + " " + poly.getPrintable());
            if (poly.isPointInside(xy))
                return true;
        return ret;
    }


    public Vector2 getSize() {
        return size;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isClicked() {
        return clicked;
    }

    public TextureRegion getButtonTex() {
        return buttonTex;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setButtonTex(TextureRegion buttonTex) {
        this.buttonTex = buttonTex;
    }

}