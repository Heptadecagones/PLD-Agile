package com.hexa17.pldagile.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * <p>Barre class.</p>
 *
 * @author Equipe IHM
 * @version $Id: $Id
 */

@SuppressWarnings("serial")
public class Barre extends JPanel {
    /**
     * Tous les composants
     */
    private JButton charger, ajouterLivreur, sauvegarder, chargerTournee;

    // La police de toutes les composants
    private final Font font = new Font("Arial", Font.PLAIN, 16);

    /**
     * <p>modifierSauvegarder.</p>
     *
     * @param Sauvegarder a {@link javax.swing.JButton} object
     */
    public void modifierSauvegarder(JButton Sauvegarder) {
        this.sauvegarder = Sauvegarder;
    }

    /**
     * <p>modifierCharger.</p>
     *
     * @param Charger a {@link javax.swing.JButton} object
     */
    public void modifierCharger(JButton Charger) {
        this.charger = Charger;
    }

    /**
     * <p>modifierChargerTournee.</p>
     *
     * @param chargerTournee a {@link javax.swing.JButton} object
     */
    public void modifierChargerTournee(JButton chargerTournee) {
        this.chargerTournee = chargerTournee;
    }

    /**
     * <p>modifierAjouterLivreur.</p>
     *
     * @param ajouterLivreur a {@link javax.swing.JButton} object
     */
    public void modifierAjouterLivreur(JButton ajouterLivreur) {
        this.ajouterLivreur = ajouterLivreur;
    }

    /**
     * <p>obtenirSauvegarder.</p>
     *
     * @return a {@link javax.swing.JButton} object
     */
    public JButton obtenirSauvegarder() {
        return sauvegarder;
    }

    /**
     * <p>obtenirCharger.</p>
     *
     * @return a {@link javax.swing.JButton} object
     */
    public JButton obtenirCharger() {
        return charger;
    }

    /**
     * <p>obtenirChargerTournee.</p>
     *
     * @return a {@link javax.swing.JButton} object
     */
    public JButton obtenirChargerTournee() {
        return chargerTournee;
    }

    /**
     * <p>obtenirAjouterLivreur.</p>
     *
     * @return a {@link javax.swing.JButton} object
     */
    public JButton obtenirAjouterLivreur() {
        return ajouterLivreur;
    }

    /**
     * <p>Constructor for Barre.</p>
     */
    public Barre() {
    }

    /**
     * <p>init.</p>
     */
    public void init() {
        // Init les boutons
        charger = creerBouton("Charger");
        ajouterLivreur = creerBouton("Nouveau livreur"); // à toi de decider le nom Henri
        sauvegarder = creerBouton("Sauvegarder");
        chargerTournee = creerBouton("Charger une tournée");

        // Ajoute les composants
        setLayout(new GridBagLayout());
        setOpaque(false);

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
    }

    /**
     * Renvoie un bouton avec des propriétés de base (police, etc)
     *
     * @param nom a {@link java.lang.String} object
     * @return JButton
     */
    public JButton creerBouton(String nom) {
        JButton bouton = new JButton(nom);
        bouton.setFont(font);
        bouton.setFocusPainted(false);
        return bouton;
    }
}
