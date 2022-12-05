package model.algo.Dijkstra;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import model.Intersection;
import model.Livraison;
import model.Livreur;
import model.PlanLivraison;
import model.Segment;
import model.Tournee;

/**
 * Unit test for graphs.
 * 
 * @author Thibaut
 */
public class DijkstraAlgoTest {

    static PlanLivraison plan;
    static DijkstraAlgo dijal;

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
        // Initialiser le plan
        plan = new PlanLivraison();
        plan.ouvrirPlan("src/main/java/largeMap.xml");
    }

    /**
     * Prépare une livraison aléatoire et génère le graphe "algorithmique".
     */
    @BeforeEach
    public void initAlgo() {

        // Initialiser le livreur
        Livreur livreur = new Livreur(69);
        ArrayList<Livraison> livrs = new ArrayList<>();

        // Construire une liste de livraisons aléatoires
        for (int i = 0; i < 4; i++) {
            Intersection inter = construireLivraisonAleatoire(plan);
            int horaire = ((int) (Math.random() * 3)) + 8;

            Livraison l = new Livraison(horaire, inter, livreur);
            livrs.add(l);
        }

        livreur.modifierLivraisons(livrs);

        dijal = new DijkstraAlgo(plan.obtenirPlan(), livreur);
    }

    /**
     * Met les variables à null pour éviter de les réutiliser après un test.
     */
    @AfterEach
    public void deinit() {
        dijal = null;
    }

    /*
     * TODO : Le test est déprécié, ce n'est Pls DijkstraAlgo qui renvoi la tournée.
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
