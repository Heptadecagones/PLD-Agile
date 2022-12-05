package view;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
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
        /*
         * try {
         * 
         * for (LookAndFeelInfo info : UIManager.obtenirInstalledLookAndFeels()) {
         * if ("Nimbus".equals(info.obtenirName())) {
         * UIManager.modifierLookAndFeel(info.obtenirClassName());
         * break;
         * }
         * }
         * } catch (ClassNotFoundException | InstantiationException |
         * IllegalAccessException | UnsupportedLookAndFeelException ex) {
         * ex.printStackTrace();
         * }
         */

        JFrame frame = new JFrame("PLD AGILE");
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setLocation(0, 0);
       
        System.out.println(frame.getBounds().height*3/4);
       
        this.carte=new Carte(frame.getBounds().height*3/4,frame.getBounds().width*3/4);
        this.barre=new Barre();
        this.description=new Description();
       
        barre.setLocation(0,0);
        barre.setSize(frame.getBounds().width, frame.getBounds().height*1/4);
        barre.setBorder(BorderFactory.createLineBorder(Color.BLUE));
       
        carte.setLocation(frame.getBounds().width*1/4,frame.getBounds().height*1/4);
        carte.setSize(frame.getBounds().width*3/4, frame.getBounds().height*3/4);
        carte.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        
        description.setLocation(0,frame.getBounds().height*1/4);
        description.setSize(frame.getBounds().width*1/4, frame.getBounds().height*3/4);
        description.setBorder(BorderFactory.createLineBorder(Color.RED));
        
        frame.add(barre);
        frame.add(carte);
        frame.add(description);
        frame.setVisible(true);
    }

}
