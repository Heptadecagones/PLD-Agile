package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Equipe IHM
 */

public class IHM {
    // position du coin supérieur gauche de l'application

    private Barre barre;
    private Description description;
    private Carte carte;

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

    public void init() {
        JFrame frame = new JFrame("PLD AGILE");

        //height of the task bar
        //Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        //int taskBarSize = scnMax.bottom;


        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setBounds(0, 0, (int)tailleEcran.getWidth(), (int)tailleEcran.getHeight());

        barre = new Barre();
        frame.add(barre, BorderLayout.NORTH);

        JPanel panelSud = new JPanel();
        panelSud.setLayout(new BoxLayout(panelSud, BoxLayout.X_AXIS));
        description = new Description();
        description.init();
        panelSud.add(description);

        int min = Math.min((int)tailleEcran.getWidth(), (int)tailleEcran.getHeight());
        carte = new Carte(9*min/10, 9*min/10);
        panelSud.add(carte);

        frame.add(panelSud, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}