package view;

import java.awt.Dimension;
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
    private int LARGEUR;
    private int LONGUEUR;
    /**
     * la taille du panneau
     */

    public void setDescText(String setdescText) {
        this.descText.setText(setdescText);
    }

    public Description(int LARGEUR,int LONGUEUR) {
        this.LONGUEUR=LONGUEUR;
        this.LARGEUR=LARGEUR;
    }

    public void init(){
        descText = new JTextArea(25, 25);
        descText.setLineWrap(true);
        scrollPane = new JScrollPane(descText);
        scrollPane.setSize(LARGEUR,LONGUEUR);
        this.add(scrollPane);
    }

    // Mise à jour des données : Ecriture de la liste des livraisons
    @Override
    public void update(Observable arg0, Object arg1) {
        PlanLivraison p = (PlanLivraison) arg0;
        String ListeLivraison = "";
        for(Livreur li:p.obtenirListeLivreur()){
            ListeLivraison+=li.toString()+ "\n";
        }
        setDescText(ListeLivraison);
    }
}
