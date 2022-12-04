package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import model.Plan;
import view.IHM;

/**
 *
 * @author Henri
 */

public class Controleur {

    Plan plan;
    IHM view;

    /**
     * Constructeur qui initialise un plan et une vue
     */
    public Controleur() {

        this.plan = new Plan();
        this.view = new IHM();
        this.view.init();

        this.plan.addObserver((Observer) this.view.obtenirCarte());
        this.plan.addObserver((Observer) this.view.obtenirDescription());

        ActionListener c = new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String command = arg0.getActionCommand();
                // System.out.println(command);
                if ("Charger".equals(command)) {
                    plan.chargerXML("src/main/java/mediumMap.xml");
                }
                /*
                 * if ("Nouvelle livraison".equals(command)) {
                 * plan.nouvelleLivraison(4)
                 * ;}
                 */
                if ("Creer".equals(command)) {
                    plan.nouvelleLivraison(view.obtenirCarte().obtenirFenetreCreation().obtenirTextHoraire(),
                            view.obtenirCarte().obtenirFenetreCreation().obtenirIntersection(),
                            view.obtenirCarte().obtenirFenetreCreation().obtenirTextLivreur());
                    System.out.println("Creer cliqué");
                }
            }
        };

        this.view.obtenirBarre().obtenirCharger().addActionListener(c);
        this.view.obtenirCarte().obtenirFenetreCreation()
                .obtenirBtnCreerLivraison().addActionListener(c);
    }

}
