package model.algo.TSP;

import java.util.Collection;
import java.util.Iterator;

import model.algo.Dijkstra.Graph;
import model.algo.Dijkstra.Node;

/**
 *
 * @author Hugo
 */

public class TSP1 extends TemplateTSP {
    @Override
    protected int bound(Node currentVertex, Collection<Node> unvisited) {
        return 0;
    }

    @Override
    protected Iterator<Node> iterator(Node currentVertex, Collection<Node> unvisited, Graph g) {
        return new SeqIter(unvisited, currentVertex, g);
    }

}
