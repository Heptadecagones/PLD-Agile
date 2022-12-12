package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observer;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import model.Intersection;
import model.Livraison;
import model.Livreur;
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
                    //view.obtenirDescription().modifierTitle("Chargement");
                    
                    planLivraison.nouvelleLivraison(view.obtenirFenetreCreation().obtenirTextHoraire(),
                            view.obtenirFenetreCreation().obtenirIntersection(),
                            view.obtenirFenetreCreation().obtenirTextLivreur().split(";")[0]);
                    System.out.println("Creer cliqué");
                    view.obtenirDescription().modifierTitle("Fin");
                }
                if("Sauvegarder".equals(command)){
                    String nom = JOptionPane.showInputDialog(new JFrame(), "Nommer fichier", null);
                    planLivraison.sauvegarder(nom);
                    System.out.println("sauvegarder cliqué");
                }



                if("Charger une tournée".equals(command)){
                    
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

                                planLivraison.chargerLivraison(file.getPath());
                            } else {
                                System.out.println("Pas un fichier xml");
                            }
                        } else {
                            System.out.println("Fichier n'existe pas");
                        }
                    }

                }
                if("Nouveau livreur".equals(command)){
                    String nom = JOptionPane.showInputDialog(new JFrame(), "Entrer nom Livreur");
                    planLivraison.ajouterLivreur(new Livreur(planLivraison.obtenirListeLivreur().size(),nom));
                    //System.out.println("nouveau  livreur cliqué");
                }
            }
        };
        
        MouseListener m = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.obtenirFenetreCreation().modifierlivreurs(planLivraison.obtenirListeLivreur());
                Intersection choixIntersection=view.obtenirCarte().carteCliquee(e);
                if (choixIntersection.obtenirId() != null) {
                    view.obtenirFenetreCreation().setIntersection(choixIntersection);
                    view.obtenirDescription().surlignerLivraison(choixIntersection);
                    for (Livreur livr : planLivraison.obtenirListeLivreur()) {
                        for (Livraison s : livr.obtenirLivraisons()) {
                            if(s.obtenirLieu()==choixIntersection){
                                view.obtenirCarte().modifierLivraisonClickee(s);
                            }
                        }
                    }
                    view.obtenirFenetreCreation().ouvrir();
                    view.obtenirCarte().repaint();

                }
            }
            @Override
            public void mouseEntered(MouseEvent arg0) {
            }
            @Override
            public void mouseExited(MouseEvent arg0) {
            }
            @Override
            public void mousePressed(MouseEvent arg0) {   
            }
            @Override
            public void mouseReleased(MouseEvent arg0) {  
            }
        };


        this.view.obtenirBarre().obtenirCharger().addActionListener(c);
        this.view.obtenirFenetreCreation()
                .obtenirBtnCreerLivraison().addActionListener(c);
        this.view.obtenirCarte()
                .addMouseListener(m);
        this.view.obtenirBarre().obtenirSauvegarder().addActionListener(c);
        this.view.obtenirBarre().obtenirChargerTournee().addActionListener(c);
        this.view.obtenirBarre().obtenirAjouterLivreur().addActionListener(c);

        this.view.obtenirDescription().obtenirBtnList().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                int index = list.locationToIndex(evt.getPoint());
                System.out.println(index);
                System.out.println("test+ "+view.obtenirDescription().obtenirBtnList().getSelectedValue());
                view.obtenirCarte().modifierLivraisonClickee((Livraison)view.obtenirDescription().obtenirBtnList().getSelectedValue());
                view.obtenirCarte().repaint();
            }
        });
    }


}
