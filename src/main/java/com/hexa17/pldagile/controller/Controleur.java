package com.hexa17.pldagile.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observer;

import javax.swing.JFileChooser;

import com.hexa17.pldagile.model.PlanLivraison;
import com.hexa17.pldagile.view.IHM;

/**
 *
 * @author Henri
 */

public class Controleur {

    PlanLivraison planLivraison;
    IHM view;

    /**
     * Constructeur qui initialise un plan et une vue
     */
    public Controleur() {
        view = new IHM();
        view.init();
        planLivraison = new PlanLivraison();
        planLivraison.addObserver((Observer) view.obtenirCarte());
        planLivraison.addObserver((Observer) view.obtenirDescription());
        ActionListener c = new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String command = arg0.getActionCommand();
                if ("Charger".equals(command)) {
                    JFileChooser selecteur = new JFileChooser("." + File.separator + "..");

                    // adapter le chemin vers les fichier XML
                    String cheminXML = File.separator + "data";
                    File repertoireProjet = new File(System.getProperty("user.dir") + cheminXML);
                    selecteur.setCurrentDirectory(repertoireProjet);

                    if (selecteur.showOpenDialog(view.obtenirBarre().obtenirCharger()) == JFileChooser.APPROVE_OPTION) {
                        File file = selecteur.getSelectedFile();
                        if (file.exists()) {
                            if (file.getName().endsWith(".xml")) {
                                planLivraison.initPlan(file.getPath());
                            } else {
                                System.out.println("Pas un fichier xml");
                            }
                        } else {
                            System.out.println("Fichier n'existe pas");
                        }
                    }
                }
                if ("Creer".equals(command)) {
                    planLivraison.nouvelleLivraison(view.obtenirCarte().obtenirFenetreCreation().obtenirTextHoraire(),
                            view.obtenirCarte().obtenirFenetreCreation().obtenirIntersection(),
                            Integer.parseInt(view.obtenirCarte().obtenirFenetreCreation().obtenirTextLivreur()));
                    System.out.println("Creer cliqué");
                }
            }
        };

        this.view.obtenirBarre().obtenirCharger().addActionListener(c);
        this.view.obtenirCarte().obtenirFenetreCreation()
                .obtenirBtnCreerLivraison().addActionListener(c);
    }

}
