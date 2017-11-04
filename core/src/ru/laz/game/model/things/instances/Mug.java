package ru.laz.game.model.things.instances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.laz.game.model.stages.Level;


public class Mug extends Thing {

	public Mug(float x, float y, float zDepth, float h, float w, String nodeName, Level level) {
		super(x, y, zDepth, h, w, nodeName, level);
		Texture tex = new Texture(Gdx.files.internal("mug.png"));
		this.actorTex = new TextureRegion(tex);
		//this.setBounds(x, y, w, h);
	}

}
