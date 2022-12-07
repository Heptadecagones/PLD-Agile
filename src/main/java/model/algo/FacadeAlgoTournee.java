package model.algo;

import java.util.ArrayList;

import com.rpieniazek.tabu.TabuSearch;

import model.Segment;
import model.Tournee;
import model.algo.Dijkstra.*;
import model.algo.TSP.TSP;
import model.algo.TSP.TSP1;
import model.Plan;
import model.Livraison;
import model.Livreur;

/**
 * Patron de conception Facade pour la partie algorithmique
 * 
 * @author Hugo
 */

public abstract class FacadeAlgoTournee {
    public static Tournee calculerTournee(Plan plan, Livreur livreur) throws CloneNotSupportedException {

        ArrayList<Segment> listeSegment = new ArrayList<Segment>();
        ArrayList<Livraison> listeLivraisons = livreur.obtenirLivraisons();
        

        // Obtention du graphe simplifié entre les points de livraison
        DijkstraAlgo algo = new DijkstraAlgo(plan, livreur);
        Graphe grapheSimplifie = algo.calculerGraphePourTSP();

        int minLiv = 24;

        for(Livraison liv : livreur.obtenirLivraisons()) {
            int heureLiv = liv.obtenirPlageHoraire();
            if(heureLiv < minLiv)
                minLiv = liv.obtenirPlageHoraire();
        }

        System.out.println(grapheSimplifie.toString());

        TabuSearch ts = new TabuSearch(grapheSimplifie, minLiv);
        Noeud[] ordreLivraison = ts.soluceEnNoeuds();

        String depart = null;
        String arrivee = null;
        double heure = minLiv;

        Livraison livraison = null;

        // On ajoute les segments dans la tournee
        for (int i = 0; i < ordreLivraison.length -1; i++) {

            depart = ordreLivraison[i].obtenirNom();
            arrivee = ordreLivraison[i + 1].obtenirNom();

            for (Livraison liv : listeLivraisons) {
                if (liv.obtenirLieu().obtenirId().equals(arrivee)) {
                    livraison = liv;
                    break;
                }
            }

            double longueur = ordreLivraison[i].obtenirNoeudsAdjacents().get(ordreLivraison[i+1]);
            double temps = longueur / 15000;
            heure += temps;
            double plageHoraire = livraison.obtenirHeureLivraison();
            if (heure < plageHoraire) heure = plageHoraire;

            livraison.modifierHeureLivraison(heure);

            listeSegment.addAll(algo.obtenirSegmentsDuPluCourtCheminEntreDepartEtArrivee(
                    depart, arrivee));
            // tournee.add(plan.obtenirSegmentsParIdsIntersections(depart, arrivee));
        }

        // On ajoute les segments entre la derniere livraison et l'entrepôt dans la
        // tournee
        depart = ordreLivraison[ordreLivraison.length - 1].obtenirNom();
        arrivee = ordreLivraison[0].obtenirNom();
        listeSegment.addAll(algo.obtenirSegmentsDuPluCourtCheminEntreDepartEtArrivee(
                depart, arrivee));

        Tournee tournee = new Tournee(listeSegment, listeLivraisons);
        return tournee;
    }
}
