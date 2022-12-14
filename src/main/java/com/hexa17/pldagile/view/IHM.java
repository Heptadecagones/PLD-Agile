package com.hexa17.pldagile.view;

import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * <p>IHM class.</p>
 *
 * @author Equipe IHM
 * @version $Id: $Id
 */
public class IHM {
    
    //4 parties de l'IHM
    private Barre barre;
    private Description description;
    private Carte carte;
    private Creation fenetreCreation;

    //getters et setters des parties de l'IHM
    /**
     * <p>modifierBarre.</p>
     *
     * @param barre a {@link com.hexa17.pldagile.view.Barre} object
     */
    public void modifierBarre(Barre barre) {
        this.barre = barre;
    }

    /**
     * <p>modifierDescription.</p>
     *
     * @param description a {@link com.hexa17.pldagile.view.Description} object
     */
    public void modifierDescription(Description description) {
        this.description = description;
    }

    /**
     * <p>modifierCarte.</p>
     *
     * @param carte a {@link com.hexa17.pldagile.view.Carte} object
     */
    public void modifierCarte(Carte carte) {
        this.carte = carte;
    }

    /**
     * <p>obtenirBarre.</p>
     *
     * @return a {@link com.hexa17.pldagile.view.Barre} object
     */
    public Barre obtenirBarre() {
        return barre;
    }

    /**
     * <p>obtenirDescription.</p>
     *
     * @return a {@link com.hexa17.pldagile.view.Description} object
     */
    public Description obtenirDescription() {
        return description;
    }

    /**
     * <p>obtenirCarte.</p>
     *
     * @return a {@link com.hexa17.pldagile.view.Carte} object
     */
    public Carte obtenirCarte() {
        return carte;
    }
    /**
     * <p>obtenirFenetreCreation.</p>
     *
     * @return a {@link com.hexa17.pldagile.view.Creation} object
     */
    public Creation obtenirFenetreCreation() {
        return fenetreCreation;
    }

    //creation de l'IHM
    /**
     * <p>init.</p>
     */
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
        frame.setResizable(true);
        frame.setVisible(true);
    }

}
