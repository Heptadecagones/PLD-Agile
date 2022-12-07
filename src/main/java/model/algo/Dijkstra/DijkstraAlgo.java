package model.algo.Dijkstra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Livraison;
import model.Livreur;
import model.Plan;
import model.Segment;

/**
 *
 * @author Yannick
 */

public class DijkstraAlgo {

    Map<String, Graphe> tousLesGraphes;
    // ArrayList<Noeud> listeDestination;
    ArrayList<Livraison> listeLivraison;
    ArrayList<Segment> listeSegment;
    Graphe grapheTSP;

    // Initialise un graphe sans liste de destinations
    public DijkstraAlgo() {

        this.tousLesGraphes = new LinkedHashMap<>();
        this.listeSegment = new ArrayList<Segment>();
        this.grapheTSP = new Graphe();
    }

    // Initialise un graphe avec toutes les destinations d'un livreur de marquées
    public DijkstraAlgo(Plan plan, Livreur livreur) {

        // WARN: Copie superficielle dans listeSegment
        this.listeSegment = plan.obtenirListeSegment();
        this.tousLesGraphes = new LinkedHashMap<String, Graphe>();
        this.grapheTSP = new Graphe();
        // Met l'entrepôt en premier dans la liste des destinations
        tousLesGraphes.put(plan.obtenirEntrepot().obtenirId(), new Graphe(plan));

        // On ajoute l'entrepôt au graphe TSP en premier
        Noeud entrepot = new Noeud(plan.obtenirEntrepot().obtenirId());
        grapheTSP.ajouterNoeud(entrepot);

        // Met les livraisons du livreur spécifié dans la liste des destinations
        for (Livraison livraison : livreur.obtenirLivraisons()) {
            String nomLivraison = livraison.obtenirLieu().obtenirId();
            tousLesGraphes.put(nomLivraison, new Graphe(plan));
            Noeud temp = new Noeud(nomLivraison);
            temp.modifierHoraireLivraison(livraison.obtenirPlageHoraire());
            grapheTSP.ajouterNoeud(temp);
        }
    }

    /**
     * 
     * @throws CloneNotSupportedException
     */
    public Graphe calculerGraphePourTSP() throws CloneNotSupportedException {

        ArrayList<Segment> tournee = new ArrayList<Segment>();
        Noeud nodeSourceGrapheTSP = null, nodeSourceGraphe = null;

        // Construit le graphe simplifié pour TSP. Le graphe simplifié contient
        // uniquement les destinations et les coûts les plus faibles pour voyager
        // entre elles.
        for (Map.Entry<String, Graphe> entreMap : tousLesGraphes.entrySet()) {
            Graphe graphe = entreMap.getValue();

            // On récupère la source du graphe pour faire l'algo Dijkstra
            for (Noeud n : graphe.obtenirNoeuds()) {
                if (n.obtenirNom().equals(entreMap.getKey())) {
                    nodeSourceGraphe = n;
                    break;
                }
            }

            // On récupère la source pour le graphe TSP, la node à qui on va ajouter des
            // valeurs
            for (Noeud n : grapheTSP.obtenirNoeuds()) {
                if (n.obtenirNom().equals(entreMap.getKey())) {
                    nodeSourceGrapheTSP = n;
                    break;
                }
            }

            graphe = calculerPlusCourtCheminDepuisLaSource(graphe, nodeSourceGraphe);

            // Ajouter les noeuds/valeurs pour graphe TSP
            for (Noeud destNoeud : grapheTSP.obtenirNoeuds()) {
                for (Noeud node : graphe.obtenirNoeuds()) {
                    if (destNoeud.obtenirNom().equals(node.obtenirNom()) && nodeSourceGrapheTSP != null) {
                        nodeSourceGrapheTSP.ajouterDestination(destNoeud, node.obtenirDistance());
                        break;
                    }
                }
            }
        }

        return grapheTSP;

        /*
         * TSP calculDeTournee = new TSP1();
         * calculDeTournee.searchSolution(20000, grapheTSP);
         * Noeud[] ordreLivraison = calculDeTournee.obtenirSolution();
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

    private static void calculerDistanceMinimale(Noeud evaluationNoeud, Double edgeWeigh, Noeud sourceNoeud) {
        double sourceDistance = sourceNoeud.obtenirDistance();
        if (sourceDistance + edgeWeigh < evaluationNoeud.obtenirDistance()) {
            evaluationNoeud.modifierDistance(sourceDistance + edgeWeigh);
            LinkedList<Noeud> shortestPath = new LinkedList<>(sourceNoeud.obtenirCheminPlusCourt());
            shortestPath.add(sourceNoeud);
            evaluationNoeud.modifierCheminPlusCourt(shortestPath);
        }
    }

    private static Noeud obtenirNoeudDistanecPlusCourte(Set<Noeud> unsettledNoeuds) {
        Noeud nodeDistancePlusCourte = null;
        double plusCouteDistance = Integer.MAX_VALUE;
        for (Noeud node : unsettledNoeuds) {
            double nodeDistance = node.obtenirDistance();
            if (nodeDistance < plusCouteDistance) {
                plusCouteDistance = nodeDistance;
                nodeDistancePlusCourte = node;
            }
        }
        return nodeDistancePlusCourte;
    }

    public static Graphe calculerPlusCourtCheminDepuisLaSource(Graphe graphe, Noeud source) {
        source.modifierDistance(0);

        Set<Noeud> nodesResolus = new HashSet<>();
        Set<Noeud> nodesNonResolus = new HashSet<>();

        nodesNonResolus.add(source);
        source.modifierDistance(0);

        while (nodesNonResolus.size() != 0) {
            Noeud nodeActuel = obtenirNoeudDistanecPlusCourte(nodesNonResolus);
            nodesNonResolus.remove(nodeActuel);
            for (Entry<Noeud, Double> adjacencyPair : nodeActuel.obtenirNoeudsAdjacents().entrySet()) {
                Noeud adjacentNoeud = adjacencyPair.getKey();
                double edgeWeight = adjacencyPair.getValue();
                if (!nodesResolus.contains(adjacentNoeud)) {
                    calculerDistanceMinimale(adjacentNoeud, edgeWeight, nodeActuel);
                    nodesNonResolus.add(adjacentNoeud);
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
                for (Noeud n : entreMap.getValue().obtenirNoeuds()) {
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
