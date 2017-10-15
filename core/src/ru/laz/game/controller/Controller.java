package ru.laz.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

import ru.laz.game.model.actors.MoveWork;
import ru.laz.game.model.actors.TakeWork;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.Thing;
import ru.laz.game.model.things.Trunk;
import ru.laz.game.view.ui.UI;
import ru.laz.game.view.ui.UIButton;

import static ru.laz.game.controller.Controller.getCursor;


class SceneGestureListener implements GestureDetector.GestureListener {

	private Level level;

	protected SceneGestureListener(Level gl) {
		level = gl;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Gdx.app.log("SCENE TOUCH DOWN", x+ " " + y + " " + pointer + " " + button);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		Vector2 touchPosL = getCursor(new Vector2(x,y),false);

		if (Controller.getHitButton(touchPosL) != null) return true;


		Vector2 touchPosW = getCursor(new Vector2(x,y),true);


		Gdx.app.log("TAP(", x+" "+ y + "); L: " + touchPosL.toString() + "; W: " + touchPosW.toString());


		String curThingName = Controller.getHitActor(touchPosW);

		Thing curThing = level.getThings().get(curThingName);
		if (curThing != null) {
			level.getMainActor().addWork(new TakeWork(curThingName, level));
			level.getMainActor().addWork(new MoveWork(curThing, touchPosW, level));
		}
		else {
			level.getMainActor().addWork(new MoveWork(touchPosW, level));
		}
		Vector3 vec3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Vector3 vecOrg = vec3.cpy();

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
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

	public TrunkGestureListener(Level level) {this.level = level;}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Gdx.app.log("TOUCH DOWN", x+ " " + y + " " + pointer + " " + button);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.log("TAP", x+" "+ y + " " + count + " " + button);
		if(x > 1800) UI.setTRUNK(false);
		Controller.setSceneControls();
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		Gdx.app.log("LONG PRESS", x+" "+ y);
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Gdx.app.log("FLING ", velocityX + " " + velocityY + " "+ button);
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Gdx.app.log("PAN ", x + " " + y + " " + deltaX + " " + deltaY);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		Gdx.app.log("PAN STOP ", x + " " + y + " " + pointer + " " + button);
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		Gdx.app.log("ZOOM ", initialDistance + " " + distance);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		Gdx.app.log("PINCH ", initialPointer1 + " " + initialPointer2 + " " +pointer1 + " " + pointer2);
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



	public static Vector2 getCursor(Vector2 input, boolean world) {
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



	public static String getHitActor(Vector2 xy) {
		if (level != null) {
			HashMap<String, Thing> things = level.getThings();
			for (Map.Entry<String, Thing> entry : things.entrySet()) {
				if (entry.getValue().isHit(xy)) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

	public static void setTrunkControls() {
		Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 1.1f, 0.15f, new TrunkGestureListener(Controller.getLevel())));
	}

	public static void setSceneControls() {
		Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 1.1f, 0.15f, new SceneGestureListener(Controller.getLevel())));
	}


	}
