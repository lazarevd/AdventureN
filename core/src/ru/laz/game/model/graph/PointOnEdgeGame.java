package ru.laz.game.model.graph;

import com.badlogic.gdx.math.Vector2;

import java.util.Map.Entry;

import ru.laz.game.AGame;

public class PointOnEdgeGame {
	
	private Vector2 position;
	private int parentEdge;
	private float edgePosition;
	private String parentPoly;
	private String childNode;



	GraphGame graph;


    public PointOnEdgeGame() {
		this.position = new Vector2();
	}
	
	public PointOnEdgeGame(String poly, int parentEdge, float k, GraphGame graph) {
		this.parentEdge = parentEdge;
		this.edgePosition = k;
		this.parentPoly = poly;
		this.graph = graph;
	}

	public void setGraph(GraphGame graph) {
		this.graph = graph;
	}


	public void setChildNode (String name) {
		this.childNode = name;
	}
	
	
	public String getChildNode () {
		return this.childNode;
	}
	
	
	public String getThisId() {
		String ret = null;
		
		for (Entry<String, PointOnEdgeGame> entry : graph.getPOEs().entrySet()) {
			if (entry.getValue().equals(this)) {
				ret = entry.getKey();
				break;
			}
		}
		
		return ret;
	}
	
	
	public int getParentEdge() {
		return this.parentEdge;
	}
	
	public String getParentPolygon() {
		return this.parentPoly;
	}
	
	
	public float getEdgePosition() {
		return this.edgePosition;
	}
	
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public void setPosition(Vector2 pos) {
	this.position = pos;
	}
	
	public void setPoint (int edge, float length) {
		
	Vector2[] edVec = new Vector2[2];
	Vector2 tmp;
		
	edVec = AGame.getGame().getGameScreen().getLevel().getGraph().getPolygons().get(this.parentPoly).getEdge(edge);
	tmp = new Vector2(edVec[1].x - edVec[0].x, edVec[1].y - edVec[0].y);
	
	tmp = tmp.scl(length);
	tmp.x = tmp.x + edVec[0].x;
	tmp.y = tmp.y + edVec[0].y;
	
	setPosition(tmp);
		
	}
	
	public void setPointPosition() {
		
	int edge = this.parentEdge;
	float length = this.edgePosition;
		
	Vector2[] edVec = new Vector2[2];
	Vector2 tmp;

	
	edVec = graph.getPolygons().get(this.parentPoly).getEdge(edge);
	tmp = new Vector2(edVec[1].x - edVec[0].x, edVec[1].y - edVec[0].y);
	
	tmp = tmp.scl(length);
	tmp.x = tmp.x + edVec[0].x;
	tmp.y = tmp.y + edVec[0].y;
	
	setPosition(tmp);
		
	}
}
