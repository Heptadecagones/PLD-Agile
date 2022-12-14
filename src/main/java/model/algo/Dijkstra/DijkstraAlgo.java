package model.algo.Dijkstra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Livreur;
import model.Plan;
import model.Segment;
import model.algo.TSP.TSP;
import model.algo.TSP.TSP1;

/**
 *
 * @author Yannick
 */

public class DijkstraAlgo {

    Map<String, Graphe> tousLesGraphes;
    // ArrayList<Node> listeDestination;
    ArrayList<Segment> listeSegment;

    // Initialise un graphe sans liste de destinations
    public DijkstraAlgo() {

        this.tousLesGraphes = new LinkedHashMap<>();
        this.listeSegment = new ArrayList<Segment>();
    }

    // Initialise un graphe avec toutes les destinations d'un livreur de marquées
    public DijkstraAlgo(Plan plan, Livreur livreur) {

        // WARN: Copie superficielle dans listeSegment
        this.listeSegment = plan.obtenirListeSegment();
        this.tousLesGraphes = new LinkedHashMap<String, Graphe>();
        // Met l'entrepôt en premier dans la liste des destinations
        tousLesGraphes.put(plan.obtenirEntrepot().obtenirId(), new Graphe(plan));

        // Met les livraisons du livreur spécifié dans la liste des destinations
        for (int i = 0; i < livreur.obtenirLivraisons().size(); i++) {
            String nomLivraison = livreur.obtenirLivraisons().get(i).obtenirLieu().obtenirId();
            tousLesGraphes.put(nomLivraison, new Graphe(plan));
        }

    }

    /**
     * 
     * @throws CloneNotSupportedException
     */
    public Graphe calculerGraphePourTSP() throws CloneNotSupportedException {

        ArrayList<Segment> tournee = new ArrayList<Segment>();
        Noeud nodeSourceGrapheTSP = null, nodeSourceGraphe = null;
        Graphe grapheTSP = new Graphe();

        // Ajoute les nodes de tous les graphes (un graphe par livreur) à TSP
        for (Map.Entry<String, Graphe> entreMap : tousLesGraphes.entrySet()) {
            Noeud temp = new Noeud(entreMap.getKey());
            grapheTSP.ajouterNode(temp);
        }

        // Construit le graphe simplifié pour TSP. Le graphe simplifié contient
        // uniquement les destinations et les coûts les plus faibles pour voyager
        // entre elles.
        for (Map.Entry<String, Graphe> entreMap : tousLesGraphes.entrySet()) {
            Graphe graphe = entreMap.getValue();

            // On récupère la source du graphe pour faire l'algo Dijkstra
            for (Noeud n : graphe.obtenirNodes()) {
                if (n.obtenirNom().equals(entreMap.getKey())) {
                    nodeSourceGraphe = n;
                    break;
                }
            }

            // On récupère la source pour le graphe TSP, la node à qui on va ajouter des
            // valeurs
            for (Noeud n : grapheTSP.obtenirNodes()) {
                if (n.obtenirNom().equals(entreMap.getKey())) {
                    nodeSourceGrapheTSP = n;
                    break;
                }
            }

            graphe = calculerPlusCourtCheminDepuisLaSource(graphe, nodeSourceGraphe);

            // Ajouter les noeuds/valeurs pour graphe TSP
            for (Noeud destNode : grapheTSP.obtenirNodes()) {
                for (Noeud node : graphe.obtenirNodes()) {
                    if (destNode.obtenirNom().equals(node.obtenirNom()) && nodeSourceGrapheTSP != null) {
                        nodeSourceGrapheTSP.ajouterDestination(destNode, node.obtenirDistance());
                        break;
                    }
                }
            }
        }

        return grapheTSP;

        /*
         * TSP calculDeTournee = new TSP1();
         * calculDeTournee.searchSolution(20000, grapheTSP);
         * Node[] ordreLivraison = calculDeTournee.obtenirSolution();
         * 
         * String depart = null;
         * String arrivee = null;
         * 
         * // On ajoute les segments dans la tournée
         * for (int i = 0; i < ordreLivraison.length - 1; i++) {
         * depart = ordreLivraison[i].obtenirNom();
         * arrivee = ordreLivraison[i + 1].obtenirNom();
         * tournee = ajouterSegment(depart, arrivee, tournee);
         * }
         * 
         * // On ajoute les segments entre la dernière livraison et l'entrepôt dans la
         * // tournée
         * depart = ordreLivraison[ordreLivraison.length - 1].obtenirNom();
         * arrivee = ordreLivraison[0].obtenirNom();
         * tournee = ajouterSegment(depart, arrivee, tournee);
         * 
         * return tournee;
         */
    }

    private static void calculerDistanceMinimale(Noeud evaluationNode, Double edgeWeigh, Noeud sourceNode) {
        double sourceDistance = sourceNode.obtenirDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.obtenirDistance()) {
            evaluationNode.modifierDistance(sourceDistance + edgeWeigh);
            LinkedList<Noeud> shortestPath = new LinkedList<>(sourceNode.obtenirCheminPlusCourt());
            shortestPath.add(sourceNode);
            evaluationNode.modifierCheminPlusCourt(shortestPath);
        }
    }

    private static Noeud obtenirNodeDistanecPlusCourte(Set<Noeud> unsettledNodes) {
        Noeud nodeDistanecPlusCourte = null;
        double plusCouteDistance = Integer.MAX_VALUE;
        for (Noeud node : unsettledNodes) {
            double nodeDistance = node.obtenirDistance();
            if (nodeDistance < plusCouteDistance) {
                plusCouteDistance = nodeDistance;
                nodeDistanecPlusCourte = node;
            }
        }
        return nodeDistanecPlusCourte;
    }

    public static Graphe calculerPlusCourtCheminDepuisLaSource(Graphe graphe, Noeud source) {
        source.modifierDistance(0);

        Set<Noeud> nodesResolus = new HashSet<>();
        Set<Noeud> nodesNonResolus = new HashSet<>();

        nodesNonResolus.add(source);

        while (nodesNonResolus.size() != 0) {
            Noeud nodeActuel = obtenirNodeDistanecPlusCourte(nodesNonResolus);
            nodesNonResolus.remove(nodeActuel);
            for (Entry<Noeud, Double> adjacencyPair : nodeActuel.obtenirNodeAdjacentes().entrySet()) {
                Noeud adjacentNode = adjacencyPair.getKey();
                double edgeWeight = adjacencyPair.getValue();
                if (!nodesResolus.contains(adjacentNode)) {
                    calculerDistanceMinimale(adjacentNode, edgeWeight, nodeActuel);
                    nodesNonResolus.add(adjacentNode);
                }
            }
            nodesResolus.add(nodeActuel);
        }
        return graphe;
    }

    public ArrayList<Segment> obtenirSegmentsDuPluCourtCheminEntreDepartEtArrivee(String depart, String arrivee) {

        ArrayList<Segment> semgentsDuPlusCourtChemin = new ArrayList<Segment>();
        Noeud nodeChemin = null;
        Noeud nodeDepart = null;
        Noeud nodeArrivee = null;

        // on recupere le chemin entre deux node du GrapheTSP
        for (Map.Entry<String, Graphe> entreMap : tousLesGraphes.entrySet()) {
            if (entreMap.getKey().equals(depart)) {
                for (Noeud n : entreMap.getValue().obtenirNodes()) {
                    if (n.obtenirNom().equals(arrivee)) {
                        nodeChemin = n;
                        break;
                    }
                }
            }
        }

        // on convertit le pluscourt chemin de ce node en liste de segment qui va etre
        // ajouté à tounee
        for (int j = 0; j < nodeChemin.obtenirCheminPlusCourt().size() - 1; j++) {
            nodeDepart = nodeChemin.obtenirCheminPlusCourt().get(j);
            nodeArrivee = nodeChemin.obtenirCheminPlusCourt().get(j + 1);

            for (Segment segment : listeSegment) {
                if (segment.obtenirOrigine().obtenirId().equals(nodeDepart.obtenirNom())
                        && segment.obtenirDestination().obtenirId().equals(nodeArrivee.obtenirNom())) {
                    semgentsDuPlusCourtChemin.add(segment);
                    break;
                }
            }
        }
        // Ajouter le dernier segment jusqu'au point de livraison/entrepot
        if (nodeChemin.obtenirCheminPlusCourt().size() == 0) {
            depart = "";
        } else {
            depart = nodeChemin.obtenirCheminPlusCourt().get(nodeChemin.obtenirCheminPlusCourt().size() - 1)
                    .obtenirNom();
        }

        for (Segment segment : listeSegment) {
            if (segment.obtenirOrigine().obtenirId().equals(depart)
                    && segment.obtenirDestination().obtenirId().equals(arrivee)) {
                semgentsDuPlusCourtChemin.add(segment);
                break;
            }
        }
        return semgentsDuPlusCourtChemin;
    }
}
