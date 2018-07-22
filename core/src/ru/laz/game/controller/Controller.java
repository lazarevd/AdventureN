package ru.laz.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Map;

import ru.laz.game.AGame;
import ru.laz.game.model.actors.MoveWork;
import ru.laz.game.model.actors.PutWork;
import ru.laz.game.model.actors.TakeWork;
import ru.laz.game.model.actors.ThingWork;
import ru.laz.game.model.stages.Location;
import ru.laz.game.model.things.Thing;
import ru.laz.game.view.ui.UI;
import ru.laz.game.view.ui.UIButton;

import static ru.laz.game.controller.Controller.convertCoordinates;


public class Controller {

    private static Location location;


    public static void moveThingWorldToTrunk(ThingContainer thingContainer) {
		AGame.getGame().getGameScreen().getTrunk().addToTrunk(thingContainer);
		thingContainer.getThing().setWidth(60);
		thingContainer.getThing().setHeight(60);
        location.removeThing(thingContainer.getThingName());
    }

    public static void moveThingTrunkToWorld(ThingContainer thingContainer, float x, float y, float zDepth, float h, float w) {
        Thing thing = thingContainer.getThing();
        thing.setX(x);
        thing.setY(y);
        thing.setzDepth(zDepth);
        thing.setHeight(h);
        thing.setWidth(w);
        location.addThing(thingContainer);
        AGame.getGame().getGameScreen().getTrunk().removeFromTrunk(thingContainer);
    }

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

    public static void setLocation(Location location) {
        Controller.location = location;
    }

    public static Location getLocation() {
        return location;
    }


    public static boolean getThingDoneStatus(String thingName) {
		return location.getThings().get(thingName).isDone();
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

    public static void setThingInteractionControls() {
        Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 0.7f, 0.15f, new ThingInteractionListener(Controller.getLocation())));
        Gdx.app.log("LISTNER", "SET TRUNK");
    }

    public static void setSceneControls() {
        Gdx.input.setInputProcessor(new GestureDetector(20, 0.4f, 0.7f, 0.15f, new SceneGestureListener(Controller.getLocation())));
        Gdx.app.log("LISTNER", "SET SCENE");
    }

}


class SceneGestureListener implements GestureDetector.GestureListener {

	private Location location;

	protected SceneGestureListener(Location gl) {
		location = gl;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Vector2 sceneCoords = convertCoordinates(x,y,true);
		Vector2 uiCoords = convertCoordinates(x,y,false);
		//Gdx.app.log("SCENE TOUCH DOWN", x+ " " + y + " scene : " + sceneCoords.x + ";" + sceneCoords.y + " / " + uiCoords.x + " " + uiCoords.y);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
        location.getMainActor().clearWorks();
		//Gdx.app.log("SCENE TAP ", "");
		Vector2 touchPosL = convertCoordinates(x,y,false);

		if (Controller.getHitButton(touchPosL) != null) return false;
		Vector2 touchPosW = convertCoordinates(x,y,true);

		ThingContainer curThing = null;
        curThing = location.getHitActor(touchPosW);
		if (curThing != null) {
			if (curThing.getThing().isCanBeTaken()) {
				location.getMainActor().addWork(new TakeWork(curThing, location));
				location.getMainActor().addWork(new MoveWork(curThing.getThing(), touchPosW, location));
			} else {
                location.getMainActor().addWork(new ThingWork(curThing, location));
                location.getMainActor().addWork(new MoveWork(curThing.getThing(), touchPosW, location));
            }
		}
		else {
			location.getMainActor().addWork(new MoveWork(touchPosW, location));
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y)
	{
		//Gdx.app.log("SCENE LONG PRESS", "");
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

class ThingInteractionListener implements GestureDetector.GestureListener {

	Location location;


	public ThingInteractionListener(Location location) {this.location = location;}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		//Gdx.app.log("THING TAP", x+" "+ y + " " + count + " " + button);
		Vector2 uiCoords = convertCoordinates(x,y,false);
		if(uiCoords.x > (UI.UI_WIDTH-(UI.UI_WIDTH/6))) {
			Gdx.app.log("HIDE TRUNK","");
			UI.setTrunk(false);
			Controller.setSceneControls();
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		//Gdx.app.log("THING LONG PRESS", x+" "+ y);
		if (UI.isTrunk()) {
			ThingContainer th = AGame.getGame().getGameScreen().getTrunk().getHitItem(convertCoordinates(x, y, false));
            Gdx.app.log("hit thing",th.getThingName());
            UI.setPickThing(new ThingContainer(th.getThingName(), th.getThing()));
		}
			return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		//Gdx.app.log("THING FLING ", velocityX + " " + velocityY + " "+ button);
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		//Gdx.app.log("THING PAN ", deltaX + " " + deltaY);
		if (UI.getPickThing() != null) {
			Vector2 uiCoords = convertCoordinates(x,y,false);
			UI.getPickThing().getThing().setX(uiCoords.x);
			UI.getPickThing().getThing().setY(uiCoords.y);
            if(uiCoords.x > (UI.UI_WIDTH-(UI.UI_WIDTH/6))) {
                Gdx.app.log("HIDE TRUNK","");
                UI.setTrunk(false);
            }
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		//Gdx.app.log("THING PAN STOP ", x + " " + y + " " + pointer + " " + button);
		if (UI.getPickThing() != null) {
			if (UI.isTrunk()) {
				ThingContainer secondPick = AGame.getGame().getGameScreen().getTrunk().getHitItem(convertCoordinates(x, y, false));
				if (secondPick != null) {
                    AGame.getGame().getGameScreen().getTrunk().genCompositeThing(UI.getPickThing(), secondPick);
			}
			} else {
				ThingContainer targetObject = location.getHitActor(convertCoordinates(x, y, true));
                ThingContainer pickObject = UI.getPickThing();
                Vector2 touchPosW = convertCoordinates(x,y,true);
                if (targetObject != null && pickObject != null) {
                	if (targetObject.getThing().getInteractionThing().equals(pickObject.getThingName())) {
						location.getMainActor().addWork(new PutWork(pickObject, targetObject, location));
						location.getMainActor().addWork(new MoveWork(targetObject.getThing(), touchPosW, location));
					}
				}
				UI.setPickThing(null);
				Controller.setSceneControls();
			}
            AGame.getGame().getGameScreen().getTrunk().arrangeThings();
	}
		UI.setPickThing(null);
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		//Gdx.app.log("TRUNK ZOOM ", initialDistance + " " + distance);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		//Gdx.app.log("trunk PINCH ", initialPointer1 + " " + initialPointer2 + " " +pointer1 + " " + pointer2);
		return false;
	}

	@Override
	public void pinchStop() {

		//Gdx.app.log("PINCH STOP ", "");
	}
}





