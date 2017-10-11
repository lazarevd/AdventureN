package ru.laz.game.model.stages;


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


public interface GameLevel  {

	public void init(UI ui);

	public void act(float delta);
	
	public void draw();
	
	public GraphGame getGraph();
	
	public void setGraph(GraphGame graph);
	
	public void setCamAngle(float angle);
	
	public float getCamAngle();
	
	public Sprite getBackground();
	
	public void addThing(String name, Thing thing);
	
	public boolean removeThing(String name);
	
	public HashMap<String, Thing> getThings();
	
	public MainActor getMainActor();
	
	public void setMainActor(MainActor mainActor);
	
	public HashMap<String, StaticObject> getStaticObjects();
	
	public void addStaticObject(String name, StaticObject sObj);
	
	public void removeStaticObject(String name);

	public Array<RenderObject> getRenderObjects();

	public Vector2 getInitalSceneCameraPosition();

	public void setInitalSceneCameraPosition(Vector2 initalSceneCameraPosition);
	
	public void QSortRender();

	
}
