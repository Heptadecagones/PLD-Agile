package controller;

import java.awt.event.*;
import java.io.File;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Plan;
import view.IHM;

public class Controleur {

    Plan plan;
    IHM view;

    public Controleur() {
        plan = new Plan();
        view = new IHM();
        view.init();
        plan.addObserver((Observer) view.obtenirCarte());
        plan.addObserver((Observer) view.obtenirDescription());
        ActionListener c = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String command = arg0.getActionCommand();
                // System.out.println(command);
                if ("Charger".equals(command)) {
                    JFileChooser selecteur = new JFileChooser();

                    // adapter le chemin vers les fichier XML 
                    String cheminXML = File.separator + "src" + File.separator + "main" + File.separator + "java";
                    File repertoireProjet = new File(System.getProperty("user.dir") + cheminXML);
                    selecteur.setCurrentDirectory(repertoireProjet);

                    if (selecteur.showOpenDialog(view.obtenirBarre().obtenirCharger()) == JFileChooser.APPROVE_OPTION) {
                        File file = selecteur.getSelectedFile();
                        if (file.exists()) {
                            if (file.getName().endsWith(".xml")) {
                                plan.chargerXML(file.getPath());
                            } else {
                                System.out.println("Pas un fichier xml");
                            }
                        } else {
                            System.out.println("Fichier n'existe pas");
                        }
                    }
                }
                /*
                 * if ("Nouvelle livraison".equals(command)) {
                 * //plan.nouvelleLivraison(4)
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

        view.obtenirBarre().obtenirCharger().addActionListener(c);
        view.obtenirCarte().obtenirFenetreCreation().obtenirBtnCreerLivraison().addActionListener(c);
    }

}
