package ru.laz.game.model.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;

import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.actors.MainActor;
import ru.laz.game.model.graph.GraphGame;
import ru.laz.game.model.things.StaticObject;
import ru.laz.game.model.things.Thing;
import ru.laz.game.view.render.RenderObject;
import ru.laz.game.view.ui.UI;

public class Location implements Json.Serializable {

	
	private MainActor mainActor;
	private float width = 1024;
	private float height = 768;
	private HashMap<String, Thing> things;
	private HashMap<String, StaticObject> staticObjects;
    private static Viewport viewportScene;
    private static OrthographicCamera scCam;
	private Vector2 initalSceneCameraPosition; //need for parallax calculating
	private String graphName = "source.graph";
	private Array<Vector2> cameraStaticPositions;

	private transient Array<Vector2> cameraMoveSpeedVectors;
	private transient Array<RenderObject> renderObjects;
	private transient GraphGame graph;

	public Location() {
        staticObjects = new HashMap<String, StaticObject>();
        renderObjects = new Array<RenderObject>();
        things = new HashMap<String, Thing>();
		graph = new GraphGame();// create graph here (it belongs to this stage)
        cameraStaticPositions = new Array<Vector2>();
        cameraMoveSpeedVectors = new Array<Vector2>();
   }




    public void initSaved() {
       //Gdx.app.log("initing ", "base class");
        viewportScene = new FillViewport(UI.UI_WIDTH, UI.UI_HEIGHT, scCam); //4x3 aspectratio i don`t know why, but it works
        for (Map.Entry<String,Thing> th : things.entrySet()) {
            renderObjects.add(th.getValue());
        }

        for (Map.Entry<String,StaticObject> th : staticObjects.entrySet()) {
            renderObjects.add(th.getValue());
        }
        renderObjects.add(mainActor);
        graph.loadGraph(graphName);
        QSortRender();//сразу обновляем порядок отрисовки объектов
    }
	

	public void init() {
        scCam = new OrthographicCamera();
        scCam.setToOrtho(false);
        viewportScene = new FillViewport(UI.UI_WIDTH, UI.UI_HEIGHT, scCam); //4x3 aspectratio i don`t know why, but it works
        graph.loadGraph(graphName);
		QSortRender();//сразу обновляем порядок отрисовки объектов	    
	}


	public void setCameraPosition(float x, float y) {
        scCam.position.x=x;
        scCam.position.y=y;
    }

	public ThingContainer getHitActor(Vector2 xy) {;
			for (Map.Entry<String, Thing> entry : things.entrySet()) {
				//Gdx.app.log("TRUNK", "hit actor " + entry.getKey() + " " + entry.getValue().getX() + ":" + entry.getValue().getY());
				if (entry.getValue().isHit(xy)) {
					return new ThingContainer(entry.getKey(), entry.getValue());
				}
			}
		return null;
	}


	private void defineCameraMove() {

	    Vector2 tmpVector = new Vector2();
	    float distance = 9999.9f;
	    for (int i = 0; i < cameraStaticPositions.size; i++) {
            float tmpDist = sceneCameraPosition().dst(cameraStaticPositions.get(i));
            if(tmpDist <= distance) {
                distance = tmpDist;
                tmpVector = cameraStaticPositions.get(i);
            }
        }

        

    }

    public Vector2 sceneCameraPosition() {
	    return new Vector2(scCam.position.x, scCam.position.y);
    }


public void act(float delta) {
	mainActor.act(delta);
	for (Map.Entry<String,Thing> th : things.entrySet()) {
		th.getValue().act(delta);
	}
}


	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}


    public static Viewport getViewportScene() {
        return viewportScene;
    }

    public static OrthographicCamera getSceneCamera() {
        return scCam;
    }

    public void QSortRender() {
        Array<RenderObject> inObj = renderObjects;
        Array<RenderObject> retObj = QsortRo(inObj, 0, renderObjects.size - 1);
        renderObjects = retObj;
    }

private static  Array<RenderObject> QsortRo (Array<RenderObject> arr, int l, int r) {

	if (r > 0) {
	
int i, j;
i = l;
j = r;
float mid = arr.get((r+l)/2).getZDepth();
RenderObject temp;
do {
	while (arr.get(i).getZDepth() < mid) {
	i++;
	}
	while (arr.get(j).getZDepth() > mid) {
	j--;
	}
	if (i <= j) {
	temp = arr.get(i);
	arr.set(i,arr.get(j));
	arr.set(j, temp);
	i++;
	j--;
	}
	}	while (i < j);
		if (l<j) {
			QsortRo(arr,l,j);
		}

		if(i<r) {
			QsortRo(arr,i,r);
		}
	}
		return arr;
		
}


	public void draw() {
	}
	

	public MainActor getMainActor() {
		return this.mainActor;
	}

	public void sortRenderLayers() {
		
		//renderObjects
	}




	

	public GraphGame getGraph() {
		return graph;
	}
	
	public void setGraph(GraphGame graph) {
		this.graph = graph;
	}


	public HashMap<String, Thing> getThings() {
		return this.things;
	}


	public HashMap<String, StaticObject> getStaticObjects() {
		return this.staticObjects;
	}


	public void setMainActor(MainActor mainActor) {
		this.mainActor = mainActor;
		renderObjects.add(this.mainActor);
	}




	public void addThing(ThingContainer thingContainer) {
		things.put(thingContainer.getThingName(), thingContainer.getThing());
		renderObjects.add(thingContainer.getThing());
	}


	public boolean removeThing(String name) {
		boolean ret = false;
		Thing thing = things.get(name);
		if ((things.remove(name) != null) && renderObjects.removeValue(thing, true))
			ret = true;
		return ret;
	}




	public void addStaticObject(String name, StaticObject sObj) {
		staticObjects.put(name, sObj);
		//stage.addActor(sObj);
		renderObjects.add(sObj);
		
	}

	public void removeStaticObject(String name) {
				staticObjects.remove(name);
	}




	public Array<RenderObject> getRenderObjects() {
		return this.renderObjects;
	}


	public Vector2 getInitalSceneCameraPosition() {
		return initalSceneCameraPosition;
	}


	public void setInitalSceneCameraPosition(float x, float y) {
		this.initalSceneCameraPosition = new Vector2(x,y);
	}

	public void setCurrentCameraZoom(float zoom) {
        this.scCam.zoom = zoom;
    }

    @Override
    public void write(Json json) {
        //Gdx.app.log("writing", this.getClass().getName());
        json.writeFields(this);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        //Gdx.app.log("initing location", "initSaved");
        json.readFields(this,jsonData);
        initSaved();
    }
}
