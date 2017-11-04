package ru.laz.game.model.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import ru.laz.game.model.actors.MainActor;
import ru.laz.game.model.graph.GraphGame;
import ru.laz.game.model.things.StaticObject;
import ru.laz.game.model.things.instances.Thing;
import ru.laz.game.view.render.RenderObject;
import ru.laz.game.view.ui.UI;

public abstract class Level {

	
	private MainActor mainActor;
	private GraphGame graph;




	private float width;
	private float height;

	private Sprite backgroundSpt;
	OrthographicCamera scCam;



	private Vector2 initalSceneCameraPosition; //need for parallax calculating
	
	HashMap<String, Thing> things;
	HashMap<String, StaticObject> staticObjects;
	private Array<RenderObject> renderObjects;

	
	
	public Level(float width, float height) {
		this.width = width;
		this.height = height;
		staticObjects = new HashMap<String, StaticObject>();
		renderObjects = new Array<RenderObject>();

	}



	

	public void init() {
		graph = new GraphGame();// create graph here (it belongs to this stage)
		graph.loadGraph();
		
	    things = new HashMap<String, Thing>();
	    
	    scCam = UI.getSceneCamera();
	    

		
		QSortRender();//сразу обновляем порядок отрисовки объектов	    
	}


	public String getHitActor(Vector2 xy) {;
			for (Map.Entry<String, Thing> entry : things.entrySet()) {
				//Gdx.app.log("TRUNK", "hit actor " + entry.getKey() + " " + entry.getValue().getX() + ":" + entry.getValue().getY());
				if (entry.getValue().isHit(xy)) {
					return entry.getKey();
				}
			}
		return null;
	}


public void act(float delta) {
	mainActor.act(delta);
}


	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
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



	public Sprite getBackground() {
		return backgroundSpt;
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




	public void addThing(String name, Thing thing) {
		things.put(name, thing);
		renderObjects.add(thing);
		
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


	public void setInitalSceneCameraPosition(Vector2 initalSceneCameraPosition) {
		Gdx.app.log("Setting cam pos ", initalSceneCameraPosition.toString());
		this.initalSceneCameraPosition = initalSceneCameraPosition;
		Gdx.app.log("Setting cam pos ", this.initalSceneCameraPosition.toString());
	}

	
}
