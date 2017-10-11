package ru.laz.game.model.graph.dataexchange;


import java.util.HashMap;

import ru.laz.game.model.graph.EdgeGame;
import ru.laz.game.model.graph.NodeGame;
import ru.laz.game.model.graph.PointOnEdgeGame;
import ru.laz.game.model.graph.Polygon4Game;


public class GraphSource {
	

	public HashMap<String, NodeGame> nodes;
	public HashMap<String, PointOnEdgeGame> poes;
	public HashMap<String, EdgeGame> edges;
	public HashMap<String, Polygon4Game> polys;

	
	public GraphSource() {
		nodes = new HashMap<String, NodeGame>();
		edges = new HashMap<String, EdgeGame>();
		polys = new HashMap<String, Polygon4Game>();
		poes = new HashMap<String, PointOnEdgeGame>();
	}
	
	
	public void initialize() {
	}

	
	
}
