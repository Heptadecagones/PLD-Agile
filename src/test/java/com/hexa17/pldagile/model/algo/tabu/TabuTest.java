package com.hexa17.pldagile.model.algo.tabu;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hexa17.pldagile.controller.PlanLivraison;
import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Livraison;
import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.algo.Solveur;

public class TabuTest {
    static PlanLivraison pl;
    static Solveur solveur;
    @BeforeAll
    public static void init() {
        TabuTest.pl = new PlanLivraison("data/mediumMap.xml");
        TabuTest.solveur = new Solveur(pl.obtenirPlan());

    }

    // Test unitaire de la méthode invoquer()
    @Test
    public void testInvoquer() {
        int taille = 30;
        Matrice matrice = new Matrice(taille);
        TabuAlgo algo = new TabuAlgo(matrice);
        algo.invoquer();
    }

    // Renvoie une intersection aléatoire de PlanLivraison
    private Intersection obtenirInterAleatoire() {
        ArrayList<Intersection> inters = pl.obtenirPlan().obtenirListeIntersection();
        return inters.get((int) (Math.random() * inters.size()));
    }

    private ArrayList<Livraison> genererLivraisons(int nbLivr, Livreur livreur) {
        ArrayList<Livraison> livraisons = new ArrayList<>();

        for (int i = 0; i < nbLivr; i++) {
            int horaire = (int) (Math.random() * nbLivr);
            Intersection lieu = obtenirInterAleatoire();
            solveur.calculerArborescenceDepuisNoeud(lieu);
            livraisons.add(new Livraison(horaire, lieu, livreur));
        }

        return livraisons;
    }


    // Test fonctionnel de Tabu
    @Test
    public void testConversionMatrice() {
        Livreur l = new Livreur(69, "Antoine Griezmann");
        ArrayList<Livraison> livraisons = genererLivraisons(5, l);
        TabuAlgo algo = new TabuAlgo(livraisons);
        algo.invoquer();
    }
}
