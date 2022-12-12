package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
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
    private JButton charger, ajouterLivreur, sauvegarder, chargerTournee;

    // La police de toutes les composants
    private final Font font = new Font("Arial", Font.PLAIN, 16);

    public void modifierSauvegarder(JButton Sauvegarder) {
        this.sauvegarder = Sauvegarder;
    }

    public void modifierCharger(JButton Charger) {
        this.charger = Charger;
    }

    public void modifierChargerTournee(JButton chargerTournee) {
        this.chargerTournee = chargerTournee;
    }

    public void modifierAjouterLivreur(JButton ajouterLivreur) {
        this.ajouterLivreur = ajouterLivreur;
    }

    public JButton obtenirSauvegarder() {
        return sauvegarder;
    }

    public JButton obtenirCharger() {
        return charger;
    }

    public JButton obtenirChargerTournee() {
        return chargerTournee;
    }

    public JButton obtenirAjouterLivreur() {
        return ajouterLivreur;
    }

    public Barre() {
    }

    public void init() {
        // Init les boutons
        charger = creerBouton("Charger");
        ajouterLivreur = creerBouton("Nouvelle livreur"); // à toi de decider le nom Henri
        sauvegarder = creerBouton("Sauvegarder");
        chargerTournee = creerBouton("Charger une tournée");

        // Ajoute les composants
        setLayout(new GridBagLayout());
        setOpaque(false);
        setFocusable(false);

        setMaximumSize(new Dimension(80, Integer.MAX_VALUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.insets = new Insets(15,5,15, 0);
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        add(charger, gbc);
        add(ajouterLivreur, gbc);
        add(sauvegarder, gbc);
        add(chargerTournee, gbc);

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
        ajouterLivreur.addActionListener(action);
        sauvegarder.addActionListener(action);
        chargerTournee.addActionListener(action);
    }

    /**
     * Renvoie un bouton avec des propriétés de base (police, etc)
     * @param nom
     * @return JButton
     */
    public JButton creerBouton(String nom) {
        JButton bouton = new JButton(nom);
        bouton.setFont(font);
        bouton.setFocusable(false);
        bouton.setFocusPainted(false);
        return bouton;
    }
}