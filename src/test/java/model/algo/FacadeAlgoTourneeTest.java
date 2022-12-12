package model.algo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hexa17.pldagile.model.algo.Solveur;
import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.PlanLivraison;
import com.hexa17.pldagile.model.PlanUsine;

/**
 * Tests du patron de conception Facade pour la partie algorithmique
 * 
 * @author Hugo
 */

public class FacadeAlgoTourneeTest {

    static PlanLivraison plan;
    static Solveur dijal;

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
        PlanUsine pu = new PlanUsine();
        pu.chargerXML("data/largeMap.xml");
        plan = new PlanLivraison();

    }

    /**
     * Prépare une livraison aléatoire et génère le graphe "algorithmique".
     */
    public Livreur renvoiLivreuravecLivraisonsAleatoires(int nombreLivraisons) {

        // Initialiser le livreur
        Livreur livreur = new Livreur(0,"Jean Jean");
        ArrayList<Livraison> livrs = new ArrayList<>();

        // Construire une liste de livraisons aléatoires
        for (int i = 0; i < nombreLivraisons; i++) {
            Intersection inter = construireLivraisonAleatoire(plan);
            int horaire = ((int) (Math.random() * 3)) + 8;

            Livraison l = new Livraison(horaire, inter, livreur);
            livrs.add(l);
        }

        livreur.modifierLivraisons(livrs);

        return livreur;
    }

    /**
     * Vérifie que la tournée n'est pas vide lorsqu'on lui donne une livraison
     * aléatoire
     */
    @Test
    void testTourneeNonVide() {
        int nombreLivraison = 4;
        Livreur livreur = renvoiLivreuravecLivraisonsAleatoires(nombreLivraison);
        ArrayList<Segment> tournee = null;
        try {
            tournee = FacadeAlgoTournee.calculerTournee(plan.obtenirPlan(), livreur);
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace();
            // Si l'exception arrive on ne veut pas voir de
            assertTrue(false);
        }

        assertTrue(tournee.size() > 0);
    }
    // TODO : implanter un test vérifiant un parcours connu (testé depuis main)
}
