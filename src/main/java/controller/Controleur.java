package controller;

import java.awt.event.*;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.PlanLivraison;
import view.IHM;

public class Controleur {

    PlanLivraison planLivraison;
    IHM view;

    public Controleur() {
        planLivraison = new PlanLivraison();
        view = new IHM();
        view.init();
        planLivraison.addObserver((Observer) view.obtenirCarte());
        planLivraison.addObserver((Observer) view.obtenirDescription());
        ActionListener c = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String command = arg0.getActionCommand();
                // System.out.println(command);
                if ("Charger".equals(command)) {
                    planLivraison.ouvrirPlan("src/main/java/largeMap.xml");
                }
                /*
                 * if ("Nouvelle livraison".equals(command)) {
                 * //plan.nouvelleLivraison(4)
                 * ;}
                 */
                if ("Creer".equals(command)) {
                    planLivraison.nouvelleLivraison(view.obtenirCarte().obtenirFenetreCreation().obtenirTextHoraire(),
                            view.obtenirCarte().obtenirFenetreCreation().obtenirIntersection(),
                            view.obtenirCarte().obtenirFenetreCreation().obtenirTextLivreur());
                    System.out.println("Creer cliqué");
                }
            }
        };

        view.obtenirBarre().obtenirCharger().addActionListener(c);
        view.obtenirCarte().obtenirFenetreCreation().obtenirBtnCreerLivraison().addActionListener(c);
    }

}
