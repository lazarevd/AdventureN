package ru.laz.game.view.render;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Map.Entry;

import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.graph.EdgeGame;
import ru.laz.game.model.graph.NodeGame;
import ru.laz.game.model.graph.PointOnEdgeGame;
import ru.laz.game.model.graph.Polygon4Game;
import ru.laz.game.model.graph.Polygon4Game.DrawStat;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.Thing;
import ru.laz.game.view.ui.UI;
import ru.laz.game.view.ui.UIButton;

public class Render {
	
	private static ShapeRenderer shapeRenderer;//single instance
    private static SpriteBatch spriteBatch;//single instance
	static boolean edgeOn, polyOn, nodeOn;
    static BitmapFont font;    
    CharSequence str = "default name";
	Sprite trunkSprite;
	private Level level;
	private UI ui;


	
	public enum Colour {YELLOW, BLUE, RED, WHITE, GREEN};
	
	
	public Render(UI ui) {
		spriteBatch = getSpriteBatch();//single instance
		shapeRenderer = getShapeRenderer();//single instance
	    font = new BitmapFont();
	    this.ui = ui;//В конструкторе, потому что UI рисуем всегда
	}
	
	public void setLevel(Level level) {
		this.level = level;
	    trunkSprite = new Sprite(ui.getTrunkTex());
	}


	public void drawObjects(Level level) {



		if (level != null) {
			setSceneCameraMatrix();
			setCamKeyPosition(UI.getSceneCamera(), getCameraSpeed());
			level.QSortRender();
			for (RenderObject ro : level.getRenderObjects()) {
				Vector2 tmpCurrentPositionVector = new Vector2(UI.getSceneCamera().position.x, UI.getSceneCamera().position.y);
				Vector2 finalPosition = level.getInitalSceneCameraPosition().cpy().sub(tmpCurrentPositionVector).scl(ro.getParallaxFactor());
				TextureRegion tex = ro.getTexture();
				Render.drawActor(tex, ro.getRenderX()+finalPosition.x, ro.getRenderY()+finalPosition.y, ro.getRenderWidth(), ro.getRenderHeight());
			}

			for (Entry<String, Thing> th : level.getThings().entrySet()) {
				for (Polygon4Game poly : th.getValue().bodyPolys) {
					drawPolygon(poly, Colour.BLUE);
				}
				for (Polygon4Game poly : th.getValue().getWorldPolygons()) {
					drawPolygon(poly, Colour.RED);
				}
			}

			if (UI.GRAPH) {
				for (Entry<String, EdgeGame> entry : level.getGraph().getEdges().entrySet()) {
					drawEdge(entry.getValue(), Colour.WHITE);
				}
				for (Entry<String, NodeGame> entry : level.getGraph().getNodes().entrySet()) {
					drawNode(entry.getValue(), Colour.GREEN, 2);
				}

				//drawNode(level.getGraph().getStart(), Colour.RED, 3);
				//drawNode(level.getGraph().getFinish(), Colour.BLUE, 3);
				for (Entry<String, Polygon4Game> entry : level.getGraph().getPolygons().entrySet()) {
					drawPolygon(entry.getValue(), Colour.BLUE);
				}


			}

			setUICameraMatrix();
			drawButtons();

			//UI.getUIStage().draw();//Перерисовываем сцену с интерфейсом
			if (UI.isTrunk()) {
				drawTrunk();
			}

			drawPickObject();
		}
	}




	
	
	public static void drawPoint(Vector2 xy, int r, Colour colour) {
		//Gdx.app.log("draw", "s");
		
		//setSceneSRCameraMatrix();
		shapeRenderer.begin(ShapeType.Filled);
		switch (colour) {
		case YELLOW:
		shapeRenderer.setColor(1, 1, 0, 1);
		break;
		case BLUE:
		shapeRenderer.setColor(0, 0, 1, 1);
		break;
		case RED:
		shapeRenderer.setColor(1, 0, 0, 1);	
		break;
		case GREEN:
		shapeRenderer.setColor(0, 1, 0, 1);	
		break;
		default:
		shapeRenderer.setColor(1, 1, 1, 1);		
		}
		
		
		
		shapeRenderer.circle(xy.x, xy.y, r);
		shapeRenderer.end();
		//setSceneSRCameraMatrix();
	}
	
	public static void drawLine(Vector2 xy1, Vector2 xy2, Colour colour) {
		shapeRenderer.begin(ShapeType.Line);
		switch (colour) {
		case YELLOW:
		shapeRenderer.setColor(1, 1, 0, 1);
		break;
		case BLUE:
		shapeRenderer.setColor(0, 0, 1, 1);
		break;
		case RED:
		shapeRenderer.setColor(1, 0, 0, 1);	
		break;
		case GREEN:
		shapeRenderer.setColor(0, 1, 0, 1);	
		break;
		default:
		shapeRenderer.setColor(1, 1, 1, 1);		
		}
		
		
		
		shapeRenderer.line(xy1.x, xy1.y, xy2.x, xy2.y);
		shapeRenderer.end();
	}
	
	
	public static void drawLine(Vector2 xy1, Vector2 xy2, String label, Colour colour) {
		shapeRenderer.begin(ShapeType.Line);
		switch (colour) {
		case YELLOW:
		shapeRenderer.setColor(1, 1, 0, 1);
		break;
		case BLUE:
		shapeRenderer.setColor(0, 0, 1, 1);
		break;
		case RED:
		shapeRenderer.setColor(1, 0, 0, 1);	
		break;
		case GREEN:
		shapeRenderer.setColor(0, 1, 0, 1);	
		break;
		default:
		shapeRenderer.setColor(1, 1, 1, 1);		
		}
		
		
		
		shapeRenderer.line(xy1.x, xy1.y, xy2.x, xy2.y);
		shapeRenderer.end();
		getSpriteBatch().begin();
		font.draw(getSpriteBatch(), label, xy2.x, xy2.y - 10);
		getSpriteBatch().end();
		
	}
	
	public static void drawLine(int x1, int y1, int x2, int y2,  Colour colour) {
		shapeRenderer.begin(ShapeType.Line);
		switch (colour) {
		case YELLOW:
		shapeRenderer.setColor(1, 1, 0, 1);
		break;
		case BLUE:
		shapeRenderer.setColor(0, 0, 1, 1);
		break;
		case RED:
		shapeRenderer.setColor(1, 0, 0, 1);	
		break;
		case GREEN:
		shapeRenderer.setColor(0, 1, 0, 1);	
		break;
		default:
		shapeRenderer.setColor(1, 1, 1, 1);		
		}
		
		
		
		shapeRenderer.line(x1, y1, x2, y2);
		shapeRenderer.end();
	}
	

	public static SpriteBatch getSpriteBatch() {
		if(spriteBatch == null) {
			spriteBatch = new SpriteBatch();
		}
		//Gdx.app.log("getSpriteBatch", spriteBatch.toString());
		return spriteBatch;
	}
	
	
	public static ShapeRenderer getShapeRenderer() {
		
		if(shapeRenderer == null) {
			shapeRenderer = new ShapeRenderer();
		}
		//Gdx.app.log("getShapeRenderer", shapeRenderer.toString());
		return shapeRenderer;
	}



	public static void setSpriteBatch(SpriteBatch spriteBatch) {
		Render.spriteBatch = spriteBatch;
	}

	
	public static void drawCursor() {
		//Gdx.app.log("world  cursor", Controller.getCursor(true).toString());
		//Render.drawPoint(Controller.getCursor(true), 3, Colour.WHITE);
		//Gdx.app.log("screen cursor", Controller.getCursor(false).toString());
		//Render.drawPoint(Controller.getCursor(false), 4, Colour.RED);
	}

	public void setTrunkSprite(Texture tex) {
		this.trunkSprite = new Sprite(tex);
		this.trunkSprite.setSize(640, 480);
	}


	public void drawNode(NodeGame nodeGame, Colour colour, float size) {
		
		if (nodeGame != null) {
		switch (colour) {
		case YELLOW:
		shapeRenderer.setColor(1, 1, 0, 1);
		break;
		case BLUE:
		shapeRenderer.setColor(0, 0, 1, 1);
		break;
		case RED:
		shapeRenderer.setColor(1, 0, 0, 1);	
		break;
		case GREEN:
			shapeRenderer.setColor(0, 1, 0, 1);	
			break;
		default:
		shapeRenderer.setColor(1, 1, 1, 1);		
		}
		
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.circle(nodeGame.getX(), nodeGame.getY(), size);
		shapeRenderer.end();
		
		
	    spriteBatch.begin();
	    /*
	    try {
	    font.draw(spriteBatch, nodeGame.getThisId().toString(), nodeGame.getX(), nodeGame.getY() - 10);
	    } catch (NullPointerException nex) {//Catch exception when node has no name.
	    font.draw(spriteBatch, "???", nodeGame.getX(), nodeGame.getY() - 10);	
	    }
	    */
	    spriteBatch.end();
		
		}
	}



    private void drawEdge(EdgeGame edgeGame, Colour colour) {
		
		if (edgeGame != null) {
		
			

		shapeRenderer.begin(ShapeType.Line);
		switch (colour) {
		case YELLOW:
		shapeRenderer.setColor(1, 1, 0, 1);
		break;
		case BLUE:
		shapeRenderer.setColor(0, 0, 1, 1);
		break;
		case RED:
		shapeRenderer.setColor(1, 0, 0, 1);	
		break;
		case WHITE:
		shapeRenderer.setColor(1, 1, 1, 1);
		break;
		default:
		shapeRenderer.setColor(1, 1, 1, 1);		
		}
		//setSceneSRCameraMatrix();	
		
		try {
			
			float[] coords = edgeGame.getNodeCoords();
	shapeRenderer.line(coords[0], coords[1], coords[2], coords[3]);
		} 
		

		
		catch (NullPointerException nex) {
			Gdx.app.log("Can`t draw edge :", nex.toString());
		}
		
	shapeRenderer.end();
		}
	}



    private static void drawPolygon(Polygon4Game polygon4Game, Colour colour) {
		
		float[] vertices = polygon4Game.getVertices();
		
		if(polygon4Game.drawStat == DrawStat.HIGHLIGHT) {
			shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1);	
			} else {
		
		switch (colour) {
		case YELLOW:
		shapeRenderer.setColor(1, 1, 0, 1);
		break;
		case BLUE:
		shapeRenderer.setColor(0, 0, 1, 1);
		break;
		case RED:
		shapeRenderer.setColor(1, 0, 0, 1);	
		break;
		case WHITE:
		shapeRenderer.setColor(1, 1, 1, 1);
		break;
		default:
		shapeRenderer.setColor(1, 1, 1, 1);		
		}
		
			}
		
		shapeRenderer.begin(ShapeType.Line);//This draw lines.
		
		shapeRenderer.polygon(vertices);
		
		shapeRenderer.end();
		
		

		shapeRenderer.begin(ShapeType.Filled); //And this draw vertices.
		shapeRenderer.setColor(1, 1, 0, 1);
		shapeRenderer.circle(vertices[0], vertices[1], 5);
		shapeRenderer.circle(vertices[2], vertices[3], 5);
		shapeRenderer.circle(vertices[4], vertices[5], 5);
		shapeRenderer.circle(vertices[6], vertices[7], 5);
		  
		shapeRenderer.end();

	
	}

    private void drawPOE(PointOnEdgeGame poe) {
		poe.setPointPosition();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.circle(poe.getPosition().x,poe.getPosition().y , 5);
		shapeRenderer.end();
	
	}
	
	/** Эти процедуры используются для преобразования объектов отрисовки из мировых координат в координаты камеры.
		Всего исользуется 2 камеры - для интерфейса (неподвитжная) и для остальных объектов (подвижная) **/











    private static void setUICameraMatrix() {
		spriteBatch.setProjectionMatrix(UI.getUICamera().combined);//Set default stage camera here
		shapeRenderer.setProjectionMatrix(UI.getUICamera().combined);//Set default stage camera here
	}

    private static void setSceneCameraMatrix() {
		spriteBatch.setProjectionMatrix(UI.getSceneCamera().combined);//Set default stage camera here
		shapeRenderer.setProjectionMatrix(UI.getSceneCamera().combined);//Set default stage camera here
	}



    private void drawTrunkBack() {
		spriteBatch.begin();
		trunkSprite.draw(spriteBatch);
		spriteBatch.end();
		
	}


/*
	public Vector2 getCameraActorSpeed(Vector2 actorPosition) {

	}
*/

    private Vector2 getCameraSpeed() {

		float x = 0,y = 0;

		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT))
		{
			x -= Gdx.graphics.getDeltaTime() * 500f;
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT))
		{
			x += Gdx.graphics.getDeltaTime() * 500f;
		}

		if(Gdx.input.isKeyPressed(Keys.DPAD_UP))
		{
			y += Gdx.graphics.getDeltaTime() * 500f;
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN))
		{
			y -= Gdx.graphics.getDeltaTime() * 500f;
		}

		return new Vector2(x,y);
	}



    private void setCamKeyPosition(OrthographicCamera sceneCamera, Vector2 speedVector) {

		float x = sceneCamera.position.x;
		float y = sceneCamera.position.y;

//
		sceneCamera.position.add(new Vector3(speedVector.x, speedVector.y, 0));
		//uiCamera.position.add(new Vector3(speedVector.x*0.5f, speedVector.y*0.5f, 0));

		sceneCamera.update();
		//Gdx.app.log("MATRIX UI", sceneCamera.projection.toString());
		//Gdx.app.log("MATRIX SCENE", sceneCamera.projection.toString());

		//Gdx.app.log("MATRIX ", " proj: \n" + sceneCamera.projection.toString() + " view: \n" + sceneCamera.view);
	}


	private void drawPickObject(){
    	ThingContainer pickThingCon = UI.getPickThing();
    	if (pickThingCon != null) {

			Render.drawActor(pickThingCon.getThing().getTexture(), pickThingCon.getThing().getX(), pickThingCon.getThing().getRenderY(), pickThingCon.getThing().getWidth(), pickThingCon.getThing().getHeight());
		}
	}
	
	private void drawTrunk() {
		setUICameraMatrix();
			drawTrunkBack();
			int x0 = 100;
			for (Entry<String, Thing> entry : ui.getTrunk().getThings().entrySet()) {//Рисуем объекты в сундуке
				TextureRegion tex = entry.getValue().getTexture();
				Render.drawActor(tex, entry.getValue().getX(), entry.getValue().getY(), entry.getValue().getWidth(), entry.getValue().getHeight());
				for (Polygon4Game poly : entry.getValue().bodyPolys) {
					drawPolygon(poly, Colour.BLUE);
				}
				x0 += 100;
			}
	}



    private static void drawButtons() {
		for (Entry<String, UIButton> entry : UI.uiButtons.entrySet()) {
			UIButton tButton = entry.getValue();
			drawActor(tButton.getButtonTex(), tButton.getPosition().x, tButton.getPosition().y, tButton.getSize().x, tButton.getSize().y);
            //drawPolygon(tButton.getPolygon(),Colour.BLUE);
		}
	}


    private static void drawActor(TextureRegion tr, float x, float y, float w, float h) {
		/**Основной класс для отрисовки актера. Стараемся соблюдать MVC.
		 * Для того, чтобы камера действовала на объект нужн оприменить к Batch объекта
		 * матрицу трансформации координат требуемой камеры.
		**/
		getSpriteBatch().begin();
		getSpriteBatch().draw(tr,x, y, w, h);
		getSpriteBatch().end();
		//setUISBCameraMatrix();// Возвращаем дефолтную матрицу камеры, чтобы не двигался интерфейс и т.п.

		
		//Gdx.app.log("screen", UI.getCursor(false).toString());
		//Gdx.app.log("world ", UI.getCursor(true).toString());		
	}
	
	
}
