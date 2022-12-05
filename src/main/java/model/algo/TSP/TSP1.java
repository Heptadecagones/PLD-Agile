package model.algo.TSP;

import java.util.Collection;
import java.util.Iterator;

import model.algo.Dijkstra.Graphe;
import model.algo.Dijkstra.Noeud;

/**
 *
 * @author Hugo
 */

public class TSP1 extends TemplateTSP {
    @Override
    protected int bound(Noeud currentVertex, Collection<Noeud> unvisited) {
        return 0;
    }

    @Override
    protected Iterator<Noeud> iterator(Noeud currentVertex, Collection<Noeud> unvisited, Graphe g) {
        return new SeqIter(unvisited, currentVertex, g);
    }

}
