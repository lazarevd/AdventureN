package ru.laz.game.model.things;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

import ru.laz.game.view.render.RenderObject;

public class StaticObject extends Group implements RenderObject {

	
	public float x, y, zDepth, width, heigth;
	private float renderHeight = 30;
	private float renderWidth = 30;
	private float parallaxFactor = 0.0f;
	private String currentTextureName  = "";
	private String currentAnimationName = "";

	private transient Animation currentAnimation;   //default static texture
	private transient TextureRegion currentTexture; //animation
	private transient TextureRegion renderTexture; //return this to render



    public StaticObject () {}


    public StaticObject (String texture) {
        this.currentTextureName = texture;
	}
	
	public StaticObject (String texture, float x, float y, float zDep) {
		this(texture);
		this.x = x;
		this.y = y;
		this.zDepth = zDep;
	}
	
	public StaticObject (String texture, float x, float y, float zDep, float width, float heigth) {
		this(texture, x, y, zDep);
		this.renderHeight = heigth;
		this.renderWidth = width;
		this.width = width;
		this.heigth = heigth;
		
	}
	
	@Override
	public TextureRegion getCurrentAnimationTexture() {
		return this.currentTexture;
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
	public float getRenderHeight() {
		return this.renderHeight;
	}

	@Override
	public float getRenderWidth() {
		return this.renderWidth;
	}

	public void setRenderHeight(float renderHeight) {
		this.renderHeight = renderHeight;
	}

	@Override
	public void setRenderWidth(float renderWidth) {
		this.renderWidth = renderWidth;
	}

	@Override
	public String getCurrentTextureName() {
		return currentTextureName;
	}

	@Override
	public void setCurrentTextureName(String textureName) {
		this.currentTextureName = textureName;
	}

	@Override
	public String getCurrentAnimationName() {
		return currentAnimationName;
	}

    @Override
    public void setCurrentAnimation(Animation animation) {
        this.currentAnimation = animation;
    }

	@Override
    public void setCurrentTexture(TextureRegion textureRegion) {
        this.currentTexture = textureRegion;
    }


}
