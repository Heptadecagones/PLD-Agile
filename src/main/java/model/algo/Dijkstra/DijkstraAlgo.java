package model.algo.Dijkstra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Livreur;
import model.PlanLivraison;
import model.Segment;
import model.algo.TSP.TSP;
import model.algo.TSP.TSP1;

/**
 *
 * @author Yannick
 */

public class DijkstraAlgo {

    Map<String, Graph> tousLesGraphes;
    // ArrayList<Node> listeDestination;
    ArrayList<Segment> listeSegment;

    // Initialise un graphe sans liste de destinations
    public DijkstraAlgo() {

        this.tousLesGraphes = new LinkedHashMap<>();
        this.listeSegment = new ArrayList<Segment>();
    }

    // Initialise un graphe avec toutes les destinations d'un livreur de marquées
    public DijkstraAlgo(PlanLivraison planLivraison, Livreur livreur) {

        // WARN: Copie superficielle dans listeSegment
        this.listeSegment = planLivraison.obtenirPlan().obtenirListeSegment();
        this.tousLesGraphes = new LinkedHashMap<String, Graph>();
        // Met l'entrepôt en premier dans la liste des destinations
        tousLesGraphes.put(planLivraison.obtenirPlan().obtenirEntrepot().obtenirId(), new Graph(planLivraison));

        // Met les livraisons du livreur spécifié dans la liste des destinations
        for (int i = 0; i < livreur.obtenirLivraisons().size(); i++) {
            String nomLivraison = livreur.obtenirLivraisons().get(i).obtenirLieu().obtenirId();
            tousLesGraphes.put(nomLivraison, new Graph(planLivraison));
        }

    }

    /**
     * Calcule une tournée.
     * 
     * @return un objet Tournee
     * @throws CloneNotSupportedException
     */
    public ArrayList<Segment> calculerTournee() throws CloneNotSupportedException {

        ArrayList<Segment> tournee = new ArrayList<Segment>();
        Node nodeSourceGrapheTSP = null, nodeSourceGraphe = null;
        Graph grapheTSP = new Graph();

        // Ajoute les nodes de tous les graphes (un graphe par livreur) à TSP
        for (Map.Entry<String, Graph> entreMap : tousLesGraphes.entrySet()) {
            Node temp = new Node(entreMap.getKey());
            grapheTSP.ajouterNode(temp);
        }

        // Construit le graphe simplifié pour TSP. Le graphe simplifié contient
        // uniquement les destinations et les coûts les plus faibles pour voyager
        // entre elles.
        for (Map.Entry<String, Graph> entreMap : tousLesGraphes.entrySet()) {
            Graph graphe = entreMap.getValue();

            // On récupère la source du graphe pour faire l'algo Dijkstra
            for (Node n : graphe.obtenirNodes()) {
                if (n.obtenirNom().equals(entreMap.getKey())) {
                    nodeSourceGraphe = n;
                    break;
                }
            }

            // On récupère la source pour le graphe TSP, la node à qui on va ajouter des
            // valeurs
            for (Node n : grapheTSP.obtenirNodes()) {
                if (n.obtenirNom().equals(entreMap.getKey())) {
                    nodeSourceGrapheTSP = n;
                    break;
                }
            }

            graphe = calculerPlusCourtCheminDepuisLaSource(graphe, nodeSourceGraphe);

            // Ajouter les noeuds/valeurs pour graphe TSP
            for (Node destNode : grapheTSP.obtenirNodes()) {
                for (Node node : graphe.obtenirNodes()) {
                    if (destNode.obtenirNom().equals(node.obtenirNom()) && nodeSourceGrapheTSP != null) {
                        nodeSourceGrapheTSP.ajouterDestination(destNode, node.obtenirDistance());
                        break;
                    }
                }
            }
        }

        // Cherche une solution optimale de trajet entre les destinations
        TSP calculDeTournee = new TSP1();
        calculDeTournee.searchSolution(20000, grapheTSP);
        Node[] ordreLivraison = calculDeTournee.obtenirSolution();

        String depart = null;
        String arrivee = null;

        // On ajoute les segments dans la tournée
        for (int i = 0; i < ordreLivraison.length - 1; i++) {
            depart = ordreLivraison[i].obtenirNom();
            arrivee = ordreLivraison[i + 1].obtenirNom();
            tournee = ajouterSegment(depart, arrivee, tournee);
        }

        // On ajoute les segments entre la dernière livraison et l'entrepôt dans la
        // tournée
        depart = ordreLivraison[ordreLivraison.length - 1].obtenirNom();
        arrivee = ordreLivraison[0].obtenirNom();
        tournee = ajouterSegment(depart, arrivee, tournee);

        return tournee;
    }

    /**
     * @param depart  (String, nom de Node)
     * @param arrivee (String, nom de Node)
     * @param tournee (ArrayList<Segment>)
     * @return
     */
    private ArrayList<Segment> ajouterSegment(String depart, String arrivee, ArrayList<Segment> tournee) {

        Node nodeChemin = null;
        Node nodeDepart = null;
        Node nodeArrivee = null;

        // On récupère le chemin entre deux nodes du GrapheTSP
        for (Map.Entry<String, Graph> entreMap : tousLesGraphes.entrySet()) {
            if (entreMap.getKey().equals(depart)) {
                for (Node n : entreMap.getValue().obtenirNodes()) {
                    if (n.obtenirNom().equals(arrivee)) {
                        nodeChemin = n;
                        break;
                    }
                }
            }
        }

        // On convertit le plus court chemin de ce node en liste de segment qui va être
        // ajouté à tounee
        for (int j = 0; j < nodeChemin.obtenirCheminPlusCourt().size() - 1; j++) {
            nodeDepart = nodeChemin.obtenirCheminPlusCourt().get(j);
            nodeArrivee = nodeChemin.obtenirCheminPlusCourt().get(j + 1);

            for (Segment segment : listeSegment) {
                if (segment.obtenirOrigine().obtenirId().equals(nodeDepart.obtenirNom())
                        && segment.obtenirDestination().obtenirId().equals(nodeArrivee.obtenirNom())) {
                    tournee.add(segment);
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
                tournee.add(segment);
                break;
            }
        }
        return tournee;
    }

    private static void calculerDistanceMinimale(Node evaluationNode, Double edgeWeigh, Node sourceNode) {
        double sourceDistance = sourceNode.obtenirDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.obtenirDistance()) {
            evaluationNode.modifierDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.obtenirCheminPlusCourt());
            shortestPath.add(sourceNode);
            evaluationNode.modifierCheminPlusCourt(shortestPath);
        }
    }

    private static Node obtenirNodeDistanecPlusCourte(Set<Node> unsettledNodes) {
        Node nodeDistanecPlusCourte = null;
        double plusCouteDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            double nodeDistance = node.obtenirDistance();
            if (nodeDistance < plusCouteDistance) {
                plusCouteDistance = nodeDistance;
                nodeDistanecPlusCourte = node;
            }
        }
        return nodeDistanecPlusCourte;
    }

    public static Graph calculerPlusCourtCheminDepuisLaSource(Graph graphe, Node source) {
        source.modifierDistance(0);

        Set<Node> nodesResolus = new HashSet<>();
        Set<Node> nodesNonResolus = new HashSet<>();

        nodesNonResolus.add(source);

        while (nodesNonResolus.size() != 0) {
            Node nodeActuel = obtenirNodeDistanecPlusCourte(nodesNonResolus);
            nodesNonResolus.remove(nodeActuel);
            for (Entry<Node, Double> adjacencyPair : nodeActuel.obtenirNodeAdjacentes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
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
}
