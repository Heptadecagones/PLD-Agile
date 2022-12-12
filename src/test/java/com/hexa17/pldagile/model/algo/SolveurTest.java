package com.hexa17.pldagile.model.algo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Livraison;
import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.PlanLivraison;

/**
 * Unit tests for graphs.
 * 
 * @author Thibaut
 */
public class SolveurTest {

    static PlanLivraison plan;
    static Solveur dijal;

    /**
     * Construit une livraison aléatoire
     * 
     * @param plan
     * @return une intersection du plan
     */
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
        plan = new PlanLivraison("src/main/java/largeMap.xml");
    }

    /**
     * Prépare une livraison aléatoire et génère le graphe "algorithmique".
     */
    @BeforeEach
    public void initAlgo() {

        // Initialiser le livreur
        Livreur livreur = new Livreur(69,"Jean Jean");
        ArrayList<Livraison> livrs = new ArrayList<>();

        // Construire une liste de livraisons aléatoires
        for (int i = 0; i < 5; i++) {
            Intersection inter = construireLivraisonAleatoire(plan);
            int horaire = ((int) (Math.random() * 3)) + 8;

            Livraison l = new Livraison(horaire, inter, livreur);
            livrs.add(l);
        }

        livreur.modifierLivraisons(livrs);

        dijal = new Solveur(plan.obtenirPlan());
    }

    /**
     * Met les variables à null pour éviter de les réutiliser après un test.
     */
    @AfterEach
    public void deinit() {
        dijal = null;
    }

    // @Test
    // public void testTabu() throws CloneNotSupportedException {
    //     this.dijResult = dijal.calculerGraphePourTSP();
    //     
    //     new TabuWrapper(dijResult);

    // }

    /*
     * TODO : Le test est déprécié, ce n'est Pls Solveur qui renvoi la tournée.
     * Remplacer avec un autre test.
     */
    /*
     * @RepeatedTest(3)
     * public void testAlgorithme() throws CloneNotSupportedException {
     * Tournee t = new Tournee(dijal.calculerTournee());
     * ArrayList<Segment> segs = t.obtenirListeSegment();
     * 
     * Intersection startInter = segs.get(0).obtenirOrigine();
     * Intersection endInter = segs.get(segs.size() - 1).obtenirDestination();
     * 
     * assertTrue(startInter == endInter);
     * }
     */

}
