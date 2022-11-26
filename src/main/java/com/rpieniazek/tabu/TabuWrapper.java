package com.rpieniazek.tabu;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.common.collect.BiMap;

import model.Plan;
import model.Segment;
import model.Tournee;
import model.Intersection;

public class TabuWrapper {

    private final Matrix matrice; // Matrice d'adjacence du graphe
    private int nbLivreurs; // Nombre de livreurs

    private BiMap<Integer, Intersection> mapRues; // HashMap qui lie un nom de rue à un ID numérique

    public TabuWrapper(Plan plan) {
        this.mapRues = new BiMap();
        this.matrice = ConvertirPlanEnMatrice(plan);
        this.nbLivreurs = 1;
    }

    // TODO: Changer le type de retour en une Tournée
    public ArrayList<Intersection> RechercheContrainte() {
        // On initialise une instance de la recherche tabu
        TabuSearch ts = new TabuSearch(this.matrice);
        // Puis on effectue la recherche
        int[] intSolutions = ts.invoke();
        ArrayList<Intersection> intersecSolutions = MapIntToSeg(intSolutions);
        // Tournee t = new Tournee(listeSegments); //TODO type de retour en Tournee
        // Enfin, on vérifie que la solution soit adaptée aux contraintes
        return intersecSolutions;
    }

    //TODO: Change return type to ArrayList<Segment>
    private ArrayList<Intersection> MapIntToSeg(int[] intSolutions) {
        ArrayList<Intersection> intersecSols = new ArrayList<>();
        for (int intSol : intSolutions) {
            intersecSols.add(mapRues.get(intSol));
        }
        return null;
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
