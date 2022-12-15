package com.hexa17.pldagile.model.algo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.hexa17.pldagile.model.Intersection;

import com.hexa17.pldagile.model.Plan;
import com.hexa17.pldagile.controller.PlanLivraison;


public class SolveurTest {
    static PlanLivraison planLivraison;
    static Solveur solveur;
    static Noeud noeudTest;

    /**
     * Méthode qui renvoi une intersection aléatoire du plan
     * @return Intersection intersection aléatoire du plan
     */
    public Noeud obtenirNoeudAleatoire()
    {
        Plan plan = planLivraison.obtenirPlan();
        int index = (int) (Math.random() * (plan.obtenirListeIntersection().size()+1));
        return plan.obtenirListeIntersection().get(index);
    }

    /**
     * Génère le plan nécessaire aux tests de l'algorithme.
     */
    @BeforeAll
    public static void initPlan() {
        // Initialiser le plan
        planLivraison = new PlanLivraison("data/largeMap.xml");
        solveur = new Solveur(planLivraison.obtenirPlan());
    }

    @BeforeEach
    public void choisirNoeudAleatoire() {
        noeudTest = obtenirNoeudAleatoire();
    }

    /**
     * Test vérifiant que les arborescences sont bien calculées (non nulle)
     */
    @RepeatedTest(5)
    void testArborescenceCalculee() {

        Noeud noeudTeste = obtenirNoeudAleatoire();
        solveur.calculerArborescenceDepuisNoeud(noeudTeste);
        assertNotNull(noeudTeste.obtenirArborescence());
    }

    /**
     * Test vérifiant que l'arborescence contient tous les nœuds du graphe
     */
    @Test
    void testArborescenceContientLesNoeudsDuGraphe() {

        Noeud noeudTeste = obtenirNoeudAleatoire();
        ArrayList<Intersection> listeAutreIntersection = planLivraison.obtenirPlan().obtenirListeIntersection();

        solveur.calculerArborescenceDepuisNoeud(noeudTeste);

        Map<Noeud, Lien> arborescenceNoeudTraite = noeudTeste.obtenirArborescence();

        for(Intersection autreIntersection : listeAutreIntersection) {
            assertTrue(arborescenceNoeudTraite.containsKey(autreIntersection));
        }
    }

    /**
     * Test vérifiant qu'une arborescence n'est calculée qu'une seule fois
     * (si déjà calculée rappeler calculerArborescenceDepuisNoeud doit retourner
     * dès son début).
     */
    @Test
    void testArborescenceCalculeeUneSeuleFois() {

        solveur.calculerArborescenceDepuisNoeud(noeudTest);
        Map<Noeud, Lien> arborescencePremierCalcul = noeudTest.obtenirArborescence();

        solveur.calculerArborescenceDepuisNoeud(noeudTest);
        Map<Noeud, Lien> arborescenceDeuxiemeCalcul = noeudTest.obtenirArborescence();

        assertSame(arborescencePremierCalcul, arborescenceDeuxiemeCalcul);
    }

    @Test
    void testPoidsStrictementPositifsHorsSoiMeme() {

        solveur.calculerArborescenceDepuisNoeud(noeudTest);
        Map<Noeud, Lien> arborescence = noeudTest.obtenirArborescence();

        // Le lien vers soi-même est vide et de poids 0, on le retire
        arborescence.remove(noeudTest);

        for(Map.Entry<Noeud, Lien> couple : arborescence.entrySet()) {
            assertTrue(couple.getValue().obtenirPoids() > 0);
        }
    }

}
