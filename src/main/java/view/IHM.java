package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;

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

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setBounds(0, 0, (int)tailleEcran.getWidth(), (int)tailleEcran.getHeight());

        barre = new Barre();
        frame.add(barre, BorderLayout.NORTH);

        JPanel panelSud = new JPanel();
        panelSud.setLayout(new BoxLayout(panelSud, BoxLayout.X_AXIS));
        description = new Description();

        int min = Math.min((int)tailleEcran.getWidth(), (int)tailleEcran.getHeight());
        carte = new Carte(9*min/10, 9*min/10);
        
        panelSud.add(description);
        panelSud.add(carte);

        /*frame.addComponentListener(new ComponentAdapter() {  
            public void componentResized(ComponentEvent evt) {
                //System.out.println(frame.getWidth() + " " + frame.getHeight());
                int largeur = frame.getWidth();
                int hauteur = frame.getHeight();
                int min = Math.min(largeur, hauteur);
                carte.setPreferredSize(new Dimension(9*min/10, 9*min/10));

                frame.revalidate();
            }
        });*/

        frame.add(panelSud, BorderLayout.SOUTH);
        //frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }

}
