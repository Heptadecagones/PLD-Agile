package com.hexa17.pldagile.model;

import java.util.ArrayList;

/**
 * <p>Livreur class.</p>
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
     * <p>Constructor for Livreur.</p>
     *
     * @param id a int
     * @param nom a {@link java.lang.String} object
     */
    public Livreur(int id,String nom) {
        this.id = id;
        this.disponibilite = true;
        this.livraisons = new ArrayList<Livraison>();
        this.tournee = new Tournee();
        this.nom=nom;
    }

    /**
     * <p>Constructor for Livreur.</p>
     *
     * @param id a int
     * @param nom a {@link java.lang.String} object
     * @param disponbilite a boolean
     */
    public Livreur(int id,String nom, boolean disponbilite) {
        this.id = id;
        this.disponibilite = disponibilite;
        this.livraisons = new ArrayList<Livraison>();
        this.tournee = new Tournee();
        this.nom=nom;
    }

    /**
     * <p>obtenirId.</p>
     *
     * @return a int
     */
    public int obtenirId() {
        return id;
    }
    /**
     * <p>obtenirNom.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String obtenirNom() {
        return nom;
    }
    /**
     * <p>modifierNom.</p>
     *
     * @param nom a {@link java.lang.String} object
     */
    public void modifierNom(String nom) {
        this.nom=nom;
    }
    /**
     * <p>modifierId.</p>
     *
     * @param id a int
     */
    public void modifierId(int id) {
        this.id = id;
    }

    /**
     * <p>estDisponible.</p>
     *
     * @return a boolean
     */
    public boolean estDisponible() {
        return disponibilite;
    }

    /**
     * <p>modifierDisponibilite.</p>
     *
     * @param disponbilite a boolean
     */
    public void modifierDisponibilite(boolean disponbilite) {
        this.disponibilite = disponbilite;
    }

    /**
     * <p>obtenirLivraisons.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Livraison> obtenirLivraisons() {
        return livraisons;
    }

    /**
     * <p>modifierLivraisons.</p>
     *
     * @param livraisons a {@link java.util.ArrayList} object
     */
    public void modifierLivraisons(ArrayList<Livraison> livraisons) {
        this.livraisons = livraisons;
    }

    /**
     * <p>modifierTournee.</p>
     *
     * @param tournee a {@link com.hexa17.pldagile.model.Tournee} object
     */
    public void modifierTournee(Tournee tournee) {
        this.tournee = tournee;
    }

    /**
     * <p>toString.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String toString() {
        String description = "id: " + this.id + " dispo: " + this.disponibilite + " ";
        for (Livraison l : this.livraisons) {
            description += " livraison: " + l;
        }
        return description;
    }

    /**
     * <p>obtenirTournee.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Tournee} object
     */
    public Tournee obtenirTournee() {
        return tournee;
    }
}
