package ru.laz.game.model.things;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.laz.game.model.graph.Polygon4Game;
import ru.laz.game.model.stages.Level;
import ru.laz.game.view.render.RenderObject;

public abstract class Thing implements RenderObject {//Наследуем от Group т.к. там есть удобные методы для трансформации объектов с помощью матриц

	private float oX;
	private float oY;


	private float xShift;
	private float yShift;
	private float width = 30;
	private float height = 30;
	private float renderWidth = 30;
	private float renderHeight = 30;
	public float zDepth = 0;
	private float renderScale = 1.0f;
	private Matrix4 transformMatrix;



	private float parallaxFactor = 0.0f;

	private String nodeName = "";

	
	TextureRegion actorTex;
	public Array<Polygon4Game> bodyPolys;//Координаты в локальной системе
	Level level;
	//private float heigth;
	
	
	public Thing(float x, float y, float zDepth, float h, float w, String nodeName, Level level) {
		
		this.setX(x);
		this.setY(y);
		this.width = w;
		this.height = h;
		this.nodeName = nodeName;
		this.renderHeight = h;
		this.renderWidth = w;
		this.zDepth = zDepth;
		bodyPolys = new Array<Polygon4Game>();
		this.level = level;
		defineBody();
		convertCoords();


	}
	
	
	public void defineBody() {//Массив вершина относительно объекта  (ноль координат вершин там, где XY вещи)
		float[] nvertices = new float[]{0,0,this.getWidth(), 0, this.getWidth(), this.getHeight(), 0, this.getHeight()};
		Polygon4Game poly = new Polygon4Game(nvertices, level.getGraph());
		updateVertices(poly.getVertices());
		bodyPolys.add(poly);
	}
	
	
	public void defineBody(float[] vertices) {//Массив вершина относительно объекта  (ноль координат вершин там, где XY вещи)
		Polygon4Game poly = new Polygon4Game(vertices, level.getGraph());
		poly.setVertices(updateVertices(poly.getVertices()));
		bodyPolys.add(poly);
	}
	


	//TODO Тут надо подумать, а то что-то геморно с конвертацией полигонов
	public boolean isHit(Vector2 xy) {	
		boolean ret = false;
		 //convertCoords();

		for (Polygon4Game poly : bodyPolys) {
			if (poly.isPointInside(xy))
				return true;
		}	
	return ret;
	}
	
	
	public void convertCoords() {//Из координат локальной в координаты предка
		/*
		bodyPolysGlobal.clear();
		for (Polygon4Game poly : bodyPolysLocal) {		
			float[] src = poly.getVertices();
			bodyPolysGlobal.add(new Polygon4Game(updateVertices(src), level.getGraph()));
		}
		*/
	}
	
	
	public TextureRegion getTexture() {
		return this.actorTex;
	}






	private Matrix4 recomputeTransformMatrix(float x, float y, float rotate, float sclX, float sclY){

		return new Matrix4();
	}


	public float[] updateVertices(float[] vertices) {//Чтобы полигон поддерживал все трансформации актера
		
	//	Matrix4 transform = computeTransform();//Получаем переходную матрицу (transformation matrix) родительской группы

		int n = vertices.length/2;//количество точек в полигоне
		Vector3[] newVec = new Vector3[n];//Создаем массив точек полигона. Vector3 т.к. его удобнее умножать на 4x4 матрицу переноса.
			
			int j = 0;
			for (int i = 0; i < newVec.length; i++) {
				newVec[i] = new Vector3(vertices[j], vertices[j+1], 0);//Создаем новый массив векторов (точек) заполняем его из массива координат
				//Gdx.app.log("Print vec", newVec[i].toString() + ", i " + i + ", j " + j);
				j+=2;
			}
			

			
			for (int i = 0; i < newVec.length; i++) {
				Vector3 curVec = new Vector3(newVec[i]);
			//	newVec[i] = curVec.mul(transform);
				//Gdx.app.log("Vectors", newVec[i].toString());
			}
			
			float[] ret = new float[n*2];//Создаем новый массив для вывода. Он вдвое больше массива векторов
			
			int k = 0;
			for (int i = 0; i < newVec.length; i++) {				
				ret[k] = newVec[i].x;
				ret[k+1] = newVec[i].y;
				//Gdx.app.log("Print vec", newVec[i].toString() + ", i " + i + ", j " + j);
				k+=2;
			} 
			//Gdx.app.log("print ret", " x1 " + ret[0] + " y1 " + ret[1] + ", x2 " + ret[2] + " y2 " + ret[3] + ", x3 " + ret[4] + " y3 " + ret[5] + ", x4 " + ret[6] + " y4 " + ret[7] + " ");
		return ret; 
	}



	@Override
	public float getParallaxFactor() {
		return parallaxFactor;
	}


	public void setParallaxFactor(float parallaxFactor) {
		this.parallaxFactor = parallaxFactor;
	}


	public void setWidth(float w) {
		this.width = w;
	}

	public void setHeight(float h) {
		this.height = h;
	}


	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	@Override
	public void setPosition(float x, float y, float zDepth) {
		this.oX = x;
		this.oY = y;
	}


	public void setXY(Vector2 xy) {
        this.oX = xy.x;
        this.oY = xy.y;
    }


    public Vector2 getXY() {
		return new Vector2(oX,oY);
	}


    public void setX(float x) {
        this.oX = x;
    }


    public void setY(float y) {
        this.oY = y;
    }


	public float getX() {
		return oX;
	}


	public float getY() {
		return oY;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public float getZDepth() {
		return this.zDepth;
	}



	public float getRenderX() {
		return oX+xShift;
	}
	

	public float getRenderY() {
		return oY+yShift;
	}


	public float getxShift() {
		return xShift;
	}

	public float getyShift() {
		return yShift;
	}

	public void setxShift(float xShift) {
		this.xShift = xShift;
	}

	public void setyShift(float yShift) {
		this.yShift = yShift;
	}

	@Override
	public void setRenderScale(float scale) {
		this.renderScale = scale;
		
	}

	@Override
	public float getRenderHeight() {
		return renderHeight*renderScale;
	}


	@Override
	public float getRenderWidth() {
		return renderWidth*renderScale;
	}
}
