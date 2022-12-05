package model.algo.Dijkstra;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.rpieniazek.tabu.TabuWrapper;

import model.Intersection;
import model.Livraison;
import model.Livreur;
import model.Plan;
import model.Segment;
import model.Tournee;

/**
 * Unit tests for graphs.
 * 
 * @author Thibaut
 */
public class DijkstraAlgoTest {

    static Plan plan;
    static DijkstraAlgo dijal;
    private Graph dijResult;

    /**
     * Construit une livraison aléatoire
     * 
     * @param plan
     * @return une intersection du plan
     */
    public Intersection construireLivraisonAleatoire(Plan p) {
        ArrayList<Intersection> interList = p.obtenirListeIntersection();
        int index = (int) (Math.random() * interList.size());
        return interList.get(index);
    }

    /**
     * Génère le plan nécessaire aux tests de l'algorithme.
     */
    @BeforeAll
    public static void initPlan() {
        // Initialiser le plan
        plan = new Plan();
        plan.chargerXML("src/main/java/largeMap.xml");
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
        for (int i = 0; i < 5; i++) {
            Intersection inter = construireLivraisonAleatoire(plan);
            int horaire = ((int) (Math.random() * 3)) + 8;

            Livraison l = new Livraison(horaire, inter, livreur);
            livrs.add(l);
        }

        livreur.modifierLivraisons(livrs);

        dijal = new DijkstraAlgo(plan, livreur);
    }

    /**
     * Met les variables à null pour éviter de les réutiliser après un test.
     */
    @AfterEach
    public void deinit() {
        dijal = null;
    }

    @Test
    public void testTabu() throws CloneNotSupportedException {
        this.dijResult = dijal.calculerTournee();
        new TabuWrapper(dijResult);
        assertTrue(false);
    }

}
