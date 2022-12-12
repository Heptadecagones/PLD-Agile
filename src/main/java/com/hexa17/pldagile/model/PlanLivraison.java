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
        Tournee tournee = new Tournee();
        Solveur s = new Solveur(plan, livreurActuel);
        tournee = s.calculerTournee();
        
        this.listeLivreur.get(numLivreur).modifierTournee(tournee);
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
