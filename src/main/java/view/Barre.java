package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Equipe IHM
 */

@SuppressWarnings("serial")
public class Barre extends JPanel {
    /**
     * Tous les composants
     */
    private JButton sauvegarder, charger;

    // La police de toutes les composants
    private final Font font = new Font("Arial", Font.PLAIN, 14);

    public void modifierSauvegarder(JButton Sauvegarder) {
        this.sauvegarder = Sauvegarder;
    }

    public void modifierCharger(JButton Charger) {
        this.charger = Charger;
    }

    public JButton obtenirSauvegarder() {
        return sauvegarder;
    }

    public JButton obtenirCharger() {
        return charger;
    }

    public Barre() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setOpaque(false);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Init les boutons
        charger = creerBouton("Charger");
        sauvegarder = creerBouton("Sauvegarder");

        // Ajoute les composants
        JPanel panelGauche = new JPanel(), panelDroit = new JPanel();
        panelGauche.add(charger);
        add(panelGauche);

        add(Box.createHorizontalGlue());
        panelDroit.add(sauvegarder);
        add(panelDroit);

        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() == charger) {
                    System.out.println("Charger cliqué");
                }

                /*
                 * if (evt.getSource() == nouvelleLivraison) {
                 * System.out.println("nouvelleLivraison cliqué");
                 * fenetreCreation.ouvrir();
                 * }
                 */

                if (evt.getSource() == sauvegarder) {
                    System.out.println("Sauvegarder cliqué");
                }
            }
        };

        charger.addActionListener(action);
        sauvegarder.addActionListener(action);
    }

    /**
     * Renvoie un bouton avec des propriétés de base (police, etc)
     * @param nom
     * @return JButton
     */
    public JButton creerBouton(String nom) {
        JButton bouton = new JButton(nom);
        bouton.setFont(font);
        bouton.setFocusPainted(false);
        return bouton;
    }
}
