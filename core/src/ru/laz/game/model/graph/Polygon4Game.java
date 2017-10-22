package ru.laz.game.model.graph;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map.Entry;

import ru.laz.game.view.render.Render;


public class Polygon4Game extends Polygon {
	
	
	Array<String> pointsOnEdges;
	public enum DrawStat {NORMAL, SELECTED, HIGHLIGHT}; 
	public DrawStat drawStat;

	private float renderScale = 1.0f;

	float zHeight; //Use height for ladders for example
	private float distance;//the same for hero size
	private Array<String> childNodes = new Array<String>();

	public Polygon4Game() {
		super();
		pointsOnEdges = new Array<String>();
	}

	
	
	public Polygon4Game(float[] vertices) {
		this.setVertices(vertices);
	}


	public String getPrintable() {
		return "[" + getVertices()[0] + ";" + getVertices()[1] +"] " +
				"[" + getVertices()[2] + ";" + getVertices()[3] +"] " +
				"[" + getVertices()[4] + ";" + getVertices()[5] +"] " +
				"[" + getVertices()[6] + ";" + getVertices()[7] +"] ";
	}



	public void addChildNode(String name) {
		childNodes.add(name);
	}
	
	public boolean removeChildNode(String name) {
		return childNodes.removeValue(name, true);
	}


	public float getRenderScale() {
		return renderScale;
	}

	public void setRenderScale(float renderScale) {
		this.renderScale = renderScale;
	}


	
	public Array<String> getChildNodes() {
		return this.childNodes;
	}
	
	@Override
	public void setVertices (float[] vertices) {
		
		if (vertices.length == 8) {
		super.setVertices(vertices);
		} else throw new IllegalArgumentException("polygons must contain only 4 points.");

	}
	
	


	


	
	public Array<String> getPointsOnEdge() {
		return this.pointsOnEdges;
	}
	
	

	
	
	public Vector2 getVertexXY(int i) { //Returns vertex. Vertex not an object, but
									//we return only x and y components of the float array of vertexes.
									//This made only for compatibility to libgdx polygon
		Vector2 vertexXY = new Vector2();
		
			vertexXY.x = this.getVertices()[getVertexId(i)[0]];
			vertexXY.y = this.getVertices()[getVertexId(i)[1]];
		
		return vertexXY;
		
	}
	

	
	
	public int[] getVertexId(int i) {
		
		int[] ids = new int[2];
		
		switch (i) {
		case 1:
			ids[0] = 0;
			ids[1] = 1;
			break;
		case 2:
			ids[0] = 2;
			ids[1] = 3;
			break;
		case 3:
			ids[0] = 4;
			ids[1] = 5;
			break;
		case 4:
			ids[0] = 6;
			ids[1] = 7;
			break;
		}
		
		
		return ids;
		
	}
	
	
	
	
	public Vector2[] getEdge(int j) {
		Vector2[] edge = new Vector2[2];
		edge[0] = new Vector2(0,0);
		edge[1] = new Vector2(0,0);//Have to initialize
		
		
		switch (j) {
		case 1:
			edge[0].x = this.getVertices()[0];
			edge[0].y = this.getVertices()[1];
			edge[1].x = this.getVertices()[2];
			edge[1].y = this.getVertices()[3];
			break;
		case 2:
			edge[0].x = this.getVertices()[2];
			edge[0].y = this.getVertices()[3];
			edge[1].x = this.getVertices()[4];
			edge[1].y = this.getVertices()[5];
			break;
		case 3:
			edge[0].x = this.getVertices()[4];
			edge[0].y = this.getVertices()[5];
			edge[1].x = this.getVertices()[6];
			edge[1].y = this.getVertices()[7];
			break;
		case 4:
			edge[0].x = this.getVertices()[6];
			edge[0].y = this.getVertices()[7];
			edge[1].x = this.getVertices()[0];
			edge[1].y = this.getVertices()[1];
			break;
		
		}
		
		return edge;
		
	}
	
	
	public Vector2 getEdgeNormal(int edgeId) {//returns normal vector
		Vector2[] edge = getEdge(edgeId); 		
		Vector2 AB = new Vector2(edge[1].x-edge[0].x, edge[1].y-edge[0].y);	
		Vector2 normal = new Vector2(AB.y, -AB.x); //Turn 90 degrees clockwise
		normal.nor(); 		
		return normal;
	}



	public float getDistanceToVertex(int vertex, int x, int y) {
		
		Vector2 vertexXY = new Vector2();
		
		vertexXY = getVertexXY(vertex);
		
		float dist;
		
		Vector2 vec1 = new Vector2(vertexXY.x,vertexXY.y);
		dist = vec1.dst(x, y);	
		return dist;
	}


	
	
	
	
	
	//for test
	
	public boolean isPointInside(Vector2 xy) {
		boolean proj = false;
		
		
		Vector2 ab = lineToVector(getEdge(getClosestEdgeId(xy)));
		Vector2 ap = lineToVector(getEdge(getClosestEdgeId(xy))[0], xy);

		/*Проверка пренадлежности делается с помощью вычисления векторного произведения 
		 *между вектором образованым ближайшей гранью (ab) и вектором между курсором началом ближайшей грани (ap)
		 *если произведение меньше 0, то точка пренадлежит полигону.
		 *Для полигонов с острыми углами еще нужна проверка на длину. ap не должно быть > ab.  
		 *
		 *
		 *Checking by using cross product between vector creating by nearest edge (ab) and 
		 *vector specified by start point of ab and xy coordinates. Negative value of cross means pont
		 *is inside poly. Length check is for non-square polys.
		 */
		
		
		
		if(ap.crs(ab) < 0 && ab.len() >= ap.len()) { //Тут проверяем, что 
			proj = true;
		} else {
			proj = false;	
		}
		

		Render.drawPoint(new Vector2(getEdge(getClosestEdgeId(xy))[0].x + ab.x, getEdge(getClosestEdgeId(xy))[0].y + ab.y), 7, Render.Colour.RED);
		Render.drawPoint(new Vector2(getEdge(getClosestEdgeId(xy))[0].x + ap.x, getEdge(getClosestEdgeId(xy))[0].y + ap.y), 7, Render.Colour.GREEN);
		Render.drawLine((int)getEdge(getClosestEdgeId(xy))[0].x, (int)getEdge(getClosestEdgeId(xy))[0].y, (int)ab.x + (int)getEdge(getClosestEdgeId(xy))[0].x, (int)ab.y + (int)getEdge(getClosestEdgeId(xy))[0].y, Render.Colour.RED);
		Render.drawLine((int)getEdge(getClosestEdgeId(xy))[0].x, (int)getEdge(getClosestEdgeId(xy))[0].y, (int)ap.x + (int)getEdge(getClosestEdgeId(xy))[0].x, (int)ap.y + (int)getEdge(getClosestEdgeId(xy))[0].y, Render.Colour.GREEN);

		

		return proj;
	}
	
	
	
	
	public Vector2 lineToVector(Vector2[] line) {
		Vector2 res = new Vector2(line[1].x-line[0].x, line[1].y - line[0].y);
		
		return res;
	}
	
	public Vector2 lineToVector(Vector2 xy0, Vector2 xy1) {
		Vector2 res = new Vector2(xy1.x-xy0.x, xy1.y - xy0.y);
		
		return res;
	}
	
	
	public float getDistance() {
		return distance;
	}



	public void setDistance(float distance) {
		this.distance = distance;
	}



	public Vector2 getClosestEdgePoint(Vector2 xy) {
		
		
		int edge = getClosestEdgeId(xy);
		return getClosestPointOnLine(getEdge(edge)[0], getEdge(edge)[1] , xy);
	
	}
	
	
	public int getClosestEdgeId(Vector2 xy) {
		
		/*Процедура возвращает номер ближайшей ВНЕШНЕЙ грани.
		 * В данном случае у нас 2 варианта: 
		 * 1. Либо курсор находится рядом с гранью, и от курсора можно опустить перпендикуляр на ближайшую грань.
		 * 2. Либо курсор находится на углу.
		 * 
		 * Проверку выполняем 
		 */
		
		
		
		HashMap<Integer,Float> distances = new HashMap<Integer,Float>();
				
		for(int i = 1; i <=4; i++) {//заполняем расстояния		
		float theDistance = xy.dst(getProjectionPointOnLine(getEdge(i)[0], getEdge(i)[1], xy));
		distances.put(i, theDistance);//Расстояние до прекции на линию
		}

		Array<Integer> duplicateLength = new Array<Integer>();	//Находим одинаковые расстояния в карте расстояний. Если таковые есть, значит курсор у угла.
		for(Entry<Integer, Float> entry : distances.entrySet()) {
			for(Entry<Integer, Float> entry2 : distances.entrySet()) {//Используем 2 вложенных цикла, чтобы сравнить каждую величину
				if(entry.getValue().equals(entry2.getValue()) && !entry.getKey().equals(entry2.getKey())) {//дополнительно вводим проверку на то, что не делаем сравнение элемента с самим собой
					duplicateLength.add(entry.getKey());
				}
			}
		}

		
		boolean isInCorner = false;			//Выясняем какой у нас случай

		if(duplicateLength.size == 2) {
			if(getCornerByEdges(duplicateLength.get(0), duplicateLength.get(1)) != 0) 
			isInCorner = true;
	}
		
		int edge = 1;
		if(isInCorner == false) {//1 случай (рядом с гранью)

			float minDist = distances.get(1);
			
			for (int i = 1; i <= 4; i++) {
				if(distances.get(i) < minDist) {
					edge = i;
					minDist = distances.get(i);
				}
				Vector2 xyN = ru.laz.game.model.math.MathGame.lineToVector(getEdge(i)[0], xy);
	 			xyN.nor();
			}
			
		} else {//2 случай - на углу
			
			HashMap<Integer, Float> dot = new HashMap<Integer, Float>();//Массив скалярных перемножений вектора курсора и нормалей

			
			//Определяем угол по записям в массиве дублей расстояний duplicateLength
			//На самом деле просто упорядочиваем набор граней
			
			int[] edges = getEdgesByCorner(getCornerByEdges(duplicateLength.get(0), duplicateLength.get(1)));
			
			int edge1 = edges[0];
			int edge2 = edges[1];
			
			Vector2 xyN = ru.laz.game.model.math.MathGame.lineToVector(getEdge(edge2)[0], xy);
 			xyN.nor();
			dot.put(edge1, getEdgeNormal(edge1).dot(xyN));
			dot.put(edge2, getEdgeNormal(edge2).dot(xyN));
		
			if(dot.get(edge1) > dot.get(edge2)) {
				edge = edge1;
			} else {
				edge = edge2;
			}
		}
		return edge;
	}



	public Vector2 getClosestPointOnLine(Vector2 a, Vector2 b, Vector2 p) {
		
		/*Для нахождения ближайшей точки P на прямой AB надо найти сначала векторную проекцию
		 * вектора AP на вектор AB. Формула расчета: ((AP, AB)/|AB|)/(AB/|AB|),
		 * где (AP, AB) - скалярное произведение, |AB| - длина вектора, AB/|AB| - нормализованный
		 * вектор на который делается проекция. При этом (AP, AB)/|AB| - скалярная проекция, 
		 * а AB/|AB| - нормализованный вектор AB.
		 * 
		 * Затем проекцию умножаем на единичный вектор, образованный от AB.
		 * 
		 * 
		 * To find closest point use projection. Full formula ((AP, AB)/|AB|)/(AB/|AB|). Where
		 * (AP, AB) dot product, (AP, AB)/|AB| - scalar projection, AB/|AB| - normalized AB.
		 * First find scalar proj then multiply it with normilized vector. 
		 */
	
		Vector2 fin;
		Vector2 AP = new Vector2(p.x - a.x, p.y - a.y);//Определяем векторы и переносим их в начало координат
		Vector2 AB = new Vector2(b.x - a.x, b.y - a.y);//AB - вектор на который проецируем точку. AP - вектор откуда проецируем
		Vector2 proj; //Векторная прекция
		
	
		float scalarProj = AP.dot(AB)/AB.len();	//Скалаярная проекция	
		Vector2 norm = new Vector2(AB);
		norm.nor();		
								//Нормализуем AB
		
		proj = new Vector2(scalarProj*norm.x, scalarProj*norm.y); //Векторная проекция. Vector projection.
		
		if (proj.len() >= AB.len() && proj.dot(AB) > 0) {//Ограничение по максимуму длины
			proj.set(AB);									//Проверяем, что угол между векторами острый (скалярное произв > 0). Это важное условие, иначе точка скачет при увеличении дистанции
		}
		
		
		
		if (proj.dot(AB) <= 0) {
			proj.x= 0;
			proj.y= 0;
		}

		fin = new Vector2(proj.x + a.x, proj.y + a.y);	
		//Итоговый вектор стоится путем умножения числа скалярной проекции на нормализованный
		//вектор AB. В завершение переносим векторы в первоначальное положение.
		return fin;
		
	}



	public int getCornerByEdges(int edge1, int edge2) {
		int ret = 0;
		
		if ((edge1 == 1 && edge2 == 2) || (edge1 == 2 && edge2 == 1)) ret = 2; 
		else if ((edge1 == 2 && edge2 == 3) || (edge1 == 3 && edge2 == 2)) ret = 3;
		else if ((edge1 == 3 && edge2 == 4) || (edge1 == 4 && edge2 == 3)) ret = 4;
		else if ((edge1 == 4 && edge2 == 1) || (edge1 == 1 && edge2 == 4)) ret = 1;
		return ret;
	}



	public int[] getEdgesByCorner(int corner) {
		int[] ret = new int[2];
		
		switch(corner) {
		case 1: ret[0] = 4;
				ret[1] = 1;
				break;
				
		case 2: ret[0] = 1;
				ret[1] = 2;
				break;
				
		case 3: ret[0] = 2;
				ret[1] = 3;
				break;
				
		case 4: ret[0] = 3;
				ret[1] = 4;
				break;
		
		}
		return ret;
	}



	public Vector2 getVectorProjection(Vector2 a, Vector2 b, Vector2 p) {
		
		Vector2 AP = new Vector2(p.x - a.x, p.y - a.y);//Определяем векторы и переносим их в начало координат
		Vector2 AB = new Vector2(b.x - a.x, b.y - a.y);//AB - вектор на который проецируем точку. AP - вектор откуда проецируем
		float scalarProj = AP.dot(AB)/AB.len();	//Скалаярная проекция
		
		Vector2 norm = new Vector2(AB);
		norm.nor();	//Нормализуем AB
		
		Vector2 vec = new Vector2(scalarProj*norm.x, scalarProj*norm.y); //Векторная проекция. Vector projection.
		return vec; 
	
	}



	public float getDistanceToLine(Vector2 a, Vector2 b, Vector2 p) {
		float dist = 0;
		
		
		
		return dist;
	}



	public Vector2 getProjectionPointOnLine(Vector2 a, Vector2 b, Vector2 p) {
		
		/*Для нахождения ближайшей точки P на прямой AB надо найти ее векторную проекцию
		 * вектора AP на вектор AB. Формула расчета: ((AP, AB)/|AB|)/(AB/|AB|),
		 * где (AP, AB) - скалярное произведение, |AB| - длина вектора, AB/|AB| - нормализованный
		 * вектор на который делается проекция. При этом (AP, AB)/|AB| - скалярная проекция, 
		 * а AB/|AB| - нормализованный вектор AB.
		 * 
		 * Затем проекцию умножаем на единичный вектор, образованный от AB.
		 * 
		 * 
		 * To find closest point use projection. Full formula ((AP, AB)/|AB|)/(AB/|AB|). Where
		 * (AP, AB) dot product, (AP, AB)/|AB| - scalar projection, AB/|AB| - normalized AB.
		 * First find scalar proj then multiply it with normilized vector. 
		 */
		
		
		Vector2 fin;
		Vector2 AP = new Vector2(p.x - a.x, p.y - a.y);//Определяем векторы и переносим их в начало координат
		Vector2 AB = new Vector2(b.x - a.x, b.y - a.y);//AB - вектор на который проецируем точку. AP - вектор откуда проецируем
		Vector2 proj = getVectorProjection(a, b, p);
		
	
		if (proj.len() >= AB.len() && proj.dot(AB) > 0) {//Ограничение по максимуму длины
			proj.set(AB);			//Проверяем, что угол между векторами острый (скалярное произв > 0). Это важное условие, иначе точка скачет от одного края к другому при увеличении дистанции						
		}
	
		if (proj.dot(AB) <= 0) {
			proj.x= 0;
			proj.y= 0;
		}
		fin = new Vector2(proj.x + a.x, proj.y + a.y);

		//Итоговый вектор стоится путем умножения числа скалярной проекции на нормализованный
		//вектор AB. В завершение переносим векторы в первоначальное положение.
		return fin;
		
	}



	public boolean hasPointOnLine(Vector2 a, Vector2 b, Vector2 p) {
		
		/*Процедура определяет можно ли провести от точки до отрезка перпендикуляр.*/
		
		
		boolean fin;
		Vector2 AP = new Vector2(p.x - a.x, p.y - a.y);//Определяем векторы и переносим их в начало координат
		Vector2 AB = new Vector2(b.x - a.x, b.y - a.y);//AB - вектор на который проецируем точку. AP - вектор откуда проецируем
		Vector2 proj = getVectorProjection(a, b, p);
		
	
		if ((proj.len() >= AB.len() && proj.dot(AB) > 0) || (proj.dot(AB) <= 0)) {//Ограничение по максимуму длины
			fin = false;			//Проверяем, что угол между векторами острый (скалярное произв > 0). Это важное условие, иначе точка скачет от одного края к другому при увеличении дистанции						
		} else 
		{
		fin = true;
		}
		return fin;
		
	}

	

}
