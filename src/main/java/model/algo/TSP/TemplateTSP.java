package model.algo.TSP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.algo.Dijkstra.Graphe;
import model.algo.Dijkstra.Noeud;

/**
 *
 * @author Hugo
 */

public abstract class TemplateTSP implements TSP {
    private Noeud[] bestSol;
    protected Graphe g;
    private double bestSolCost;
    private int timeLimit;
    private long startTime;

    public void searchSolution(int timeLimit, Graphe g) {
        if (timeLimit <= 0)
            return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSol = new Noeud[g.obtenirNodes().size()];
        Collection<Noeud> unvisited = new ArrayList<Noeud>(g.obtenirNodes().size() - 1);
        Set<Noeud> nodes = g.obtenirNodes();
        Iterator<Noeud> it = nodes.iterator(); // Remarque : l'entrepôt est toujours le premier du set
        Collection<Noeud> visited = new ArrayList<Noeud>(g.obtenirNodes().size());
        visited.add(it.next()); // The first visited vertex is 0
        while (it.hasNext())
            unvisited.add(it.next());
        bestSolCost = Double.MAX_VALUE;
        branchAndBound(visited.iterator().next(), unvisited, visited, 0);
    }

    public Noeud getSolution(int i) {
        if (g != null && i >= 0 && i < g.obtenirNodes().size())
            return bestSol[i];
        return null;
    }

    public double getSolutionCost() {
        if (g != null)
            return bestSolCost;
        return -1;
    }

    /**
     * Method that must be defined in TemplateTSP subclasses
     * 
     * @param currentVertex
     * @param unvisited
     * @return a lower bound of the cost of paths in <code>g</code> starting from
     *         <code>currentVertex</code>, visiting
     *         every vertex in <code>unvisited</code> exactly once, and returning
     *         back to vertex <code>0</code>.
     */
    protected abstract int bound(Noeud currentVertex, Collection<Noeud> unvisited);

    /**
     * Method that must be defined in TemplateTSP subclasses
     * 
     * @param currentVertex
     * @param unvisited
     * @param g
     * @return an iterator for visiting all vertices in <code>unvisited</code> which
     *         are successors of <code>currentVertex</code>
     */
    protected abstract Iterator<Noeud> iterator(Noeud currentVertex, Collection<Noeud> unvisited, Graphe g);

    /**
     * Template method of a branch and bound algorithm for solving the TSP in
     * <code>g</code>.
     * 
     * @param currentVertex the last visited vertex
     * @param unvisited     the set of vertex that have not yet been visited
     * @param visited       the sequence of vertices that have been already visited
     *                      (including currentVertex)
     * @param currentCost   the cost of the path corresponding to
     *                      <code>visited</code>
     */
    private void branchAndBound(Noeud currentVertex, Collection<Noeud> unvisited,
            Collection<Noeud> visited, double currentCost) {
        if (System.currentTimeMillis() - startTime > timeLimit)
            return;
        if (unvisited.size() == 0) {
            Noeud entrepot = visited.iterator().next();
            // IMPORTANT : je laisse le code initial en commentaires, au cas où je me trompe
            // sur l'interprétation
            Map<Noeud, Double> nodesAdjacentes = currentVertex.obtenirNodeAdjacentes();
            if (nodesAdjacentes.containsKey(entrepot)) {
                // if (g.isArc(currentVertex,0)){
                // Le coup pour aller de la node actuelle vers la node x est la valeur de la map
                // nodes adjacentes
                // correspondant à la clé x
                // if (currentCost+g.getCost(currentVertex,0) < bestSolCost){
                if (currentCost + nodesAdjacentes.get(entrepot) < bestSolCost) {
                    visited.toArray(bestSol);
                    bestSolCost = currentCost + nodesAdjacentes.get(entrepot);
                }
            }
        } else if (currentCost + bound(currentVertex, unvisited) < bestSolCost) {
            Iterator<Noeud> it = iterator(currentVertex, unvisited, g);
            Map<Noeud, Double> adjacents = currentVertex.obtenirNodeAdjacentes();
            while (it.hasNext()) {
                Noeud nextVertex = it.next();
                visited.add(nextVertex);
                unvisited.remove(nextVertex);
                branchAndBound(nextVertex, unvisited, visited,
                        // currentCost+g.getCost(currentVertex, nextVertex));
                        currentCost + adjacents.get(nextVertex));
                visited.remove(nextVertex);
                unvisited.add(nextVertex);
            }
        }
    }

    public Noeud[] obtenirSolution() {
        return bestSol;
    }

}
