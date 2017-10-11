package ru.laz.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

import ru.laz.game.model.actors.MoveWork;
import ru.laz.game.model.actors.TakeWork;
import ru.laz.game.model.stages.GameLevel;
import ru.laz.game.model.things.Thing;
import ru.laz.game.model.things.Trunk;
import ru.laz.game.view.ui.UI;

import static ru.laz.game.controller.Controller.getCursor;


class SceneGestureListener implements GestureDetector.GestureListener {

	private GameLevel gameLevel;

	protected SceneGestureListener(GameLevel gl) {
		gameLevel = gl;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Gdx.app.log("SCENE TOUCH DOWN", x+ " " + y + " " + pointer + " " + button);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.log("TAP", x+" "+ y + " " + count + " " + button);
		Vector2 touchPos = getCursor(new Vector2(x,y),true);

		String curThingName = Controller.getHitActor(touchPos);

		Thing curThing = gameLevel.getThings().get(curThingName);
		if (curThing != null) {
			gameLevel.getMainActor().addWork(new TakeWork(curThingName, gameLevel));
			gameLevel.getMainActor().addWork(new MoveWork(curThing, touchPos, gameLevel));
		}
		else {
			gameLevel.getMainActor().addWork(new MoveWork(touchPos, gameLevel));
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
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Gdx.app.log("TOUCH DOWN", x+ " " + y + " " + pointer + " " + button);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.log("TAP", x+" "+ y + " " + count + " " + button);
		if(x > 1800) UI.setTRUNK(false);
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
	
	private static GameLevel gameLevel;
	private static Trunk trunk;

	private GestureDetector trunkGestureDetector;
	private GestureDetector sceneGestureDetector;



	public static Vector2 getCursor(Vector2 input, boolean world) {
		Vector3 vec3 = new Vector3();

		if (!world) {
			vec3.set(new Vector3(input.x, input.y, 0));
		} else {
			vec3.set(UI.getViewportScene().unproject(new Vector3(input.x, input.y, 0)));
		}
		Vector2 ret = new Vector2(vec3.x, vec3.y);
		return ret;
	}



	public static Vector2 getCursor(boolean world) {
		Vector3 vec3 = new Vector3();
		
		if (!world) {
		vec3.set(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		} else {
		vec3.set(UI.getViewportScene().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
		}
		
		Vector2 ret = new Vector2(vec3.x, vec3.y);
		return ret;
	}


	public static String getHitActor(Vector2 xy) {
		if (gameLevel != null) {
			HashMap<String, Thing> things = gameLevel.getThings();
			for (Map.Entry<String, Thing> entry : things.entrySet()) {
				if (entry.getValue().isHit(xy)) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

	public static void setTrunkControls() {
		Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 1.1f, 0.15f, new TrunkGestureListener()));
	}

	public static void setSceneControls(GameLevel gl) {
		Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 1.1f, 0.15f, new SceneGestureListener(gl)));
	}


/*
	private void processTrunk() {

		Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 1.1f, 0.15f, new TrunkGestureListener()));

		Vector2 touchPos = new Vector2();
		touchPos.set(getCursor(true).x, getCursor(true).y);
		String curThingName = getHitThingTrunk(touchPos);
		Thing curThing = gameLevel.getThings().get(curThingName);
		Gdx.app.log("TRUNK ", curThingName+"");
		if (curThing != null) {
			curThing.setX(getCursor(true).x);
			curThing.setY(getCursor(true).y);
		}

	}


	private void processScene() {

		Vector2 touchPos = new Vector2();
		touchPos.set(getCursor(true).x, getCursor(true).y);
		String curThingName = getHitActor(touchPos);
		Thing curThing = gameLevel.getThings().get(curThingName);
		if (curThing != null) {
			gameLevel.getMainActor().addWork(new TakeWork(curThingName, gameLevel));
			gameLevel.getMainActor().addWork(new MoveWork(curThing, touchPos, gameLevel));
		}
		else {
			gameLevel.getMainActor().addWork(new MoveWork(touchPos, gameLevel));
		}
		Vector3 vec3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Vector3 vecOrg = vec3.cpy();
	}







	public String getHitThingTrunk(Vector2 xy) {
		if (trunk != null) {
			for (Entry<String, Thing> entry : trunk.getThings().entrySet()) {
                Gdx.app.log("HIT ", entry.getKey()+ " "+ getCursor(true).x+":"+getCursor(true).y + " " + entry.getValue().getXY().toString());
                if (entry.getValue().isHit(xy)) {
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	

	
	public void setGameLevel (GameLevel gameLevel) {
		this.gameLevel = gameLevel;
	}

*/

	}
