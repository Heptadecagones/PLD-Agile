package model.algo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Intersection;
import model.Livraison;
import model.Livreur;
import model.PlanLivraison;
import model.Segment;

import model.algo.Dijkstra.*;

/**
 * Tests du patron de conception Facade pour la partie algorithmique
 * 
 * @author Hugo
 */

public class FacadeAlgoTourneeTest {

    static PlanLivraison plan;
    static DijkstraAlgo dijal;
    static final int nombreLivraisons = 4;
    static Livreur livreur;

    /**
     * Construit une livraison aléatoire
     * 
     * @param plan
     * @return une intersection du plan
     */

    public static Intersection construireLivraisonAleatoire(PlanLivraison p) {
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
    public static Livreur renvoiLivreurAvecLivraisonsAleatoires(int nombreLivraisons) {

        // Initialiser le livreur
        Livreur livreurRenvoi = new Livreur(0);
        ArrayList<Livraison> livrs = new ArrayList<>();

        // Construire une liste de livraisons aléatoires
        for (int i = 0; i < nombreLivraisons; i++) {
            Intersection inter = construireLivraisonAleatoire(plan);
            int horaire = ((int) (Math.random() * 3)) + 8;

            Livraison l = new Livraison(horaire, inter, livreur);
            livrs.add(l);
        }

        livreurRenvoi.modifierLivraisons(livrs);

        return livreurRenvoi;
    }

    @BeforeAll
    public static void initLivreur() {
        livreur = renvoiLivreurAvecLivraisonsAleatoires(nombreLivraisons);
    }

    /**
     * Vérifie que la tournée n'est pas vide lorsqu'on lui donne une livraison
     * aléatoire
     */
    @Test
    void testTourneeNonVide() {
        ArrayList<Segment> tournee = null;
        try {
            tournee = FacadeAlgoTournee.calculerTournee(plan.obtenirPlan(), livreur);
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace();
            // Si l'exception arrive le test échoue
            assertTrue(false);
        }

        assertTrue(tournee.size() > 0);
    }

    /**
     * Vérifie que l'origine du premier segment de la tournée et la destination
     * du dernier segment sont bien l'entrepôt
     */
    @Test
    void testDepartEtArriveeTourneeSontBienEntrepot() {
        ArrayList<Segment> tournee = null;
        Intersection entrepot = plan.obtenirPlan().obtenirEntrepot();
        try {
            tournee = FacadeAlgoTournee.calculerTournee(plan.obtenirPlan(), livreur);
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace();
            // Si l'exception arrive le test échoue
            assertTrue(false);
        }

        assertSame(entrepot, tournee.get(0).obtenirOrigine());
        assertSame(entrepot, tournee.get(tournee.size()-1).obtenirDestination());
    }

    /**
     * Vérifie que l'origine du premier segment de la tournée et la destination
     * du dernier segment sont bien l'entrepôt
     */
    @Test
    void testNombreSegmentsAuMoinsEgalANombreLivraison() {
        ArrayList<Segment> tournee = null;
        try {
            tournee = FacadeAlgoTournee.calculerTournee(plan.obtenirPlan(), livreur);
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace();
            // Si l'exception arrive le test échoue
            assertTrue(false);
        }

        /* Il doit y avoir au moins un Segment par livrasion, plus une pour le
            retour à l'entrpôt */

        assertTrue(tournee.size() >= nombreLivraisons+1);
    }


    // TODO : implanter un test vérifiant un parcours connu (testé depuis main)
}
