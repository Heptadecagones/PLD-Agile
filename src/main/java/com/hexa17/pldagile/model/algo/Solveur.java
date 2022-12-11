package com.hexa17.pldagile.model.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hexa17.pldagile.model.Livraison;
import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.Plan;
import com.hexa17.pldagile.model.Segment;
import com.hexa17.pldagile.model.algo.tabu.TabuSearch;

/**
 *
 * @author Hugo, Thibaut, Yannick
 */

// TODO Singleton Design Pattern
public class Solveur {

    Plan plan;
    ArrayList<Livraison> livraisons;

    // Initialise un graphe avec toutes les destinations d'un livreur de marquées
    // public DijkstraAlgo(Plan plan, Livreur livreur) {
    public Solveur(Plan plan, Livreur livreur) {
        this.plan = plan;
        this.livraisons = livreur.obtenirLivraisons();
    }

    /**
     * 
     * @param noeudActuel
     * @param noeudVoisin
     * @param arborescence     arborescence du noeud d'origine
     * @param liensEntreNoeuds
     * @return le nouveau chemin allant du noeud d'origine à noeudVoisin
     */
    private ArrayList<Segment> calculerNouveauChemin(Noeud noeudActuel,
            Noeud noeudVoisin, Map<Noeud, Lien> arborescence, Segment chemin) {
        // On crée une nouvelle liste de références
        ArrayList<Segment> nouveauChemin = new ArrayList<Segment>();
        // On ajoute le chemin allant du noeud de référence au noeud actuel
        nouveauChemin.addAll(arborescence.get(noeudActuel).obtenirChemin());
        // Et on lui ajoute le segment de noeudActuel à voisin
        nouveauChemin.add(chemin);

        return nouveauChemin;
    }

    /**
     * 
     * @param noeudActuel
     * @param noeudVoisin
     * @param arborescence     arborescence du noeud d'origine
     * @param liensEntreNoeuds
     * @return le nouveau poids allant du noeud d'origine à noeudVoisin
     */
    private double calculerNouveauPoids(Noeud noeudActuel,
            Noeud noeudVoisin, Map<Noeud, Lien> arborescence, Segment chemin) {
        // On récupère le nouveau poids
        return arborescence.get(noeudActuel).obtenirPoids() + chemin.obtenirLongueur();
    }

    /**
     * Méthode qui calcule les arborescences pour chaque noeud donc chaque
     * Intersection de livraison
     * 
     * @param graphePourArbo
     * @param noeudsACalculer
     */
    public void calculerArborescences(Set<Noeud> noeudsACalculer) {
        // noms à changer, graphe pour arbo pourrait ne pas être utile, voir
        // constructuer
        // noeudsACalculer c'est l'ensemble des noeuds pour lesquels on veut créer
        // l'arborescence

        for (Noeud noeudCalcul : noeudsACalculer) {
            if (noeudCalcul.obtenirArborescence() == null) {
                calculerArborescenceDepuisNoeud(noeudCalcul);
            }
        }
    }

    public void calculerArborescenceDepuisNoeud(Noeud noeud) {

        // On ne calcule une arborescence qu'une fois
        if (noeud.obtenirArborescence() != null)
            return;

        // Rq : Lien est un équivalent de Pair<ArrayList<Segment>, Double>,
        // voir classe Lien pour les méthodes
        Map<Noeud, Lien> arborescence = new HashMap<Noeud, Lien>();
        Map<Noeud, ArrayList<Segment>> liensEntreNoeuds = graphe.obtenirLiensEntreNoeuds();

        /* Preparation */
        // Sommets "noirs" de l'algo
        ArrayList<Noeud> noeudsTraites = new ArrayList<Noeud>();
        // Sommets "gris" de l'algo (on a besoin d'une liste ordonnée, donc un hashset)
        Set<Noeud> noeudsEnCours = new HashSet<Noeud>();
        // Sommets "blanc" de l'algo
        ArrayList<Noeud> noeudsAExplorer = new ArrayList<Noeud>();
        noeudsAExplorer.addAll(plan.obtenirNoeuds());
        noeudsAExplorer.remove(noeud); // On retire en premier le noeud origine
        noeudsEnCours.add(noeud);

        arborescence.put(noeud, new Lien(new ArrayList<Segment>(), (double) 0));

        ArrayList<Segment> nouveauChemin;
        double nouveauPoids;

        while (!noeudsEnCours.isEmpty()) {
            // TODO: remplacer par une recherche de noeud ayant un coup minimal (utiliser
            // arborescence)
            Noeud noeudActuel = noeudsEnCours.iterator().next();

            // On récupère tous les noeuds qui sont accessibles par noeudActuel
            // avec keySet() (ensemble des clés d'une Map)
            for (Segment cheminVoisin : liensEntreNoeuds.get(noeudActuel)) {
                Noeud voisin = cheminVoisin.obtenirDestination();
                // Si le noeud est "blanc" ou "gris"
                if (!noeudsTraites.contains(voisin)) {

                    /* Relachement de l'arc */
                    // noeudActuel = Si, voisin = Sj
                    if (arborescence.containsKey(voisin)) {
                        if (arborescence.get(voisin).obtenirPoids() > arborescence.get(noeudActuel).obtenirPoids()
                                + cheminVoisin.obtenirLongueur()) {

                            nouveauChemin = calculerNouveauChemin(noeudActuel, voisin, arborescence, cheminVoisin);
                            nouveauPoids = calculerNouveauPoids(noeudActuel, voisin, arborescence, cheminVoisin);

                            // On modifie l'arborescence actuelle
                            arborescence.get(noeudActuel).modifierChemin(nouveauChemin);
                            arborescence.get(noeudActuel).modifierPoids(nouveauPoids);
                        }
                    } else {
                        /*
                         * Si on a pas voisin dans l'arborescence, c'est
                         * nécessairement que son poids était équivalent à +infini
                         */
                        nouveauChemin = calculerNouveauChemin(noeudActuel, voisin, arborescence, cheminVoisin);
                        nouveauPoids = calculerNouveauPoids(noeudActuel, voisin, arborescence, cheminVoisin);

                        // On crée le Lien avec les infos
                        Lien lienVersVoisin = new Lien(nouveauChemin, nouveauPoids);
                        // On ajoute voisin dans l'arborescence

                        arborescence.put(noeudActuel, lienVersVoisin);
                    }

                    // "Coloriage" du noeud voisin en "gris"
                    if (noeudsAExplorer.contains(voisin)) {
                        noeudsAExplorer.remove(voisin);
                        noeudsEnCours.add(voisin);
                    }
                }
            }
            // "Coloriage" du noeud actuel en "noir"
            noeudsEnCours.remove(noeudActuel);
            noeudsTraites.add(noeudActuel);
        }

        /* Mise à jour de l'arborescence */
        // On enlève le lien entre le noeud et lui-même
        arborescence.remove(noeud);
        // On modifie l'arborescence du noeud
        noeud.modifierArborescence(arborescence);
    }

    // /**
    //  * 
    //  * @param noeudsCibles
    //  * @return
    //  */
    // public Map<Noeud, Map<Noeud, Lien>> calculerGrapheSimplifie(ArrayList<Noeud> noeudsCibles) {
    //     Map<Noeud, Map<Noeud, Lien>> grapheArborescence = new HashMap<Noeud, Map<Noeud, Lien>>();
    //     Map<Noeud, Lien> tempArborescence;
    //     /* Récupération des liens entre les noeuds cibles */
    //     for (Noeud noeudTraite : noeudsCibles) {
    //         tempArborescence = new HashMap<Noeud, Lien>();
    //         // FIXME arboNoeudTraite est null lorsqu'on crée une livraison
    //         Map<Noeud, Lien> arboNoeudTraite = noeudTraite.obtenirArborescence();
    //         ArrayList<Noeud> autreNoeuds = new ArrayList<Noeud>();
    //         autreNoeuds.addAll(noeudsCibles);
    //         autreNoeuds.remove(noeudTraite);
    //         for (Noeud n : autreNoeuds) {
    //             tempArborescence.put(n, arboNoeudTraite.get(n));
    //         }
    //         grapheArborescence.put(noeudTraite, tempArborescence);
    //     }

    //     /* Suppression des liens entre noeuds d'horaire de livrasion différents */
    //     return grapheArborescence;
    // }


    public ArrayList<Segment> calculerTournee() {
        
        // Calcule le graphe simplifié
        Set<Noeud> noeuds = new HashSet<>();
        int minLiv = 99;

        Noeud entrepot = plan.obtenirEntrepot();
        entrepot.modifierHoraireLivraison(99);
        entrepot.modifierArborescence(null);
        entrepot.modifierHeureLivraison(0);
        noeuds.add(entrepot);

        for (Livraison liv : livraisons) {
            Noeud noeud = liv.obtenirLieu(); 
            noeud.modifierArborescence(null);
            noeud.modifierHeureLivraison(0);
            noeuds.add(noeud);
            if (noeud.obtenirHoraireLivraison() < minLiv) minLiv = noeud.obtenirHoraireLivraison();
        }

        calculerArborescences(noeuds);
        Graphe grapheSimplifie = new Graphe(noeuds);
        TabuSearch tabu = new TabuSearch(grapheSimplifie, minLiv);
        Noeud[] ordreLivraison = tabu.soluceEnNoeuds();

        ArrayList<Segment> tournee = new ArrayList<Segment>();

        // On ajoute les segments dans la tournee
        for (int i = 0; i < ordreLivraison.length-1; i++) {
            tournee.addAll(ordreLivraison[i+1].obtenirArborescence().get(ordreLivraison[i]).obtenirChemin());
        }
        return tournee;
    }
}
