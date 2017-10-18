package ru.laz.game.model.graph;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map.Entry;

import static ru.laz.game.model.math.MathGame.getMiddleOfLine;

public class NodeGame {
	
	private float x, y;
	private transient String parentNode;
	private transient Array<String> neighbours;
	private transient int G, H; //G - from start cost, H - to finish heuristic cost.
	private Array<String> poeList;
	protected NodeType nodeType;



	transient GraphGame graph;
	private transient float distance;//�� �� ��� ��� ��������� ������� �����




	private float renderScale;
	
	enum NodeType {STANDART, POE};
	
	private  NodeGame() {
		this.poeList = new Array<String>();
		this.neighbours = new Array<String>();
	}


	public NodeGame(float x2, float y2, float rendScale, GraphGame graph) {
		this();
		this.x = x2;
		this.y = y2;
		this.parentNode = null;
		this.renderScale = rendScale;
		this.G = 0;
		this.H = 0;
		this.graph = graph;
		this.nodeType = NodeType.STANDART;
	}


	public void setGraph(GraphGame graph) {
		this.graph = graph;
	}

	
	public NodeGame(Array<String> poeList, GraphGame graph) {
		this(100,100, 1.0f, graph);
		this.poeList = poeList;
		nodeType = NodeType.POE;
		//setPOEPosition();
		for (String poe : poeList) {		
			
				try{
					graph.getPOEs().get(poe).setChildNode(this.getThisId());
				} catch (NullPointerException nex) {
					Gdx.app.log("Can`t set child POE node", nex.toString());
				}	
		}
	}
	

	public void addNeighbour(String name) {
		//Gdx.app.log("Fill", "start");
		
		if (neighbours.size > 0){
		for (String nod : neighbours) {
			if (nod.equals(name)) {
				return;
					}
				}
		}
		neighbours.add(name);
		
	}
	
	
	
	public String getThisId() {
		String ret = null;
		
		for (Entry<String, NodeGame> entry : ((HashMap<String, NodeGame>)graph.getNodes()).entrySet()) {
			if (entry.getValue().equals(this)) {
				ret = entry.getKey();
				break;
			}
		}
		
		return ret;
	}
	
public void clearLinked() {
		
		deleteFromNeighbours();
		//Gdx.app.log("start deleting", "");
		for (String edg : getNeighbourEdges()) {
			//Gdx.app.log("deleting", edg);
			graph.getEdges().remove(edg);
		}
		
		
}
	
public void deleteFromNeighbours () {
	for (NodeGame nod : graph.getNodes().values()) {
		nod.getNeighbours().removeValue(getThisId(), true);
	}
}
	
	
	
public Array<String> getNeighbourEdges() {
	
	Array<String> retEdge = new Array<String>();
	
	
	for(Entry<String, EdgeGame> entry : graph.getEdges().entrySet()) {
		if (getThisId().equals(entry.getValue().getNodes().get(0)) || getThisId().equals(entry.getValue().getNodes().get(1))) {
			
			if (!retEdge.contains(entry.getKey(), true)) retEdge.add(entry.getKey());
		}
	}
	
return retEdge;

}
	
	
	public String printNeighbours() {
		
		String neighbours = "";
		
		
		for (String node : getNeighbours()) {
			neighbours = neighbours + " " + node.toString();
		}
		
		return "[" + neighbours + "]";
	}




	public void setRenderScale(float renderScale) {
		this.renderScale = renderScale;
	}

	public float getRenderScale() {
		return renderScale;
	}
	
	/*
	public float getCost(Node node) { //Get Heuristic cost. We need this when we chose next node to move.
		
		
		
		
		
		class CustomComparator implements Comparator<Node> {
		    @Override
		    public int compare(Node o1, Node o2) {
		        return o1.getCost(node).compareTo(o2.getCost(node));
		    }

		}
		
		Vector2 vec;
		
		for (Node nod : getNeighbours())
			
			
	}
	

	*/
	public Array<String> getNeighbours() {
	return this.neighbours;	
	}	
	
	

	
	
	public void setParent(String parent) {
		this.parentNode = parent;
	}

	public String getParent() {
		return this.parentNode;
	}	
	
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;

	}
	
	

	
	public int getCost() {
		return this.G + this.H;
	}
	
	
	public void setG(int g) {
		this.G = g;
	}
	
	public void setH(int h) {
		this.H = h;
	}
	
	public int getG() {
		return this.G;
	}
	
	public int getH() {
		return this.H;
	}
	
	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getDistanceToNode(NodeGame node) {
		float dist;
		
		Vector2 vec1 = new Vector2(this.x,this.y);
		dist = vec1.dst(node.getX(), node.getY());	
		return dist;
	}
	
	public float getDistanceToPoint(float x, float y) {
		float dist;
		
		Vector2 vec1 = new Vector2(this.x,this.y);
		dist = vec1.dst(x, y);	
		//Gdx.app.log("get dist", dist+"");
		return dist;
	}	
	

	public float getAngle(NodeGame node) {
		float deltaX, deltaY;
		float angle;
		
		deltaX = node.getX() - this.x;
		deltaY = node.getY() - this.y;

		angle = (float)Math.atan2(deltaY, deltaX) * MathUtils.radiansToDegrees;
		if (angle < 0) angle += 360;
		
		return angle;
	}
	
	
	public int countHCost() {
		
		//float angle; 
		float distance;
		int H;
		
		distance = Math.abs(getDistanceToNode(graph.getFinish()));
		
		H = (int) distance;
		
		
		return H;
	}
	
	
	public void updateStatus() {
		
		if (this.nodeType == NodeType.POE)
			setPOEPosition();
	}

	//Methods for POE type
	public Array<String> getListOfPOE () {
		return this.poeList;
	}
	
	
	public void setPOEPosition() {

		Gdx.app.log("POE ", poeList.get(0));

		PointOnEdgeGame poe1 = graph.getPOEById(poeList.get(0));
		PointOnEdgeGame poe2 = graph.getPOEById(poeList.get(1));
				
		try {
		Vector2 pos = getMiddleOfLine(poe1.getPosition().x, poe1.getPosition().y, poe2.getPosition().x, poe2.getPosition().y );
		this.setX((int)pos.x);
		this.setY((int)pos.y);		
		}
		catch (NullPointerException nex) {
			Gdx.app.log("POE", poeList.get(0) + " : " + poe1 + "|"+ poeList.get(1) + " : " + poe2);
			Gdx.app.log("setPOEPosition() - no poe! ", nex.toString());
		}
	}
	

public Vector2 getXY() {
	return new Vector2(x,y);
}



}
