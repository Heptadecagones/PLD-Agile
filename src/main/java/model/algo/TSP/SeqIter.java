package model.algo.TSP;

import java.util.Collection;
import java.util.Iterator;
import model.algo.Dijkstra.*;
import java.util.Map;

public class SeqIter implements Iterator<Node> {
	private Node[] candidates;
	private int nbCandidates;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code> 
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 */
	public SeqIter(Collection<Node> unvisited, Node currentVertex, Graph g){
		this.candidates = new Node[unvisited.size()];
		for (Node s : unvisited){
			// On vérifie qu'il existe un lien entre la Node actuelle (currentVertex) et la node s
			Map<Node, Integer> nodesAdjacentes = currentVertex.obtenirNodeAdjacentes();
			if(nodesAdjacentes.containsKey(s))
				candidates[nbCandidates++] = s;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Node next() {
		nbCandidates--;
		return candidates[nbCandidates];
	}

	@Override
	public void remove() {}

}
