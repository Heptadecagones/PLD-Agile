package main.java.model;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class Map extends Observable{
    String valeur;

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
        System.out.println("nouvelle valeur ="+valeur);
        this.setChanged();
        this.notifyObservers();
    }
}
