package model.algo.Dijkstra;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
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

    DijkstraAlgo dijal;

    @DisplayName("Test bidon")
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    public Intersection construireLivraisonAleatoire(Plan p) {
        ArrayList<Intersection> interList = p.obtenirListeIntersection();
        int index = (int) (Math.random() * interList.size());
        return interList.get(index);
    }

    @Test
    public void testSetup() throws CloneNotSupportedException {
        // Initialiser le plan
        Plan plan = new Plan();
        plan.chargerXML("src/main/java/smallMap.xml");

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

        this.dijal = new DijkstraAlgo(plan, livreur);
        Tournee t = new Tournee(dijal.calculerTournee());
        System.out.println(t);
    }
}
