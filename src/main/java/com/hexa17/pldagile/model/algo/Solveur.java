package com.hexa17.pldagile.model.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Livraison;
import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.Plan;
import com.hexa17.pldagile.model.Segment;
import com.hexa17.pldagile.model.Tournee;
import com.hexa17.pldagile.model.algo.tabu.TabuSearch;

/**
 * <p>Solveur class.</p>
 *
 * @author Hugo, Thibaut, Yannick
 * @version $Id: $Id
 */

// TODO Singleton Design Pattern
public class Solveur {

    Plan plan;

    // Initialise un graphe avec toutes les destinations d'un livreur de marquées
    // public DijkstraAlgo(Plan plan, Livreur livreur) {
    /**
     * <p>Constructor for Solveur.</p>
     *
     * @param plan a {@link com.hexa17.pldagile.model.Plan} object
     */
    public Solveur(Plan plan) {
        this.plan = plan;
    }

    /**
     * Méthode qui calcule les arborescences pour chaque noeud donc chaque
     * Intersection de livraison
     *
     * @param livraisons a {@link java.util.ArrayList} object
     */
    public void calculerArborescences(ArrayList<Livraison> livraisons) {
        // noms à changer, graphe pour arbo pourrait ne pas être utile, voir
        // constructuer
        // noeudsACalculer c'est l'ensemble des noeuds pour lesquels on veut créer
        // l'arborescence

        Set<Noeud> noeudsACalculer = new LinkedHashSet<Noeud>();

        for (Livraison liv : livraisons) {
            noeudsACalculer.add(liv.obtenirLieu());
        }

        for (Noeud noeudCalcul : noeudsACalculer) {
            if (noeudCalcul.obtenirArborescence() == null) {
                calculerArborescenceDepuisNoeud(noeudCalcul);
            }
        }
    }

    /**
     * <p>calculerArborescenceDepuisNoeud.</p>
     *
     * @param source a {@link com.hexa17.pldagile.model.algo.Noeud} object
     */
    public void calculerArborescenceDepuisNoeud(Noeud source) {

        // On ne calcule une arborescence qu'une fois
        if (source.obtenirArborescence() != null) return;

        // Rq : Lien est un équivalent de Pair<ArrayList<Segment>, Double>,
        // voir classe Lien pour les méthodes
        Map<Noeud, Lien> arborescence = new HashMap<Noeud, Lien>();
        Map<Noeud, ArrayList<Segment>> liensEntreNoeuds = plan.obtenirLiensEntreNoeuds();

        /* Preparation */
        // Sommets "noirs" de l'algo
        ArrayList<Noeud> noeudsTraites = new ArrayList<Noeud>();
        // Sommets "gris" de l'algo (on a besoin d'une liste ordonnée, donc un hashset)
        Set<Noeud> noeudsEnCours = new HashSet<Noeud>();
        // Sommets "blanc" de l'algo
        ArrayList<Noeud> noeudsAExplorer = new ArrayList<Noeud>();

        arborescence.put(source, new Lien(new ArrayList<Segment>(), (double) 0));

        
        for (Noeud n : plan.obtenirListeIntersection()) {
            if (n != source) {
                noeudsAExplorer.add(n);
                arborescence.put(n,new Lien(new ArrayList<Segment>(), (double) Integer.MAX_VALUE));
            }
        }
        
        noeudsEnCours.add(source);

        while (!noeudsEnCours.isEmpty()) {
            // TODO: remplacer par une recherche de noeud ayant un coup minimal (utiliser
            // arborescence)
            Noeud noeudActuel = rechercheNoeudDistanceMin(arborescence, noeudsEnCours);
            noeudsEnCours.remove(noeudActuel);
            noeudsTraites.add(noeudActuel);

            // On récupère tous les noeuds qui sont accessibles par noeudActuel
            // avec keySet() (ensemble des clés d'une Map)
            if (!liensEntreNoeuds.containsKey(noeudActuel)) continue;
            for (Segment cheminVoisin : liensEntreNoeuds.get(noeudActuel)) {

                Noeud voisin = cheminVoisin.obtenirDestination();
                // Si le noeud est "blanc" ou "gris"
                if (!noeudsTraites.contains(voisin)) {

                    if (noeudsAExplorer.contains(voisin)) {
                        noeudsAExplorer.remove(voisin);
                        noeudsEnCours.add(voisin);
                    }

                    double temp = arborescence.get(noeudActuel).obtenirPoids() + cheminVoisin.obtenirLongueur();

                    if (temp < arborescence.get(voisin).obtenirPoids()) {
                        arborescence.get(voisin).modifierPoids(temp);
                        arborescence.get(voisin).modifierChemin(new ArrayList<Segment>());
                        arborescence.get(voisin).obtenirChemin().addAll(arborescence.get(noeudActuel).obtenirChemin());
                        arborescence.get(voisin).obtenirChemin().add(cheminVoisin);
                    }
                }
            }
        }

        source.modifierArborescence(arborescence);
    }

    private Noeud rechercheNoeudDistanceMin(Map<Noeud, Lien> arborescence, Set<Noeud> noeudsGris) {
        Noeud noeudMin = null;
        double dist = 0;
        double distMin = Integer.MAX_VALUE;

        for (Noeud n : noeudsGris) {
            dist = arborescence.get(n).obtenirPoids();
            if (dist <= distMin) {
                distMin = dist;
                noeudMin = n;
            }
        }
        return noeudMin;
    }


    /**
     * <p>calculerTournee.</p>
     *
     * @param livreur a {@link com.hexa17.pldagile.model.Livreur} object
     * @return a {@link com.hexa17.pldagile.model.Tournee} object
     */
    public Tournee calculerTournee(Livreur livreur) {
        
        // Calcule le graphe simplifié
        ArrayList<Livraison> destinations = new ArrayList<Livraison>();

        Intersection entrepot = plan.obtenirEntrepot();
        Livraison livEntrepot = new Livraison(99, entrepot, livreur);

        destinations.add(livEntrepot);
        destinations.addAll(livreur.obtenirLivraisons());

        calculerArborescences(destinations);

        TabuSearch tabu = new TabuSearch(destinations);
        Livraison[] ordreLivraison = tabu.soluceEnNoeuds();

        ArrayList<Segment> listeSegment = new ArrayList<Segment>();
        ArrayList<Livraison> listeLivraison = new ArrayList<Livraison>();


        // On ajoute les segments dans la tournee
        for (int i = 0; i < ordreLivraison.length-1; i++) {
            listeSegment.addAll(ordreLivraison[i+1].obtenirLieu().obtenirArborescence().get(ordreLivraison[i].obtenirLieu()).obtenirChemin());
            if (i != 0) listeLivraison.add(ordreLivraison[i]);
        }

        // for (Livraison liv : listeLivraison) {
        //     System.out.println(liv.obtenirHoraireLivraison());
        //     System.out.println(liv.obtenirHeureLivraison());
        // }

        Tournee tournee = new Tournee(listeSegment, listeLivraison);
        return tournee;
    }
}
