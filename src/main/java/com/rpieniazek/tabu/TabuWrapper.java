package com.rpieniazek.tabu;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import com.google.common.collect.BiMap;

import model.Plan;
import model.Segment;
import model.Tournee;
import model.Intersection;

public class TabuWrapper {

    private final Matrix matrice; // Matrice d'adjacence du graphe
    private int nbLivreurs; // Nombre de livreurs
    private Plan plan;      // Plan utilisé pour calculer les tournées

    private BiMap<Integer, Intersection> mapRues; // HashMap qui lie un nom de rue à un ID numérique

    public TabuWrapper(Plan plan) {
        this.mapRues = new BiMap();
        this.plan = plan;
        this.matrice = ConvertirPlanEnMatrice(plan);
        this.nbLivreurs = 1;
    }

    // TODO: Changer le type de retour en une Tournée
    public Tournee RechercheContrainte() {
        // On initialise une instance de la recherche tabu
        TabuSearch ts = new TabuSearch(this.matrice);
        // Puis on effectue la recherche
        int[] intSolutions = ts.invoke();
        ArrayList<Segment> segSolutions = MapIntToSeg(intSolutions);
        Tournee t = new Tournee(segSolutions); //TODO type de retour en Tournee
        // Enfin, on vérifie que la solution soit adaptée aux contraintes
        //return intersecSolutions;
        return t;
    }

    //TODO: Test the method
    private ArrayList<Segment> MapIntToSeg(int[] intSolutions) {
        ArrayList<Segment> segSols = new ArrayList<>();
        Pair<String, String> idIdentificationSegment;
        HashMap<Pair<String, String>, Segment> segmentsParIds = this.plan.obtenirSegmentsParIds();

        for (int i = 0; i < intSolutions.length - 1; i++) {
            // intSolutions.length - 1 car on regarde toujours la solution i et i+1
            // De plus on a i-1 segments par la règle des poteaux

            idIdentificationSegment = new Pair<String, String>(
                    mapRues.get(intSolutions[i]).obtenirId(),
                    mapRues.get(intSolutions[i+1]).obtenirId()
                );

            segSols.add(segmentsParIds.get(idIdentificationSegment));
        }
        return segSols;
    }

    // Attribue à chaque nom de rue un ID unique dans mapRues
    private Matrix ConvertirPlanEnMatrice(Plan plan) {
        ArrayList<Segment> listeSeg = plan.obtenirListeSegment();
        int taille = listeSeg.size();
        double[][] preMatrice = new double[taille][taille];
        int i = 0;
        for (Segment seg : listeSeg) {
            Intersection origine = seg.obtenirOrigine();
            Intersection destination = seg.obtenirDestination();

            this.mapRues.put(i++, origine);
            this.mapRues.put(i++, destination);

            int origID = mapRues.inverse().get(origine);
            int destID = mapRues.inverse().get(destination);

            preMatrice[origID][destID] = seg.obtenirLongueur();
        }

        return new Matrix(preMatrice);
    }
}
