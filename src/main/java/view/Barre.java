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
    private JButton zoom,dezoom;
    private int LARGEUR;
    private int LONGUEUR;


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
    public JButton obtenirZoom() {
        return zoom;
    }
    public JButton obtenirDezoom() {
        return dezoom;
    }
    public Barre(int LARGEUR,int LONGUEUR) {
        this.LONGUEUR=LONGUEUR;
        this.LARGEUR=LARGEUR;
    }

    public void init(){
        setLayout(null);
        // Init les boutons
        charger = creerBouton("Charger");
        sauvegarder = creerBouton("Sauvegarder");
        zoom = creerBouton("Zoom");
        dezoom = creerBouton("Dezoom");
        // Ajoute les composants
        charger.setBounds(LARGEUR/10,0,LARGEUR/10,LONGUEUR);
        add(charger);

        sauvegarder.setBounds(LARGEUR/2-LARGEUR/6-LARGEUR/10,0,LARGEUR/10,LONGUEUR);
        add(sauvegarder);

        zoom.setBounds(LARGEUR/2+LARGEUR/10,0,LARGEUR/10,LONGUEUR);
        add(zoom);
        dezoom.setBounds(LARGEUR-LARGEUR/6-LARGEUR/10,0,LARGEUR/10,LONGUEUR);
        add(dezoom);

        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() == charger) {
                    System.out.println("Charger cliqué");
                }

                if (evt.getSource() == sauvegarder) {
                    System.out.println("Sauvegarder cliqué");
                }

                if (evt.getSource() == zoom) {
                    System.out.println("Zoom cliqué");
                }

                if (evt.getSource() == dezoom) {
                    System.out.println("Dezoom cliqué");
                }
            }
        };

        charger.addActionListener(action);
        sauvegarder.addActionListener(action);
        zoom.addActionListener(action);
        dezoom.addActionListener(action);
    }

    /**
     * Renvoie un bouton avec des propriétés de base (police, etc)
     * 
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
