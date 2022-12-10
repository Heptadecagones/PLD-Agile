package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observer;

import javax.swing.JFileChooser;

import model.PlanLivraison;
import view.IHM;

/**
 *
 * @author Henri
 */

public class Controleur {

    public PlanLivraison planLivraison;
    public IHM view;

    /**
     * Constructeur qui initialise un plan et une vue
     */
    public Controleur() {
        planLivraison = new PlanLivraison();
        view = new IHM();
        view.init();
        planLivraison.addObserver((Observer) view.obtenirCarte());
        planLivraison.addObserver((Observer) view.obtenirDescription());
        ActionListener c = new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String command = arg0.getActionCommand();
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
                                // Init les données / Reinit les données de l'ancienne carte
                                view.obtenirCarte().initDonnee();
                                planLivraison.init();

                                planLivraison.ouvrirPlan(file.getPath());
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
                            view.obtenirCarte().obtenirFenetreCreation().obtenirTextLivreur().split(";")[0]);
                    System.out.println("Creer cliqué");
                }
            }
        };

        this.view.obtenirBarre().obtenirCharger().addActionListener(c);
        this.view.obtenirCarte().obtenirFenetreCreation()
                .obtenirBtnCreerLivraison().addActionListener(c);
    }

}
