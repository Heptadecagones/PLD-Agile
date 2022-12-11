package com.hexa17.pldagile.model;

import java.util.Observable;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.hexa17.pldagile.model.algo.Graphe;
import com.hexa17.pldagile.model.algo.Lien;
import com.hexa17.pldagile.model.algo.Noeud;
import com.hexa17.pldagile.model.algo.Solveur;
import com.hexa17.pldagile.model.algo.tabu.TabuSearch;

/**
 *
 * @author Henri
 *         Voir diagramme des classes
 *         Classe avec un Plan et une liste de Livreur, permet de faire le lien
 *         entre le plan et les livraisons
 */
public class PlanLivraison extends Observable {

    private ArrayList<Livreur> listeLivreur;
    private Plan plan;
    private Solveur solveur;

    // AJOUT DUNE LIVRAISON, METHODE APPELEE PAR LE CONTROLLEUR
    public void nouvelleLivraison(String horaire, Intersection intersection, int numLivreur) {
        Livreur livreurActuel = this.listeLivreur.get(numLivreur);
        Livraison nouvelleLivraison = new Livraison(Integer.parseInt(horaire), intersection,
                livreurActuel);
        this.listeLivreur.get(numLivreur).obtenirLivraisons().add(nouvelleLivraison);
        Tournee t = new Tournee();
        Solveur s = new Solveur(plan, livreurActuel);
        // Calculer l'arborescence de la nouvelle livraison
        s.calculerArborescenceDepuisNoeud(intersection);
        // Calcule le graphe simplifié
        ArrayList<Livraison> livraisons = listeLivreur.get(numLivreur).obtenirLivraisons();
        Set<Noeud> noeuds = new HashSet<>();
        int minLiv = 99;

        for (Livraison liv : livraisons) {
            Noeud noeud = liv.obtenirLieu();
            noeuds.add(noeud);

            if (noeud.obtenirHoraireLivraison() < minLiv)
                minLiv = noeud.obtenirHoraireLivraison();
        }
        Noeud entrepot = plan.obtenirEntrepot();
        entrepot.modifierHoraireLivraison(99);
        noeuds.add(entrepot);
        Graphe grapheSimplifie = new Graphe(noeuds);
        TabuSearch tabu = new TabuSearch(grapheSimplifie, minLiv);
        Noeud[] ordreLivraison = tabu.soluceEnNoeuds();

        ArrayList<Segment> tournee = new ArrayList<Segment>();

        // On ajoute les segments dans la tournee
        for (int i = 0; i < ordreLivraison.length - 1; i++) {
            tournee.addAll(ordreLivraison[i + 1].obtenirArborescence().get(ordreLivraison[i]).obtenirChemin());
        }

        // Minimalisation du graphe
        /*
         * for(Livraison liv : livraisons) {
         * for(Livraison liv2 : livraisons) {
         * if(!liv.equals(liv2)) {
         * if(liv.obtenirPlageHoraire() > liv2.obtenirPlageHoraire()) {
         * grapheMinimal.get(liv.obtenirLieu()).remove(liv2.obtenirLieu());
         * }
         * else {
         * if(grapheMinimal.get(liv.obtenirLieu()).containsKey(plan.obtenirEntrepot()))
         * {
         * grapheMinimal.get(liv.obtenirLieu()).remove(plan.obtenirEntrepot());
         * }
         * }
         * }
         * }
         * }
         */

        // Appeler TabuSearch
        this.listeLivreur.get(numLivreur).obtenirTournee()
                .modifierListeSegment(t.obtenirListeSegment());
        this.setChanged();
        this.notifyObservers();
    }

    public PlanLivraison() {
        this.plan = null;
        this.solveur = null;
        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0));
        this.listeLivreur.add(new Livreur(1));
        this.listeLivreur.add(new Livreur(2));
        this.listeLivreur.add(new Livreur(3));
    }

    public void initPlan(String cheminXml) {
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml);
        this.plan = pf.construirePlan();
        //FIXME comment avoir le plan ici, on passe le plan en paramètre?? - Thibaut
        this.solveur = new Solveur(plan);

        this.setChanged();
        this.notifyObservers();

        solveur.calculerArborescenceDepuisNoeud(this.plan.obtenirEntrepot());
    }

    public PlanLivraison(String cheminXml) {
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml);
        this.plan = pf.construirePlan();
        this.setChanged();
        this.notifyObservers();

        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0));
        this.listeLivreur.add(new Livreur(1));
        this.listeLivreur.add(new Livreur(2));
        this.listeLivreur.add(new Livreur(3));

        //FIXME Même chose qu'au-dessus
        this.solveur = new Solveur(this.plan);
        // On calcul l'arborescence de l'entrepôt avant d'ajouter des livraisons
        solveur.calculerArborescenceDepuisNoeud(this.plan.obtenirEntrepot());
    }

    public ArrayList<Livreur> obtenirListeLivreur() {
        return this.listeLivreur;
    }

    public Plan obtenirPlan() {
        return this.plan;
    }
}
