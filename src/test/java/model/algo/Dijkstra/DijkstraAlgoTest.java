package model.algo.Dijkstra;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Intersection;
import model.Livraison;
import model.Livreur;
import model.Plan;
import model.Tournee;

/**
 * Unit test for graphs.
 */
public class DijkstraAlgoTest {

    static Plan plan;
    static DijkstraAlgo dijal;

    /** Construit une livraison aléatoire
     * @param plan
     * @return une intersection du plan
     */
    public Intersection construireLivraisonAleatoire(Plan p) {
        ArrayList<Intersection> interList = p.obtenirListeIntersection();
        int index = (int) (Math.random() * interList.size());
        return interList.get(index);
    }

    /** Génère le plan nécessaire aux tests de l'algorithme.
     */
    @BeforeAll
    public static void initPlan() {
        // Initialiser le plan
        plan = new Plan();
        plan.chargerXML("src/main/java/smallMap.xml");
    }

    /** Prépare une livraison aléatoire et génère le graphe "algorithmique".
     */
    @BeforeEach
    public void initAlgo() {

        // Initialiser le livreur
        Livreur livreur = new Livreur("Abdul-Martin");
        ArrayList<Livraison> livrs = new ArrayList<>();

        // Construire une liste de livraisons aléatoires
        for (int i = 0; i < 4; i++) {
            Intersection inter = construireLivraisonAleatoire(plan);
            int horaire = ((int) (Math.random() * 3)) + 8;

            Livraison l = new Livraison(horaire, inter, livreur);
            livrs.add(l);
        }

        livreur.modifierLivraisons(livrs);

        dijal = new DijkstraAlgo(plan, livreur);
    }

    /** Met les variables à null pour éviter de les réutiliser après un test.
     */
    @AfterEach
    public void deinit() {
        dijal = null;
        plan = null;
    }

    @Test
    public void testAlgorithme() throws CloneNotSupportedException {
        Tournee t = new Tournee(dijal.calculerTournee());
        //System.out.println(t);
    }

}
