package ru.laz.game.model.graph;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import  ru.laz.game.model.graph.dataexchange.GraphSource;

public class GraphGame {
		
	
	public HashMap<String, NodeGame> nodes;
	private HashMap<String, EdgeGame> edges;
	private HashMap<String, Polygon4Game> polygons;
	private HashMap<String, PointOnEdgeGame> poes;
	
	public GraphGame() {
		nodes = new HashMap<String, NodeGame>();
		edges = new HashMap<String, EdgeGame>();
		polygons = new HashMap<String, Polygon4Game>();
		poes = new HashMap<String, PointOnEdgeGame>();
	}
	
	
	public void updateStatus() {
		for(NodeGame nod : nodes.values()) {
			nod.updateStatus();
		}

	}


	
	
	public void loadGraph() {
		
		
		FileHandle file = Gdx.files.internal("source.graph");
    	String scores = file.readString();
    	Json json = new Json();
    	GraphSource graphSource = json.fromJson(GraphSource.class, scores);
    	//Gdx.app.log("json load", scores);
		
		edges.clear();
		nodes.clear();
		polygons.clear();
		poes.clear();
		
		for (Entry<String, NodeGame> ns : graphSource.nodes.entrySet()) {
			if (ns.getValue().nodeType == NodeGame.NodeType.STANDART)//Standart nodes
			Gdx.app.log("addnode S ", ns.getKey());
			addNode(ns.getKey(), ns.getValue());
		}
		
		for (Entry<String, EdgeGame> ed : graphSource.edges.entrySet()) {
			Gdx.app.log("addedge ", ed.getKey());
			addEdge(ed.getKey(), ed.getValue());
		}
			
		for (Entry<String, Polygon4Game> ps : graphSource.polys.entrySet()) {
			Gdx.app.log("addpoly ", ps.getKey());
			addPolygon(ps.getKey(), ps.getValue());
		}
		
		for(Entry<String, PointOnEdgeGame> poe : graphSource.poes.entrySet()) {
			Gdx.app.log("addPOE ", poe.getKey());
			addPointOnEdge(poe.getValue().getParentPolygon(), poe.getValue().getParentEdge(), poe.getValue().getEdgePosition(), poe.getKey());
		}
				
		for (Entry<String,NodeGame> ns : graphSource.nodes.entrySet()) {
			if (ns.getValue().nodeType == NodeGame.NodeType.POE)//POE nodes
				Gdx.app.log("addnode P ", ns.getKey());
				addNode(ns.getKey(), ns.getValue());
			}	
		
    	for(PointOnEdgeGame poe : poes.values()) {
    		poe.setPointPosition();
    	}
		
		updateStatus();
	}
	
	
	public void clearGraph() {
		edges.clear();
		nodes.clear();
		polygons.clear();
		poes.clear();
	}
	
	
	public void clearNodes() {
		edges.clear();
		nodes.clear();
		poes.clear();
	}
	
	
	
	public void clearPolygonsChildNodes() {
		for(Polygon4Game poly : polygons.values()) {//??? ??????? ????????	
			poly.getChildNodes().clear();
		}
	}
	

	public String getHitPolygon(Vector2 xy) {
		String res = "-1";
		
		for(Entry<String,Polygon4Game> entry : polygons.entrySet()) {
			if(entry.getValue().isPointInside(xy)) 
				res = entry.getKey();
		}
	
	return res;
	}
	
	
	public void deleteNode(String name) {
		
		if(nodes.get(name) != null)
		{
		nodes.get(name).clearLinked();	
		nodes.remove(name);
		}

	}
	
	
	
	
	
	
	public void addNode(String name, NodeGame node) {
		node.setGraph(this);
		this.nodes.put(name, node);
	
	}


	public void addEdge(String name, EdgeGame edge) {		
		if (edge != null) {
		edge.setGraph(this);
 		this.edges.put(name, edge);	
		}
	
	}
	
	public void addPolygon(String name, Polygon4Game polygon) {
		if (polygon != null) {
			polygon.setGraph(this);
			this.polygons.put(name, polygon);
		}
	}
	
	public String getNewPOEName() {
		String newName = "poe";
		
		newName += (getLastPOENameNumber() + 1);
		
	
		
		return newName;
	}
	
	
	public int getLastPOENameNumber() {
		
		int curInt = 0;
		String regExp = "[0-9]+$";
		Pattern p = Pattern.compile(regExp);
		Matcher m;
		
			
			for (String poe : poes.keySet()) {
				m = p.matcher((String)poe);
				
				if (m.find()) {
				
				if(Integer.parseInt(m.group()) > curInt) {
					curInt = Integer.parseInt(m.group());
				}
				}
				
				
			}
			
			
		return curInt;
	}
	

	
	
	public void addPointOnEdge(String polygon, int edge, float k, String name) {
		PointOnEdgeGame poe = new PointOnEdgeGame(polygon, edge, k, this);
		poes.put(name, poe);
		polygons.get(polygon).pointsOnEdges.add(name);
	}

	
	
	public String getNewEdgeName() {
		String newName = null;
		
		newName = "edge" + (getLastEdgeNumber() + 1);
		
		
		return newName;
	}
	
	
	private int getLastEdgeNumber() {
		
		int curInt = 0;
		String regExp = "[0-9]+$";
		Pattern p = Pattern.compile(regExp);
		Matcher m;
		
			
			for (String nod : edges.keySet()) {
				m = p.matcher(nod);
				
				if (m.find()) {
				
				if(Integer.parseInt(m.group()) > curInt) {
					curInt = Integer.parseInt(m.group());
				}
				}
				
				
			}
	return curInt;
	}
	
	public String getNewNodeName() {
		String newName = null;
		
		newName = "node" + (getLastNodeNameNumber() + 1);
		
		
		return newName;
	}
	
	
	private int getLastNodeNameNumber() {
		
		int curInt = 0;
		String regExp = "[0-9]+$";
		Pattern p = Pattern.compile(regExp);
		Matcher m;
		
			
			for (String nod : nodes.keySet()) {
				m = p.matcher(nod);
				
				if (m.find()) {
				
				if(Integer.parseInt(m.group()) > curInt) {
					curInt = Integer.parseInt(m.group());
				}
				}
				
				
			}
			
			
		return curInt;
	}

	
	
	
	
	
	
	public HashMap<String, NodeGame> getNodes() {
		return this.nodes;
	}
	
	
	public String getNodeKey(NodeGame node) {
		
		for(Entry<String, NodeGame> entry : nodes.entrySet()) {
			if (node.equals(entry.getValue())) {
				return entry.getKey();
			}
		}	
		return null;
	}




	public Array<String> getNodesInRadius(Vector2 xy, float radius) {
		Array<String> retNodes = new Array<String>();
		for (Entry<String, NodeGame> entry : nodes.entrySet()) {
			if(entry.getValue().getDistanceToPoint(xy.x, xy.y) <= radius) {
				retNodes.add(entry.getKey());
			}
		}
		return retNodes;
	}


	public String getNearestNode(Vector2 xy) {
		
		float curDist = Float.MAX_VALUE;
		String curNode = "";
		for (Entry<String, NodeGame> entry : nodes.entrySet()) {
			
			if(curDist > entry.getValue().getDistanceToPoint(xy.x, xy.y) && entry.getKey() != "start") {//???? ?? ?????? ???? ???????
				curDist = entry.getValue().getDistanceToPoint(xy.x, xy.y);
				curNode = entry.getKey();
			}			
		}	
		//Gdx.app.log("getNearestNode", "dist: " + curDist + "node: " + curNode);
		return curNode;
	}
	
	public HashMap<String, Polygon4Game> getPolygons() {
		
		return this.polygons;
	}
	
	
	public String getEdgeKey(EdgeGame edge) {
		
		for (Entry<String, EdgeGame> entry : edges.entrySet()) {
			
			if (entry.equals(edge)) {
				return entry.getKey();
			}
		}
		
		
		return null;
	}
	
	
	public HashMap<String, EdgeGame> getEdges() {
		return this.edges;
	}
	
	public HashMap<String, PointOnEdgeGame> getPOEs() {
		return this.poes;
	}
	
	
	public PointOnEdgeGame getPOEById(String name) {	
		return this.poes.get(name);
	}

	
	
public void refreshNeighbours() {
	
		fillNeighbours();
	
}




public void fillNeighbours() {
	
	for (Entry<String, NodeGame> entry : nodes.entrySet()) {
	
		for (EdgeGame edg : edges.values()) {
			if (entry.getKey().equals(edg.getNodes().get(0))) {//if first node exist in edge, then second is neigbout
				entry.getValue().addNeighbour(edg.getNodes().get(1));
			} else if (entry.getKey().equals(edg.getNodes().get(1))) {//  and vice versa 
				entry.getValue().addNeighbour(edg.getNodes().get(0));		
		}
	}
	
}

	}
public NodeGame getStart() {
		
		return nodes.get("start");
	}
	
	
	public String getStartId() {

		return "start";
	}
	
	
	public NodeGame getFinish() {
		return nodes.get("finish");
		}
	
	public String getFinishId() {
		return "finish";
	}
	
	public NodeGame getByName(String name) {
		
		
		return nodes.get(name);
	}

	
	
public Array<String> getPolyChildNodes(String poly) {
	
	Array<String> retNodes = new Array<String>();//nodes connected to start and finish in poly

	
	for (String poe : polygons.get(poly).getPointsOnEdge()) {//find connected nodes for "start"
		
		for (Entry<String,NodeGame> nod : nodes.entrySet()) {
			if (nod.getValue().getListOfPOE().size == 2) {
			String nodPoe1 = nod.getValue().getListOfPOE().get(0);
			String nodPoe2 = nod.getValue().getListOfPOE().get(1);
			
			if(retNodes.contains(nod.getKey(), true) == false && (nodPoe1.equals(poe) || nodPoe2.equals(poe)))
				retNodes.add(nod.getKey());//add node to list
			}
		}
	}
	return retNodes;
}
	
	

public void addStartFinishNodes(Vector2 sxy, Vector2 fxy, float scale) {
		
		String polf = null;
		String pols = null;
		
		deleteNode("start");
		deleteNode("finish");

		
		for(Entry<String, Polygon4Game> poly : polygons.entrySet()) { //ќпределе¤ем стартовый и финишный полигоны, если они есть
	
			if (poly.getValue().isPointInside(fxy)) {//if cursor clicked on a poly, get this poly
				polf = poly.getKey();
				Gdx.app.log("IN POLY ", polf);
			}
			if (poly.getValue().isPointInside(sxy)) {//get polygon with start position
					pols = poly.getKey();
			}
			
	
		}
			
		
			if(pols != null && polf != null) {//—лучай, если и старт и финиш внутри полигона
								
				if (pols.equals(polf)) 
				{
				addNode("start", new NodeGame(sxy.x,sxy.y, polygons.get(pols).getRenderScale(), this));
				addNode("finish", new NodeGame(fxy.x,fxy.y, polygons.get(polf).getRenderScale(), this));
					//TODO добавить scale д¤л полигона
				addEdge(getNewEdgeName(), new EdgeGame("start", "finish", this));
				//Gdx.app.log("all notnull & equal"," polys: " + pols + "; polyf: " + polf);
				} 
				else 
				{//≈сли старт и финиш еще и в одном и том же полигоне
					//Gdx.app.log("all notnull & differs", " polys: " + pols + "; polyf: " + polf);
					Array<String> snodes = getPolyChildNodes(pols);//nodes connected to start and finish in poly
					Array<String> fnodes = getPolyChildNodes(polf);
					
					addNode("start", new NodeGame(sxy.x,sxy.y,polygons.get(pols).getRenderScale(), this));// add nodes
					addNode("finish", new NodeGame(fxy.x,fxy.y, polygons.get(polf).getRenderScale(), this));

					for (String nod : snodes) {
						addEdge(getNewEdgeName(), new EdgeGame("start", nod, this));
					}
					
					for (String nod : fnodes) {
						addEdge(getNewEdgeName(), new EdgeGame("finish", nod, this));
					}
				}		
			}
			
			else if (polf != null) {// огда стартуем не с полигона (например остановились между ними и пошли в другую сторону)

				Array<String> fnodes = getPolyChildNodes(polf);
				for (String nod : fnodes) {//draw edges for finish						
					addEdge(getNewEdgeName(), new EdgeGame("finish", nod, this));
				}
				addNode("start", new NodeGame(sxy.x,sxy.y, scale, this));
				addNode("finish", new NodeGame(fxy.x,fxy.y, 1.0f, this));
				addEdge(getNewEdgeName(), new EdgeGame("start", getNearestNode(sxy), this));

			}  else {//» если старт и финиш не с полигона, только по нодам ориентируемс¤. ѕока это рабочее решение, ноды проще контролировать
				//—оедин¤ем старт и финиш с ближайшими нодами

				addNode("start", new NodeGame(sxy.x,sxy.y, scale, this));

				NodeGame nearestNode = nodes.get(getNearestNode(fxy));

				Vector2 nearestXY = nearestNode.getXY();//финиш у ближайшей ноды
				addNode("finish", new NodeGame(nearestXY.x,nearestXY.y, nearestNode.getRenderScale(), this));

				for (String nodeName : getNodesInRadius(sxy, 100)) {
					Gdx.app.log("s", nodeName);
					if (!nodeName.equals("start")) {
						addEdge(getNewEdgeName(), new EdgeGame("start", nodeName, this));
					}
				}

				for (String nodeName : getNodesInRadius(fxy, 100)) {
					Gdx.app.log("f", nodeName);
					if (!nodeName.equals("finish")) {
						addEdge(getNewEdgeName(), new EdgeGame("finish", nodeName, this));
					}
				}
			}

			
	
		refreshNeighbours();//REFRESH
		
	}


public ArrayList<String> AStarSearch() {

	fillNeighbours();
	ArrayList<String> openList = new ArrayList<String>();
	ArrayList<String> closedList = new ArrayList<String>();
	ArrayList<String> finalPath = new ArrayList<String>();
	String curNode;
	
	if (nodes.get("start") != null && nodes.get("finish") != null) {	
		for (NodeGame nd : nodes.values()) {		//Assign default parents (nodes itself for begining);
			nd.setParent(getNodeKey(nd));
		}

		openList.add(getStartId()); //This will be start
		while(openList.size() > 0 && !closedList.contains(getFinishId())) {
			
			curNode = openList.get(openList.size()-1); // Get last element (FIFO queue)
			for (String openNode : openList) {//Find node with lowest price

				if (nodes.get(curNode).getCost() > nodes.get(openNode).getCost()) {
					curNode = openNode;
				}
			}
			
			closedList.add(curNode);
			openList.remove(curNode);		
			for (String nam : nodes.get(curNode).getNeighbours()) {

				if (!closedList.contains(nam)) {
				nodes.get(nam).setParent(curNode);
				nodes.get(nam).setG(nodes.get(nodes.get(curNode).getParent()).getG() + 10); //G - from start cost, //Big construction. HashMaps..
				nodes.get(nam).setH(nodes.get(nam).countHCost()); //H - to finish heuristic cost. 
				if (!openList.contains(nam)){		
				openList.add(nam);
				}
				}
						
			}
			
		}

		String curentNod = getFinishId();
		String parNod;
		
		while (curentNod != getStartId()) { //add cycle count to debug
			
			finalPath.add(curentNod); 
			parNod = nodes.get(curentNod).getParent();
			
			if(parNod.equals(curentNod)) {
				break;
			} else {
				curentNod = parNod;
			}

		}
		
		Collections.reverse(finalPath);
		//Gdx.app.log("Final nodes (reverse sorted)", printNodes(finalPath));	
	}
		return finalPath;	
}



public ArrayList<String> filterMovePath(ArrayList<String> astarPath) {
	
	ArrayList<String> workList = new ArrayList<String>();
	
	
	
	for(String nod_i : astarPath) {
		
		if(!nod_i.equals("start") && !nod_i.equals("finish")) {	
			//Gdx.app.log("nod_i", nod_i);
		NodeGame nodeI = nodes.get(nod_i);
		
		for(String nod_j : astarPath) {
			if(!nod_i.equals("start") && !nod_i.equals("finish") && !nod_i.equals(nod_j)) {
			NodeGame nodeJ = nodes.get(nod_j);
			//Gdx.app.log("distance measure", nodeI.getDistance(nodeJ) + "");
			if((nodeI.getDistanceToNode(nodeJ) < 10.0f)) {
				workList.add(nod_j);
					}
				}	
			}
		}			
	}
	astarPath.removeAll(workList);
return astarPath;
}








public String printNodes(ArrayList<String> nodeList) {
	
	String neighbours = "";
	
	
	for (String node : nodeList) {
		neighbours = neighbours + " " + node.toString();
	}
	
	return "[" + neighbours + "]";
}


}
