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

    Map<String, Graphe> arborescenceParLivraison;
    // ArrayList<Noeud> listeDestination;
    ArrayList<Livraison> listeLivraison;
    ArrayList<Segment> listeSegment;
    Graphe grapheTSP;

    // Initialise un graphe sans liste de destinations
    public DijkstraAlgo() {

        this.arborescenceParLivraison = new LinkedHashMap<>();
        this.listeSegment = new ArrayList<Segment>();
        this.grapheTSP = new Graphe();
    }

    // Initialise un graphe avec toutes les destinations d'un livreur de marquées
    public DijkstraAlgo(Plan plan, Livreur livreur) {

        // WARN: Copie superficielle dans listeSegment
        this.listeSegment = plan.obtenirListeSegment();
        this.arborescenceParLivraison = new LinkedHashMap<String, Graphe>();
        this.grapheTSP = new Graphe();
        // Met l'entrepôt en premier dans la liste des destinations
        arborescenceParLivraison.put(plan.obtenirEntrepot().obtenirId(), plan.clone());

        // On ajoute l'entrepôt au graphe TSP en premier
        Noeud entrepot = new Noeud(plan.obtenirEntrepot().obtenirId());
        grapheTSP.ajouterNoeud(entrepot);

        // Met les livraisons du livreur spécifié dans la liste des destinations
        for (Livraison livraison : livreur.obtenirLivraisons()) {
            String nomLivraison = livraison.obtenirLieu().obtenirId();
            arborescenceParLivraison.put(nomLivraison, plan.clone());
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

        Noeud nodeSourceGrapheTSP = null, nodeSourceGraphe = null;

        // Construit le graphe simplifié pour TSP. Le graphe simplifié contient
        // uniquement les destinations et les coûts les plus faibles pour voyager
        // entre elles.
        for (Map.Entry<String, Graphe> entreMap : arborescenceParLivraison.entrySet()) {
            Graphe graphe = entreMap.getValue();

            // On récupère la source du graphe pour faire l'algo Dijkstra
            for (Noeud n : graphe.obtenirNoeuds()) {
                if (n.obtenirId().equals(entreMap.getKey())) {
                    nodeSourceGraphe = n;
                    break;
                }
            }

            // On récupère la source pour le graphe TSP, la node à qui on va ajouter des
            // valeurs
            for (Noeud n : grapheTSP.obtenirNoeuds()) {
                if (n.obtenirId().equals(entreMap.getKey())) {
                    nodeSourceGrapheTSP = n;
                    break;
                }
            }

            graphe = calculerPlusCourtCheminDepuisLaSource(graphe, nodeSourceGraphe);

            // Ajouter les noeuds/valeurs pour graphe TSP
            for (Noeud destNoeud : grapheTSP.obtenirNoeuds()) {
                for (Noeud node : graphe.obtenirNoeuds()) {
                    if (destNoeud.obtenirId().equals(node.obtenirId()) && nodeSourceGrapheTSP != null) {
                        nodeSourceGrapheTSP.ajouterDestination(destNoeud, node.obtenirPoids());
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
         * depart = ordreLivraison[i].obtenirId();
         * arrivee = ordreLivraison[i + 1].obtenirId();
         * tournee = ajouterSegment(depart, arrivee, tournee);
         * }
         * 
         * // On ajoute les segments entre la dernière livraison et l'entrepôt dans la
         * // tournée
         * depart = ordreLivraison[ordreLivraison.length - 1].obtenirId();
         * arrivee = ordreLivraison[0].obtenirId();
         * tournee = ajouterSegment(depart, arrivee, tournee);
         * 
         * return tournee;
         */
    }

    private static void calculerPoidsMinimal(Noeud evaluationNoeud, Double edgeWeigh, Noeud sourceNoeud) {
        double sourcePoids = sourceNoeud.obtenirPoids();
        if (sourcePoids + edgeWeigh < evaluationNoeud.obtenirPoids()) {
            evaluationNoeud.modifierPoids(sourcePoids + edgeWeigh);
            LinkedList<Noeud> shortestPath = new LinkedList<>(sourceNoeud.obtenirCheminPlusCourtDepuisEntrepot());
            shortestPath.add(sourceNoeud);
            evaluationNoeud.modifierCheminPlusCourtDepuisEntrepot(shortestPath);
        }
    }

    private static Noeud obtenirNoeudDistanecPlusCourte(Set<Noeud> unsettledNoeuds) {
        Noeud nodePoidsPlusFaible = null;
        double plusPetitPoids = Integer.MAX_VALUE;
        for (Noeud node : unsettledNoeuds) {
            double nodePoids = node.obtenirPoids();
            if (nodePoids < plusPetitPoids) {
                plusPetitPoids = nodePoids;
                nodePoidsPlusFaible = node;
            }
        }
        return nodePoidsPlusFaible;
    }

    public static Graphe calculerPlusCourtCheminDepuisLaSource(Graphe graphe, Noeud source) {
        source.modifierPoids(0);

        Set<Noeud> nodesResolus = new HashSet<>();
        Set<Noeud> nodesNonResolus = new HashSet<>();

        nodesNonResolus.add(source);
        source.modifierPoids(0);

        while (nodesNonResolus.size() != 0) {
            Noeud nodeActuel = obtenirNoeudDistanecPlusCourte(nodesNonResolus);
            nodesNonResolus.remove(nodeActuel);
            for (Entry<Noeud, Double> adjacencyPair : nodeActuel.obtenirNoeudsAdjacents().entrySet()) {
                Noeud adjacentNoeud = adjacencyPair.getKey();
                double edgeWeight = adjacencyPair.getValue();
                if (!nodesResolus.contains(adjacentNoeud)) {
                    calculerPoidsMinimal(adjacentNoeud, edgeWeight, nodeActuel);
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
        for (Map.Entry<String, Graphe> entreMap : arborescenceParLivraison.entrySet()) {
            if (entreMap.getKey().equals(depart)) {
                for (Noeud n : entreMap.getValue().obtenirNoeuds()) {
                    if (n.obtenirId().equals(arrivee)) {
                        nodeChemin = n;
                        break;
                    }
                }
            }
        }

        // on convertit le pluscourt chemin de ce node en liste de segment qui va etre
        // ajouté à tounee
        for (int j = 0; j < nodeChemin.obtenirCheminPlusCourtDepuisEntrepot().size() - 1; j++) {
            nodeDepart = nodeChemin.obtenirCheminPlusCourtDepuisEntrepot().get(j);
            nodeArrivee = nodeChemin.obtenirCheminPlusCourtDepuisEntrepot().get(j + 1);

            for (Segment segment : listeSegment) {
                if (segment.obtenirOrigine().obtenirId().equals(nodeDepart.obtenirId())
                        && segment.obtenirDestination().obtenirId().equals(nodeArrivee.obtenirId())) {
                    semgentsDuPlusCourtChemin.add(segment);
                    break;
                }
            }
        }
        // Ajouter le dernier segment jusqu'au point de livraison/entrepot
        if (nodeChemin.obtenirCheminPlusCourtDepuisEntrepot().size() == 0) {
            depart = "";
        } else {
            depart = nodeChemin.obtenirCheminPlusCourtDepuisEntrepot().get(nodeChemin.obtenirCheminPlusCourtDepuisEntrepot().size() - 1)
                    .obtenirId();
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
