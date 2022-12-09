package com.hexa17.pldagile.model;

import java.util.Observable;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hexa17.pldagile.model.algo.Lien;
import com.hexa17.pldagile.model.algo.Noeud;
import com.hexa17.pldagile.model.algo.Solveur;

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
    public void nouvelleLivraison(String horaire, Intersection intersection, int numLivreur) {
        Livraison nouvelleLivraison = new Livraison(Integer.parseInt(horaire), intersection,
                this.listeLivreur.get(numLivreur));
        this.listeLivreur.get(numLivreur).obtenirLivraisons().add(nouvelleLivraison);
        Tournee t = new Tournee();
        Solveur s = new Solveur(plan);
        // Calculer l'arborescence de la nouvelle livraison
        s.calculerArborescenceDepuisNoeud(intersection);
        // Calcule le graphe simplifié
        ArrayList<Livraison> livraisons = listeLivreur.get(numLivreur).obtenirLivraisons();
        ArrayList<Noeud> intersections = valueGrabber(livraisons, liv -> liv.obtenirLieu());
        intersections.add(plan.obtenirEntrepot());
        Map<Noeud, Map<Noeud, Lien>> grapheMinimal = s.calculerGrapheSimplifie(intersections);
        // Minimalisation du graphe
        /*for(Livraison liv : livraisons) {
            for(Livraison liv2 : livraisons) {
                if(!liv.equals(liv2)) {
                    if(liv.obtenirPlageHoraire() > liv2.obtenirPlageHoraire()) {
                        grapheMinimal.get(liv.obtenirLieu()).remove(liv2.obtenirLieu());
                    }
                    else {
                        if(grapheMinimal.get(liv.obtenirLieu()).containsKey(plan.obtenirEntrepot())) {
                            grapheMinimal.get(liv.obtenirLieu()).remove(plan.obtenirEntrepot());
                        }
                    }
                }
            }
        }*/
        
        // Appeler TabuSearch
        this.listeLivreur.get(numLivreur).obtenirTournee()
                .modifierListeSegment(t.obtenirListeSegment());
        this.setChanged();
        this.notifyObservers();
    }

    private static <C, T> ArrayList<T> valueGrabber(List<C> items, Function<C, T> func) {
        return (ArrayList<T>) items.stream().map(func).collect(Collectors.toList());
    }

    public PlanLivraison(String xml) {
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(xml);
        this.setChanged();
        this.notifyObservers();
        this.plan = pf.construirePlan();

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
