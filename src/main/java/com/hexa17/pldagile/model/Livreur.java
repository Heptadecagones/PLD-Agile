package com.hexa17.pldagile.model;

import java.util.ArrayList;

/**
 * <p>Livreur classe.</p>
 *
 * @author Yannick
 * @version $Id: $Id
 */
public class Livreur {

    private int id;
    private boolean disponibilite;
    private ArrayList<Livraison> livraisons;
    private Tournee tournee;
    private String nom;

    /**
     * <p>Constructeur pour Livreur.</p>
     *
     * @param id un int
     * @param nom un {@link java.lang.String} objet
     */
    public Livreur(int id,String nom) {
        this.id = id;
        this.disponibilite = true;
        this.livraisons = new ArrayList<Livraison>();
        this.tournee = new Tournee();
        this.nom=nom;
    }

    /**
     * <p>Constructeur pour Livreur.</p>
     *
     * @param id un int
     * @param nom un {@link java.lang.String} objet
     * @param disponbilite un boolean
     */
    public Livreur(int id,String nom, boolean disponibilite) {
        this.id = id;
        this.disponibilite = disponibilite;
        this.livraisons = new ArrayList<Livraison>();
        this.tournee = new Tournee();
        this.nom=nom;
    }

    /**
     * <p>Accesseur Id.</p>
     *
     * @return un int
     */
    public int obtenirId() {
        return id;
    }
    /**
     * <p>Accesseur nom.</p>
     *
     * @return un {@link java.lang.String} objet
     */
    public String obtenirNom() {
        return nom;
    }
    /**
     * <p>Modificateur nom.</p>
     *
     * @param nom un {@link java.lang.String} objet
     */
    public void modifierNom(String nom) {
        this.nom=nom;
    }
    /**
     * <p>modificateur id.</p>
     *
     * @param id un int
     */
    public void modifierId(int id) {
        this.id = id;
    }

    /**
     * <p>Accesseur disponibilité.</p>
     *
     * @return un boolean
     */
    public boolean estDisponible() {
        return disponibilite;
    }

    /**
     * <p>Modificateur disponibilité.</p>
     *
     * @param disponbilite un boolean
     */
    public void modifierDisponibilite(boolean disponbilite) {
        this.disponibilite = disponbilite;
    }

    /**
     * <p>Accesseur livraisons.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Livraison> obtenirLivraisons() {
        return livraisons;
    }

    /**
     * <p>Modificateur livraisons.</p>
     *
     * @param livraisons un {@link java.util.ArrayList} objet
     */
    public void modifierLivraisons(ArrayList<Livraison> livraisons) {
        this.livraisons = livraisons;
    }

    
    /**
     * <p>Accesseur tournée.</p>
     *
     * @return un {@link com.hexa17.pldagile.model.Tournee} objet
     */
    public Tournee obtenirTournee() {
        return tournee;
    }

    /**
     * <p>Modificateur tournée.</p>
     *
     * @param tournee un {@link com.hexa17.pldagile.model.Tournee} objet 
     */
    public void modifierTournee(Tournee tournee) {
        this.tournee = tournee;
    }

    /**
     * <p>toString.</p>
     *
     * @return un {@link java.lang.String} objet
     */
    public String toString() {
        String description = "id: " + this.id + " dispo: " + this.disponibilite + " ";
        for (Livraison l : this.livraisons) {
            description += " livraison: " + l;
        }
        return description;
    }
}
