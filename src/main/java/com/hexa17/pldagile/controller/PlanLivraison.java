package com.hexa17.pldagile.controller;

import java.util.Observable;
import java.util.ArrayList;

import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Livraison;
import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.Plan;
import com.hexa17.pldagile.model.PlanUsine;
import com.hexa17.pldagile.model.Tournee;
import com.hexa17.pldagile.model.algo.Solveur;

/**
 * <p>
 * PlanLivraison classe.
 * </p>
 *
 * @author Henri
 *         Voir diagramme des classes
 *         Classe avec un Plan et une liste de Livreur, permet de faire le lien
 *         entre le plan et les livraisons
 * @version $Id: $Id
 */
public class PlanLivraison extends Observable {

    private ArrayList<Livreur> listeLivreur;
    private Plan plan;

    /**
     * <p>initialisation des livreurs du plan.</p>
     */
    public void init() {
        this.plan = null;
        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0, "Jean Titi"));
        this.listeLivreur.add(new Livreur(1, "Hervé Lapin"));
        this.listeLivreur.add(new Livreur(2, "Jeanne-Annick Al-Pohou"));
        this.listeLivreur.add(new Livreur(3, "Bertrand Turpin"));
    }

    /**
     * <p>Constructeur pour PlanLivraison.</p>
     */
    public PlanLivraison() {
        init();
    }

    /**
     * <p>Constructeur pour PlanLivraison.</p>
     *
     * @param cheminXml un {@link java.lang.String} objet
     */
    public PlanLivraison(String cheminXml) {
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml, false);
        this.plan = pf.construirePlan();

        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0, "Jean Jean"));
        this.listeLivreur.add(new Livreur(1, "Hervé Lapin"));
        this.listeLivreur.add(new Livreur(2, "Jeanne-Annick Al-Pohou"));
        this.listeLivreur.add(new Livreur(3, "Bertrand Turpin"));

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>
     * initPlan.
     * </p>
     *
     * @param cheminXml un {@link java.lang.String} objet
     */

    // Initialisation du plan lorsqu'un fichier est chargé
    public void initPlan(String cheminXml) {

        // Réinitialisation des livreurs
        init();

        // Récupération des données du fichier XML
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml, false);

        // Construction du plan
        this.plan = pf.construirePlan();

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>Accesseur ListeLivreur.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Livreur> obtenirListeLivreur() {
        return this.listeLivreur;
    }

    /**
     * <p>Accesseur Plan.</p>
     *
     * @return un {@link com.hexa17.pldagile.model.Plan} objet
     */
    public Plan obtenirPlan() {
        return this.plan;
    }

    /**
     * <p>Création d'une nouvelle livraison.</p>
     *
     * @param horaire un {@link java.lang.String} objet
     * @param intersection un {@link com.hexa17.pldagile.model.Intersection} objet
     * @param numLivreur un {@link java.lang.String} objet
     */
    public void nouvelleLivraison(String horaire, Intersection intersection, String numLivreur) {

        // Création de la nouvelle livraison et attribution de celle-ci au livreur
        Livreur livreurActuel = this.listeLivreur.get(Integer.parseInt(numLivreur));
        Livraison nouvelleLivraison = new Livraison(Integer.parseInt(horaire), intersection, livreurActuel);
        livreurActuel.obtenirLivraisons().add(nouvelleLivraison);

        // Calcul de la nouvele tournée du livreur
        Tournee tournee = new Tournee();
        Solveur solveur = new Solveur(plan);
        tournee = solveur.calculerTournee(livreurActuel);
        livreurActuel.modifierTournee(tournee);

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>Suppression d'une livraison.</p>
     *
     * @param livraison un {@link com.hexa17.pldagile.model.Livraison} objet
     */
    public void supprimerLivraison(Livraison livraison) {

        // Test si la livraison existe bien
        if (livraison == null)
            return;

        Livreur livreur = livraison.obtenirLivreur();
        Tournee tournee = new Tournee();

        // On retire la livraison de la liste de livraison du livreur
        livreur.obtenirLivraisons().remove(livraison);
        livraison.modifierLivreur(null);

        // Calcul de la tournée si la liste de livraison du livreur n'est pas vide
        if (!livreur.obtenirLivraisons().isEmpty()) {
            Solveur solveur = new Solveur(plan);
            tournee = solveur.calculerTournee(livreur);
        }
        livreur.modifierTournee(tournee);

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>Ajout d'un livreur.</p>
     *
     * @param l un {@link com.hexa17.pldagile.model.Livreur} objet
     */
    public void ajouterLivreur(Livreur l) {
        this.listeLivreur.add(l);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>Charger un fichier enregistré.</p>
     *
     * @param cheminXml un {@link java.lang.String} objet
     */

     public void chargerLivraison(String cheminXml) {

        // Récupération des données
        PlanUsine pf = new PlanUsine();
        this.listeLivreur = pf.chargerXML(cheminXml, true);

        // Construction du plan
        this.plan = pf.construirePlan();

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>sauvegarder dans un fichier l'état du plan.</p>
     *
     * @param nomFichier un {@link java.lang.String} objet
     */

     public void sauvegarder(String nomFichier) {
        PlanUsine pf = new PlanUsine();
        pf.sauvegarderPlan(nomFichier, listeLivreur, plan);
     }
    
}
