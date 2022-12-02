package view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import model.Livraison;
import model.Plan;

@SuppressWarnings("serial")
public class Description extends JPanel implements Observer {
    JTextArea descText;
    JScrollPane scrollPane;
    private final int LONGUEUR = 200;
    private final int HAUTEUR = 650;

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

    // UPDATE DES DONNEES : ECRITURE DE LA LISTE DES LIVRAISONS
    @Override
    public void update(Observable arg0, Object arg1) {
        Plan p = (Plan) arg0;
        String ListeLivraison = "";
        for (Livraison l : p.obtenirListeLivraison()) {
            ListeLivraison += l.toString() + "\n\n";
        }
        setDescText(ListeLivraison);
    }
}
