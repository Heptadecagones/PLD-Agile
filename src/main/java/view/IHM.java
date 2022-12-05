package view;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.Color;
/**
 *
 * @author Equipe IHM
 */

public class IHM {
    // position du coin supérieur gauche de l'application
    private Barre barre; 
    private Description description;
    private Carte carte;
    private JScrollPane panelCarte;

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

        JFrame frame = new JFrame("PLD AGILE");
        frame.setLayout(null);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setLocation(0, 0);
              
        this.carte=new Carte(frame.getBounds().height*8/10,frame.getBounds().height*8/10);
        this.barre=new Barre(frame.getBounds().width, frame.getBounds().height*1/10);
        this.description=new Description(frame.getBounds().width*1/4, frame.getBounds().height*8/10);
       
        barre.setLocation(0,0);
        barre.setSize(frame.getBounds().width, frame.getBounds().height*1/10);
        barre.setBackground(Color.BLUE);

        description.setLocation(0,frame.getBounds().height*1/10);
        description.setSize(frame.getBounds().width*1/4, frame.getBounds().height*8/10);
        description.setBackground(Color.RED);
        
        barre.init();
        description.init();

        panelCarte = new JScrollPane(carte);
        panelCarte.setLocation(frame.getBounds().width*1/4,frame.getBounds().height*1/10);
        panelCarte.setSize(frame.getBounds().width*3/4, frame.getBounds().height*8/10);
        panelCarte.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelCarte.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelCarte.setEnabled(true);

        frame.add(barre);
        frame.add(panelCarte);
        frame.add(description);
        frame.setVisible(true);
    }

}
