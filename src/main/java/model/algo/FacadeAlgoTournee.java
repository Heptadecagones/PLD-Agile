package model.algo;

import java.util.ArrayList;
import model.Segment;
import model.algo.Dijkstra.*;
import model.algo.TSP.TSP;
import model.algo.TSP.TSP1;
import model.Plan;
import model.Livreur;

/**
 * Patron de conception Facade pour la partie algorithmique
 * 
 * @author Hugo
 */

public abstract class FacadeAlgoTournee {
    public static ArrayList<Segment> calculerTournee(Plan plan, Livreur livreur) throws CloneNotSupportedException {
        ArrayList<Segment> tournee = new ArrayList<Segment>();

        // Obtention du graphe
        DijkstraAlgo algo = new DijkstraAlgo(plan, livreur);
        Graph grapheNoœdsLivraisons = algo.calculerGraphePourTSP();

        // calcul de le tournée
        TSP calculDeTournee = new TSP1();
        calculDeTournee.searchSolution(20000, grapheNoœdsLivraisons);
        Node[] ordreLivraison = calculDeTournee.obtenirSolution();

        String depart = null;
        String arrivee = null;

        // On ajoute les segments dans la tournee
        for (int i = 0; i < ordreLivraison.length - 1; i++) {

            depart = ordreLivraison[i].obtenirNom();
            arrivee = ordreLivraison[i + 1].obtenirNom();

            tournee.addAll(algo.obtenirSegmentsDuPluCourtCheminEntreDepartEtArrivee(
                    depart, arrivee));
            // tournee.add(plan.obtenirSegmentsParIdsIntersections(depart, arrivee));
        }

        // On ajoute les segments entre la derniere livraison et l'entrepôt dans la
        // tournee
        depart = ordreLivraison[ordreLivraison.length - 1].obtenirNom();
        arrivee = ordreLivraison[0].obtenirNom();
        tournee.addAll(algo.obtenirSegmentsDuPluCourtCheminEntreDepartEtArrivee(
                depart, arrivee));

        return tournee;
    }
}
