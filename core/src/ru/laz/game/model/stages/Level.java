package ru.laz.game.model.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import ru.laz.game.model.actors.MainActor;
import ru.laz.game.model.graph.GraphGame;
import ru.laz.game.model.things.StaticObject;
import ru.laz.game.model.things.Thing;
import ru.laz.game.view.render.RenderObject;
import ru.laz.game.view.ui.UI;

public class Level implements GameLevel {

	
	private MainActor mainActor;
	private GraphGame graph;

	float camAngle = 0.5f;
	private Sprite backgroundSpt;
	OrthographicCamera scCam;



	private Vector2 initalSceneCameraPosition; //need for parallax calculating
	OrthographicCamera uiCam;
	private UI ui;
	
	HashMap<String, Thing> things;
	HashMap<String, StaticObject> staticObjects;
	private Array<RenderObject> renderObjects;

	
	
	public Level() {
		staticObjects = new HashMap<String, StaticObject>();
		renderObjects = new Array<RenderObject>();

	}
	
	
	
	
	

@Override
	public void init(UI ui) {
		this.ui = ui;
		

		
		graph = new GraphGame();// create graph here (it belongs to this stage)
		graph.loadGraph();
		
	    things = new HashMap<String, Thing>();
	    
	    scCam = UI.getSceneCamera();
	    uiCam = UI.getUICamera();
	    

		
		QSortRender();//сразу обновляем порядок отрисовки объектов	    
	}






@Override
public void act(float delta) {  
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


	
	
	@Override
	public void setCamAngle(float angle) {
		this.camAngle = angle;
		
	}


	@Override
	public float getCamAngle() {
		return camAngle;
	}
	
	
	@Override
	public void draw() {
	}
	



	
	@Override
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



	@Override
	public Sprite getBackground() {
		return backgroundSpt;
	}



	@Override
	public HashMap<String, Thing> getThings() {
		return this.things;
	}



	@Override
	public HashMap<String, StaticObject> getStaticObjects() {
		return this.staticObjects;
	}


	@Override
	public void setMainActor(MainActor mainActor) {
		this.mainActor = mainActor;
		renderObjects.add(this.mainActor);
		
	}






	@Override
	public void addThing(String name, Thing thing) {
		things.put(name, thing);
		renderObjects.add(thing);
		
	}



	@Override
	public boolean removeThing(String name) {
		boolean ret = false;
		Thing thing = things.get(name);
		if ((things.remove(name) != null) && renderObjects.removeValue(thing, true))
			ret = true;
		return ret;
	}






	@Override
	public void addStaticObject(String name, StaticObject sObj) {
		staticObjects.put(name, sObj);
		//stage.addActor(sObj);
		renderObjects.add(sObj);
		
	}

	@Override
	public void removeStaticObject(String name) {
				staticObjects.remove(name);
	}






	@Override
	public Array<RenderObject> getRenderObjects() {
		return this.renderObjects;
	}


	@Override
	public Vector2 getInitalSceneCameraPosition() {
		return initalSceneCameraPosition;
	}

	@Override
	public void setInitalSceneCameraPosition(Vector2 initalSceneCameraPosition) {
		Gdx.app.log("Setting cam pos ", initalSceneCameraPosition.toString());
		this.initalSceneCameraPosition = initalSceneCameraPosition;
		Gdx.app.log("Setting cam pos ", this.initalSceneCameraPosition.toString());
	}

	
}
