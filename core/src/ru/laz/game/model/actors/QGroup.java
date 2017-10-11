package ru.laz.game.model.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class QGroup extends Group {
	
	
	
	public void setParallaxScale(int cameraHeight) {
		
		Float scale = 1 - this.getY()/cameraHeight;
		this.setScale(scale);
	}
	
	
	@Override
	public void draw (Batch batch, float parentAlpha) {
		//Gdx.app.log("QGroup combined: \n", this.getStage().getCamera().combined.toString());
		batch.setProjectionMatrix(this.getStage().getCamera().combined);
		if (isTransform()) applyTransform(batch, computeTransform());
		drawChildren(batch, parentAlpha);
		if (isTransform()) resetTransform(batch);
	}

}

