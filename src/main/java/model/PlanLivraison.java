package model;

import java.util.Observable;
import java.util.ArrayList;
import model.algo.FacadeAlgoTournee;

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

    // AJOUT DUNE LIVRAISON, METHODE APPELEE PAR LE CONTROLLEUR
    public void nouvelleLivraison(String horaire, Intersection intersection, String numLivreur) {
        Livraison nouvelleLivraison = new Livraison(Integer.parseInt(horaire), intersection,
                this.listeLivreur.get(Integer.parseInt(numLivreur)));
        this.listeLivreur.get(Integer.parseInt(numLivreur)).obtenirLivraisons().add(nouvelleLivraison);
        // System.out.println("Livreur:" +
        // this.listeLivreur.get(Integer.parseInt(numLivreur)).toString());
        // TODO: remplacer l'appel de DijkstraAlgo par un appel de FacadeAlgoTournee
        // DijkstraAlgo dijal = new DijkstraAlgo(this,
        // this.listeLivreur.get(Integer.parseInt(numLivreur)));
        Tournee t = new Tournee();
        try {
            t = FacadeAlgoTournee.calculerTournee(
                    plan, this.listeLivreur.get(Integer.parseInt(numLivreur)));
            // System.out.println("test\n:" + t.toString());
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace();
        }
        this.listeLivreur.get(Integer.parseInt(numLivreur)).obtenirTournee()
                .modifierListeSegment(t.obtenirListeSegment());
        this.setChanged();
        this.notifyObservers();
    }

    public void ouvrirPlan(String XML) {
        this.plan.chargerXML(XML);
        this.setChanged();
        this.notifyObservers();
    }

    public PlanLivraison() {
        this.plan = new Plan();
        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0));
        this.listeLivreur.add(new Livreur(1));
        this.listeLivreur.add(new Livreur(2));
        this.listeLivreur.add(new Livreur(3));
    }

    public ArrayList<Livreur> obtenirListeLivreur() {
        return this.listeLivreur;
    }

    public Plan obtenirPlan() {
        return this.plan;
    }
}
