package ru.laz.game.view.render;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface RenderObject {

	public void setPosition(float x, float y);
	
	public void setPosition(float x, float y, float zDepth);
	
	public void setRenderWidth(float renderWidth);

	public void setRenderHeight(float renderHeight);
	
	public String getCurrentTextureName();

	public String getCurrentAnimationName();

	public void setCurrentAnimation(Animation animation);

	public void setCurrentTextureName(String textureName);

	public void setCurrentTexture(TextureRegion textureRegion);

	public TextureRegion getRenderTexture();

	public void setParallaxFactor(float parallaxFactor);

	public float getParallaxFactor();
	
	public float getZDepth();
	
	public float getRenderX();
	
	public float getRenderY();

	public float getRenderHeight();
	
	public float getRenderWidth();

}
