package ru.laz.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Map;

import ru.laz.game.model.actors.MoveWork;
import ru.laz.game.model.actors.TakeWork;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.instances.Thing;
import ru.laz.game.model.things.Trunk;
import ru.laz.game.view.ui.UI;
import ru.laz.game.view.ui.UIButton;

import static ru.laz.game.controller.Controller.convertCoordinates;


class SceneGestureListener implements GestureDetector.GestureListener {

	private Level level;

	protected SceneGestureListener(Level gl) {
		level = gl;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Vector2 sceneCoords = convertCoordinates(x,y,true);
		Gdx.app.log("SCENE TOUCH DOWN", x+ " " + y + " scene : " + sceneCoords.x + ";" + sceneCoords.y + " " + pointer + " " + button);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.log("SCENE TAP ", "");
		Vector2 touchPosL = convertCoordinates(x,y,false);

		if (Controller.getHitButton(touchPosL) != null) return false;
		Vector2 touchPosW = convertCoordinates(x,y,true);

		String curThingName = level.getHitActor(touchPosW);
		Thing curThing = level.getThings().get(curThingName);
		if (curThing != null) {
			level.getMainActor().addWork(new TakeWork(curThingName, level));
			level.getMainActor().addWork(new MoveWork(curThing, touchPosW, level));
		}
		else {
			level.getMainActor().addWork(new MoveWork(touchPosW, level));
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y)
	{ Gdx.app.log("SCENE LONG PRESS", "");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public void pinchStop() {

	}
}

class TrunkGestureListener implements GestureDetector.GestureListener {

	Level level;
	Thing pickThing = null;
	String pickThingName = null;

	public TrunkGestureListener(Level level) {this.level = level;}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		pickThing = null;
		pickThingName = "";
		Vector2 uiCoords = convertCoordinates(x,y,false);
		Gdx.app.log("TRUNK TOUCH DOWN", x+ " " + y + " scene : " + uiCoords.x + ";" + uiCoords.y + " " + pointer + " " + button);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.log("TRUNK TAP", x+" "+ y + " " + count + " " + button);
		Vector2 uiCoords = convertCoordinates(x,y,false);
		if(uiCoords.x > (UI.UI_WIDTH-(UI.UI_WIDTH/6))) {
			Gdx.app.log("HIDE TRUNK","");
			UI.setTRUNK(false);
			Controller.setSceneControls();
		}

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		Gdx.app.log("TRUNK LONG PRESS", x+" "+ y);
		pickThingName = UI.getTrunk().getHitActor(convertCoordinates(x,y,false));
		pickThing = UI.getTrunk().getThings().get(pickThingName);
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Gdx.app.log("TRUNK FLING ", velocityX + " " + velocityY + " "+ button);
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Gdx.app.log("TRUNK PAN ", pickThing + " " + x + " " + y + " " + deltaX + " " + deltaY);
		if (pickThing != null) {
			Vector2 uiCoords = convertCoordinates(x,y,false);
			pickThing.setX(uiCoords.x);
			pickThing.setY(uiCoords.y);
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		Gdx.app.log("TRUNK PAN STOP ", x + " " + y + " " + pointer + " " + button);
		//pickThing=null;
		//UI.getTrunk().arrangeThings();
		String secondPickThingName = UI.getTrunk().getHitActor(convertCoordinates(x,y,false));
		if(pickThingName !=null && pickThingName!="" && secondPickThingName !=null && secondPickThingName!="") {
			Thing newThing = UI.getTrunk().genCompositeThing(pickThingName, secondPickThingName);
		} else {
			UI.getTrunk().arrangeThings();
		}

		pickThing = null;
		pickThingName = "";
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		Gdx.app.log("TRUNK ZOOM ", initialDistance + " " + distance);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		Gdx.app.log("trunk PINCH ", initialPointer1 + " " + initialPointer2 + " " +pointer1 + " " + pointer2);
		return false;
	}

	@Override
	public void pinchStop() {
		Gdx.app.log("PINCH STOP ", "");
	}
}







public class Controller {



	private static Level level;
	private static Trunk trunk;

	private GestureDetector trunkGestureDetector;
	private GestureDetector sceneGestureDetector;



	public static Vector2 convertCoordinates(float x, float y, boolean world) {
		return convertCoordinates(new Vector2(x,y), world);
	}


	public static Vector2 convertCoordinates(Vector2 input, boolean world) {//convert device screen (pixel) coords to UI or World coords
		Vector3 vec3 = new Vector3();

		if (!world) {
			vec3.set(UI.getViewportUI().unproject(new Vector3(input.x, input.y, 0)));
		} else {
			vec3.set(UI.getViewportScene().unproject(new Vector3(input.x, input.y, 0)));
		}
		Vector2 ret = new Vector2(vec3.x, vec3.y);
		return ret;
	}





	public static void setLevel(Level level) {
		Controller.level = level;
	}

	public static Level getLevel() {
		return level;
	}

	public static String getHitButton(Vector2 xy) {
		for (Map.Entry<String,UIButton> entry : UI.uiButtons.entrySet()) {
			if(entry.getValue().isHit(xy)) {
				entry.getValue().clicked();
				return entry.getKey();
			}
		}
		return null;
	}





	public static void setTrunkControls() {
		Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 1.1f, 0.15f, new TrunkGestureListener(Controller.getLevel())));
		Gdx.app.log("LISTNER", "SET TRUNK");
	}

	public static void setSceneControls() {
		Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 1.1f, 0.15f, new SceneGestureListener(Controller.getLevel())));
		Gdx.app.log("LISTNER", "SET SCENE");
	}


	}
