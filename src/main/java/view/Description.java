/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author NGUYEN Danh Lan
 */

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.Livraison;
import model.Plan;

@SuppressWarnings("serial")
public class Description extends JPanel implements Observer{
	JTextArea descText;
    private final int LONGUEUR = 200;
    private final int HAUTEUR = 650;
    public void setDescText(String setdescText) {
        this.descText.setText(setdescText);
    }
    public Description() {
            setPreferredSize(new Dimension(LONGUEUR, HAUTEUR));
            setBorder(new CompoundBorder(new TitledBorder("Description"), new EmptyBorder(0, 0, 0, 0)));

            descText = new JTextArea("Pas de contenu");
            this.add(descText);
            // TO DO
    }
    @Override
    public void update(Observable arg0, Object arg1) {
        Plan p=(Plan) arg0;
        String ListeLivraison="";
        for(Livraison l : p.obtenirListeLivraison()){
            ListeLivraison+=l.toString()+"\n\n";
        }
        setDescText(ListeLivraison);
    }
}
