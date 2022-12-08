package model.algo.Dijkstra;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/*import java.util.LinkedHashMap;
import java.util.LinkedList;*/
import java.util.Map;
/*import java.util.Map.Entry;

import ch.qos.logback.core.joran.sanity.Pair;*/

import java.util.Set;

import model.Livraison;
/*import model.Livreur;
import model.Plan;*/
import model.Segment;

/**
 *
 * @author Yannick
 */

public class Solveur {

    Map<String, Graphe> arborescenceParLivraison;
    // ArrayList<Noeud> listeDestination;
    ArrayList<Livraison> listeLivraison;
    ArrayList<Segment> listeSegment;
    Graphe graphe;

    public Solveur() {
        assert (false); // Fait crash le programme (cela évite qu'on construire un DA vide)
    }

    // Initialise un graphe avec toutes les destinations d'un livreur de marquées
    // public DijkstraAlgo(Plan plan, Livreur livreur) {
    public Solveur(Graphe graphe) {
        this.graphe = graphe;
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
            Noeud noeudVoisin, Map<Noeud, Lien> arborescence,
            Map<Noeud, Map<Noeud, Segment>> liensEntreNoeuds) {
        // On crée une nouvelle liste de références
        ArrayList<Segment> nouveauChemin = new ArrayList<Segment>();
        // On ajoute le chemin allant du noeud de référence au noeud actuel
        nouveauChemin.addAll(arborescence.get(noeudActuel).obtenirChemin());
        // Et on lui ajoute le segment de noeudActuel à voisin
        nouveauChemin.add(liensEntreNoeuds.get(noeudActuel).get(noeudVoisin));

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
            Noeud noeudVoisin, Map<Noeud, Lien> arborescence,
            Map<Noeud, Map<Noeud, Segment>> liensEntreNoeuds) {
        // On récupère le nouveau poids
        double nouveauPoids = arborescence.get(noeudActuel).obtenirPoids();
        nouveauPoids += liensEntreNoeuds.get(noeudActuel).get(noeudVoisin).obtenirLongueur();

        return nouveauPoids;
    }

    /**
     * Méthode qui calcule les arborescences pour chaque noeud donc chaque
     * Intersection de livraison
     * 
     * @param graphePourArbo
     * @param noeudsACalculer
     */
    public void calculerArborescences(List<Noeud> noeudsACalculer) {
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

    private void calculerArborescenceDepuisNoeud(Noeud noeud) {
        /*
         * TODO : algo de dijkstra depuis ce noeud.
         * Une fois l'arborescence calculée, la donner au noeud
         */

        // Rq : Map.Entry est un équivalent de Pair
        Map<Noeud, Lien> arborescence = new HashMap<Noeud, Lien>();
        Map<Noeud, Map<Noeud, Segment>> liensEntreNoeuds = graphe.obtenirLiensEntreNoeuds();

        /* Preparation */
        // Sommets "noirs" de l'algo
        ArrayList<Noeud> noeudsTraites = new ArrayList<Noeud>();
        // Sommets "gris" de l'algo (on a besoin d'une liste ordonnée, donc un hashset)
        Set<Noeud> noeudsEnCours = new HashSet<Noeud>();
        // Sommets "blanc" de l'algo
        ArrayList<Noeud> noeudsAExplorer = new ArrayList<Noeud>();
        noeudsAExplorer.addAll(graphe.obtenirNoeuds());
        noeudsAExplorer.remove(noeud); // On retire en premier le noeud origine
        noeudsEnCours.add(noeud);

        arborescence.put(noeud, new Lien(new ArrayList<Segment>(), (double) 0));

        ArrayList<Segment> nouveauChemin;
        double nouveauPoids;

        while (!noeudsEnCours.isEmpty()) {
            // TODO : remplacer par une recherche de noeud ayant un coup minimal (utiliser
            // arborescence)
            Noeud noeudActuel = noeudsEnCours.iterator().next();

            // On récupère tous les noeuds qui sont accessibles par noeudActuel
            // avec keySet() (ensemble des clés d'une Map)
            for (Noeud voisin : liensEntreNoeuds.get(noeudActuel).keySet()) {
                // Si le noeud est "blanc" ou "gris"
                if (!noeudsTraites.contains(voisin)) {

                    /* Relachement de l'arc */
                    // noeudActuel = Si, voisin = Sj
                    if (arborescence.containsKey(voisin)) {
                        if (arborescence.get(voisin).obtenirPoids() > arborescence.get(noeudActuel).obtenirPoids()
                                + liensEntreNoeuds.get(noeudActuel).get(voisin).obtenirLongueur()) {

                            nouveauChemin = calculerNouveauChemin(noeudActuel, voisin, arborescence, liensEntreNoeuds);
                            nouveauPoids = calculerNouveauPoids(noeudActuel, voisin, arborescence, liensEntreNoeuds);

                            // On modifie l'arborescence actuelle
                            arborescence.get(noeudActuel).modifierChemin(nouveauChemin);
                            arborescence.get(noeudActuel).modifierPoids(nouveauPoids);
                        }
                    } else {
                        /*
                         * Si on a pas voisin dans l'arborescence, c'est
                         * nécessairement que son poids était équivalent à +infini
                         */
                        nouveauChemin = calculerNouveauChemin(noeudActuel, voisin, arborescence, liensEntreNoeuds);
                        nouveauPoids = calculerNouveauPoids(noeudActuel, voisin, arborescence, liensEntreNoeuds);

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
}
