package ru.laz.game.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Created by Dmitry Lazarev on 09.07.2018.
 */
public class TextureFabric {



    private static HashMap<String, TextureRegion> staticTextures = new HashMap<String, TextureRegion>();
    private static HashMap<String, Animation> animatedTextures = new HashMap<String, Animation>();


    public static void init() {
        staticTextures.put("dummy", new TextureRegion(new Texture(Gdx.files.internal("dummy.png"))));
    }

    public static Animation getAnimatedTexture(String animName) {
        return animatedTextures.get(animName);
    }


    public static TextureRegion getTexture(String texName) {
        return staticTextures.get(texName);
    }


    public static void addTexture(String name, TextureRegion textureRegion) {
        staticTextures.put(name, textureRegion);
    }

    public static void addAnimation(String name, String filename, int columns, int rows, int tileWidth, int tileHeight, float frameDuration) {
        Texture animationSheet;
        Animation animation;
        TextureRegion[] frames;
        animationSheet = new Texture(Gdx.files.internal(filename));
        frames = genTextureRegion(animationSheet, columns,rows, tileWidth, tileHeight);
        animation = new Animation(frameDuration, frames);
        animatedTextures.put(name, animation);
    }


    private static TextureRegion[] genTextureRegion(Texture sheet, int cols, int rows, int tileWidth, int tileHeight) {

        TextureRegion[] walkFrames;
        TextureRegion[][] tmp = TextureRegion.split(sheet, tileWidth, tileHeight);

        walkFrames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        return walkFrames;
    }
}
