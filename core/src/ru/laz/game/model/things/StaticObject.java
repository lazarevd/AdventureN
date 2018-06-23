package ru.laz.game.model.things;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

import ru.laz.game.view.render.RenderObject;

public class StaticObject extends Group implements RenderObject {

	
	public float x, y, zDepth, width, heigth;
	public TextureRegion textureRegion;

	private float renderHeigth = 30;
	private float renderWidth = 30;
	private float renderScale = 1.0f;
	private float parallaxFactor = 0.0f;

	
	public StaticObject (TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
	public StaticObject (TextureRegion textureRegion, float x, float y, float zDep) {
		this(textureRegion);
		this.x = x;
		this.y = y;
		this.zDepth = zDep;
	}
	
	public StaticObject (TextureRegion textureRegion, float x, float y, float zDep, float width, float heigth) {
		this(textureRegion, x, y, zDep);
		this.renderHeigth = heigth;
		this.renderWidth = width;
		this.width = width;
		this.heigth = heigth;
		
	}
	
	@Override
	public TextureRegion getTexture() {
		return this.textureRegion;
	}

	@Override
	public void setParallaxFactor(float parallaxFactor) {
		this.parallaxFactor = parallaxFactor;
	}

	@Override
	public float getParallaxFactor() {
		return this.parallaxFactor;
	}

	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.x = y;
	}

	@Override
	public void setPosition(float x, float y, float zDepth) {
		setPosition(x,y);
		this.zDepth = zDepth;
	}

	@Override
	public void setSize(float w, float h) {
		this.width = w;
		this.heigth = h;	
	}

	@Override
	public float getRenderX() {
		return this.x;
	}
	
	@Override
	public float getRenderY() {
		return this.y;
	}
	
	@Override
	public float getWidth() {
		return this.width;
	}
	
	
	@Override
	public float getHeight() {
		return this.heigth;
	}

	@Override
	public float getZDepth() {
		return this.zDepth;
	}

	@Override
	public void setRenderScale(float scale) {
		super.setScale(scale);
		
	}

	public  float getRenderScale() {
		return this.renderScale;
	}

	@Override
	public float getRenderHeight() {
		return this.renderHeigth*renderScale;
	}

	@Override
	public float getRenderWidth() {
		return this.renderWidth*renderScale;
	}

}
