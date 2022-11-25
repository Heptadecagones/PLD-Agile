package com.rpieniazek.tabu;

public class TabuWrapper {

    private final Matrix matrice; // Matrice d'adjacence du graphe
    private int nbLivreurs; // Nombre de livreurs
    
    public TabuWrapper() {
        this.matrice = null; //TODO convertir XML en matrice
        this.nbLivreurs = 1;
    }

    //TODO: Changer le type de retour en une liste de segments
    public void RechercheContrainte() {
    }
}
