package view;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IHM {
    // position du coin supérieur gauche de l'application
    final int POSITION_X = 50;
    final int POSITION_Y = 20;

    private Barre barre = new Barre();
    private Description description = new Description();
    private Carte carte = new Carte();

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
        /*
         * try {
         * 
         * for (LookAndFeelInfo info : UIManager.obtenirInstalledLookAndFeels()) {
         * if ("Nimbus".equals(info.obtenirName())) {
         * UIManager.modifierLookAndFeel(info.obtenirClassName());
         * break;
         * }
         * }
         * } catch (ClassNotFoundException | InstantiationException |
         * IllegalAccessException | UnsupportedLookAndFeelException ex) {
         * ex.printStackTrace();
         * }
         */

        JFrame frame = new JFrame("PLD AGILE");

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setLocation(POSITION_X, POSITION_Y);
        frame.add(barre, BorderLayout.NORTH);

        JPanel panelSud = new JPanel();
        panelSud.setLayout(new BoxLayout(panelSud, BoxLayout.LINE_AXIS));
        panelSud.add(description);
        panelSud.add(carte);
        frame.add(panelSud, BorderLayout.SOUTH);
        // frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setResizable(false);
        frame.setVisible(true);
    }

}
