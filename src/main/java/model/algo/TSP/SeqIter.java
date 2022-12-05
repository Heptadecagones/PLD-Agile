package model.algo.TSP;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import model.algo.Dijkstra.Graphe;
import model.algo.Dijkstra.Noeud;

/**
 *
 * @author Hugo
 */

public class SeqIter implements Iterator<Noeud> {
    private Noeud[] candidates;
    private int nbCandidates;

    /**
     * Create an iterator to traverse the set of vertices in <code>unvisited</code>
     * which are successors of <code>currentVertex</code> in <code>g</code>
     * Vertices are traversed in the same order as in <code>unvisited</code>
     * 
     * @param unvisited
     * @param currentVertex
     * @param g
     */
    public SeqIter(Collection<Noeud> unvisited, Noeud currentVertex, Graphe g) {
        this.candidates = new Noeud[unvisited.size()];
        for (Noeud s : unvisited) {
            // On vérifie qu'il existe un lien entre la Noeud actuelle (currentVertex) et la
            // node s
            Map<Noeud, Double> nodesAdjacentes = currentVertex.obtenirNoeudAdjacentes();
            if (nodesAdjacentes.containsKey(s))
                candidates[nbCandidates++] = s;
        }
    }

    @Override
    public boolean hasNext() {
        return nbCandidates > 0;
    }

    @Override
    public Noeud next() {
        nbCandidates--;
        return candidates[nbCandidates];
    }

    @Override
    public void remove() {
    }

}
