package com.hexa17.pldagile.model.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Livraison;
import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.Plan;
import com.hexa17.pldagile.model.Segment;
import com.hexa17.pldagile.model.Tournee;
import com.hexa17.pldagile.model.algo.tabu.TabuSearch;

/**
 * <p>Classe Solveur
 * <br/>
 * Classe responsable du calcul des plus courts chemins entre les intersections du plan
 * et du calcul des tournées.</p>
 *
 * @author Hugo, Thibaut, Yannick
 * @version $Id: 1.0
 */


public class Solveur {

    Plan plan;

    /**
     * <p>Constructeur parametré de la classe Solveur.</p>
     *
     * @param plan l'instance de {@link com.hexa17.pldagile.model.Plan} sur laquelle on souhaite effectuer des tournées
     */
    public Solveur(Plan plan) {
        this.plan = plan;
    }

    /**<p>
     * Méthode qui calcule les arborescences pour chaque noeud de la liste en paramètre
     *</p>
     * @param livraisons une {@link java.util.ArrayList} de {@link com.hexa17.pldagile.model.Livraison} 
correspondant à la liste des livraisons pour lesquelle on soihaite calculer l'arborescence des intersections 
à livrer
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
     * <p>Méthode qui calcule l' arborescence du {@link com.hexa17.pldagile.model.algo.Noeud} 
passé en paramètre
     * <br/>Une arborescence est l'ensemble des plus courts chemins et leur poids
 (stockée dans une instance de {@link com.hexa17.pldagile.model.algo.Lien}) 
menant aux {@link com.hexa17.pldagile.model.algo.Noeud} atteignables depuis le 
{@link com.hexa17.pldagile.model.algo.Noeud} en paramètre</p>
     *
     * @param source le {@link com.hexa17.pldagile.model.algo.Noeud} dont on 
souhaite calculer l'arborescence
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

    /**
     * <p>Méthode renvoyant le noeud le plus proche (de poids minimal) du noeud étudié</p>
     *
     * @param arborescence, l'arborescence du noeud étudié de type {@link java.util.Map}<
{@link com.hexa17.pldagile.model.algo.Noeud}, {@link com.hexa17.pldagile.model.algo.Lien}>
     * @param noeudsGris un {@link java.util.Set} de {@link com.hexa17.pldagile.model.algo.Noeud} 
qui ont été découverts
     * @return le {@link com.hexa17.pldagile.model.algo.Noeud} le plus proche du noeud étudié
     */
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
     * <p>Méthode calculant la {@link com.hexa17.pldagile.model.Tournee} d'un 
{@link com.hexa17.pldagile.model.Livreur}, otpimisée pour être terminer le plus 
tôt possible </p>
     *
     * @param livreur le {@link com.hexa17.pldagile.model.Livreur} dont on souhaite calculer la {@link com.hexa17.pldagile.model.Tournee}
     * @return la {@link com.hexa17.pldagile.model.Tournee} du {@link com.hexa17.pldagile.model.Livreur}
     */
    public Tournee calculerTournee(Livreur livreur) {
        
        // Calcule l'arborescence de chaque destination
        ArrayList<Livraison> destinations = new ArrayList<Livraison>();

        Intersection entrepot = plan.obtenirEntrepot();
        Livraison livEntrepot = new Livraison(99, entrepot, livreur);

        destinations.add(livEntrepot);
        destinations.addAll(livreur.obtenirLivraisons());

        calculerArborescences(destinations);

        // Calcule le meilleur trajet

        TabuSearch tabu = new TabuSearch(destinations);
        Livraison[] ordreLivraison = tabu.soluceEnLivraisons();

        ArrayList<Segment> listeSegment = new ArrayList<Segment>();
        ArrayList<Livraison> listeLivraison = new ArrayList<Livraison>();


        // On ajoute les segments et liste de livraison dans la tournee
        for (int i = 0; i < ordreLivraison.length-1; i++) {
            listeSegment.addAll(ordreLivraison[i+1].obtenirLieu().obtenirArborescence().get(ordreLivraison[i].obtenirLieu()).obtenirChemin());
            if (i != 0) listeLivraison.add(ordreLivraison[i]);
        }

        Tournee tournee = new Tournee(listeSegment, listeLivraison);
        return tournee;
    }
}
