package ru.laz.game.model.graph;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public class EdgeGame {
	
	
	private Array<String> nodesToLink;



	GraphGame graph;

	private EdgeGame() {}
	
	public EdgeGame(String n1, String n2, GraphGame graph) {
		if (n1.equals(n2)) {
			Gdx.app.log("","Node1 must be != Node2");
			throw new IllegalArgumentException("Node1 must be != Node2");
		}
		nodesToLink = new Array<String>();
		nodesToLink.add(n1);
		nodesToLink.add(n2);
		this.graph = graph;
	}


	public void setGraph(GraphGame graph) {
		this.graph = graph;
	}
	
	public Array<String> getNodes () {
		return nodesToLink;
	}

	
	public float[] getNodeCoords() {
		
		float[] ret = new float[4];
		
		ret[0] = graph.getNodes().get(nodesToLink.get(0)).getX();
		ret[1] = graph.getNodes().get(nodesToLink.get(0)).getY();
		ret[2] = graph.getNodes().get(nodesToLink.get(1)).getX();
		ret[3] = graph.getNodes().get(nodesToLink.get(1)).getY();
		
		return ret;
	}
	
}
