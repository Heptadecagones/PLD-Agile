package view;

import java.awt.Dimension;
import java.awt.BorderLayout;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import model.Livreur;
import model.PlanLivraison;

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

    public void setDescText(String setdescText) {
        this.descText.setText(setdescText);
    }

    public Description() {
    }

    public void init() {
        setLayout(new BorderLayout());

        descText = new JTextArea(10, 10);
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
        for(Livreur li:p.obtenirListeLivreur()){
            ListeLivraison+=li.toString()+ "\n\n";
        }
        setDescText(ListeLivraison);
    }
}