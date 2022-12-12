package view;

import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Equipe IHM
 */

public class IHM {
    
    //4 parties de l'IHM
    private Barre barre;
    private Description description;
    private Carte carte;
    private Creation fenetreCreation;

    //getters et setters des parties de l'IHM
    public void modifierBarre(Barre barre) {
        this.barre = barre;
    }

    public void modifierDescription(Description description) {
        this.description = description;
    }

    public void modifierCarte(Carte carte) {
        this.carte = carte;
    }

    public Barre obtenirBarre() {
        return barre;
    }

    public Description obtenirDescription() {
        return description;
    }

    public Carte obtenirCarte() {
        return carte;
    }
    public Creation obtenirFenetreCreation() {
        return fenetreCreation;
    }

    //creation de l'IHM
    public void init() {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        
        JFrame frame = new JFrame("PLD AGILE");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(0, 0, (int)tailleEcran.getWidth(), (int)tailleEcran.getHeight());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        fenetreCreation = new Creation();
        fenetreCreation.init();
        
        barre = new Barre();
        barre.init();
        panel.add(barre);

        carte = new Carte();
        panel.add(carte);

        description = new Description();
        description.init();
        panel.add(description);
        
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}