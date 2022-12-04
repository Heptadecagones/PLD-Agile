package model.algo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import model.Intersection;
import model.Livraison;
import model.Livreur;
import model.PlanLivraison;
import model.Segment;
import model.Tournee;

import model.algo.Dijkstra.*;

public class FacadeAlgoTourneeTest {

    static PlanLivraison plan;
    static DijkstraAlgo dijal;

    /**
     * Construit une livraison aléatoire
     * 
     * @param plan
     * @return une intersection du plan
     */

    // TODO : vérifier si utile pour les tests
    public Intersection construireLivraisonAleatoire(PlanLivraison p) {
        ArrayList<Intersection> interList = p.obtenirPlan().obtenirListeIntersection();
        int index = (int) (Math.random() * interList.size());
        return interList.get(index);
    }


    /**
     * Génère le plan nécessaire aux tests de l'algorithme.
     */
    @BeforeAll
    public static void initPlan() {
        // Initialiser le plan
        plan = new PlanLivraison();
        plan.ouvrirPlan("src/main/java/largeMap.xml");
    }

    // TODO : implanter un test vérifiant un parcours connu (testé depuis main)
}
