package com.hexa17.pldagile.view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.PlanLivraison;

/**
 *
 * @author Equipe IHM
 */

@SuppressWarnings("serial")
public class Description extends JPanel implements Observer {
    /**
     * Tous les composants
     */
    JTextArea descText;
    JScrollPane scrollPane;

    /**
     * la taille du panneau
     */
    private int LONGUEUR = 200;
    private int HAUTEUR = 650;

    public void setDescText(String setdescText) {
        this.descText.setText(setdescText);
    }

    public Description() {
        setPreferredSize(new Dimension(LONGUEUR, HAUTEUR));
        setBorder(new TitledBorder("Description"));

        descText = new JTextArea(20, 25);
        descText.setText("Pas de contenu");
        descText.setLineWrap(true);
        scrollPane = new JScrollPane(descText);
        add(scrollPane);
    }

    // Mise à jour des données : Ecriture de la liste des livraisons
    @Override
    public void update(Observable arg0, Object arg1) {
        PlanLivraison p = (PlanLivraison) arg0;
        String ListeLivraison = "";
        for (Livreur li : p.obtenirListeLivreur()) {
            ListeLivraison += li.toString() + "\n";
        }
        setDescText(ListeLivraison);
    }
}