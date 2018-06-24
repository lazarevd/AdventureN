package ru.laz.game.view.render;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface RenderObject {

	public void setPosition(float x, float y);
	
	public void setPosition(float x, float y, float zDepth);
	
	public void setRenderScale(float scale);
	
	public TextureRegion getTexture();

	public void setParallaxFactor(float parallaxFactor);

	public float getParallaxFactor();
	
	public float getZDepth();
	
	public float getRenderX();
	
	public float getRenderY();

	public float getRenderHeight();
	
	public float getRenderWidth();

}
